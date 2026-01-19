# agents/google_agents.py

import os
import base64
import json

from email.mime.text import MIMEText
from typing import Dict, List
from datetime import datetime, timedelta

from google.oauth2.credentials import Credentials
from google.auth.transport.requests import Request
from googleapiclient.discovery import build
from googleapiclient.errors import HttpError
from dotenv import load_dotenv

load_dotenv()

GMAIL_CLIENT_ID = os.getenv("GMAIL_CLIENT_ID")
GMAIL_CLIENT_SECRET = os.getenv("GMAIL_CLIENT_SECRET")
GMAIL_REFRESH_TOKEN = os.getenv("GMAIL_REFRESH_TOKEN")
GMAIL_RECIPIENT = os.getenv("GMAIL_RECIPIENT")
SPREADSHEET_ID = os.getenv("SPREADSHEET_ID")

SCOPES = [
    "https://www.googleapis.com/auth/gmail.send",
    "https://www.googleapis.com/auth/calendar",
    "https://www.googleapis.com/auth/spreadsheets",
]

def get_credentials():
    creds = Credentials(
        None,
        refresh_token=os.getenv("GMAIL_REFRESH_TOKEN"),
        client_id=os.getenv("GMAIL_CLIENT_ID"),
        client_secret=os.getenv("GMAIL_CLIENT_SECRET"),
        token_uri="https://oauth2.googleapis.com/token",
        scopes=SCOPES
    )
    creds.refresh(Request())
    return creds

def get_credentials_from_json():
    """
    Used for local dev or legacy OAuth file-based flow.
    """
    from google.oauth2.credentials import Credentials
    import json
    import os

    creds_json = os.getenv("GOOGLE_CREDENTIALS_JSON")
    if creds_json:
        return Credentials.from_authorized_user_info(
            json.loads(creds_json),
            scopes=SCOPES
        )

    return Credentials.from_authorized_user_file(
        "config/credentials.json",
        scopes=SCOPES
    )

def get_google_credentials():
    """
    Smart credentials loader.
    """
    if os.getenv("GMAIL_REFRESH_TOKEN"):
        return get_credentials()

    return get_credentials_from_json()
   

# Gmail

def send_email(subject: str, body: str, recipient: str = None) -> Dict:
    recipient = recipient or GMAIL_RECIPIENT
    if not recipient:
        return {"status": "error", "error": "No email recipient configured"}

    try:
        creds = get_credentials() #original line main.py
        #creds = get_google_credentials()
        service = build("gmail", "v1", credentials=creds)

        message = MIMEText(body)
        message["to"] = recipient
        message["subject"] = subject

        encoded_msg = base64.urlsafe_b64encode(message.as_bytes()).decode()

        sent = service.users().messages().send(
            userId="me",
            body={"raw": encoded_msg},
        ).execute()

        return {"status": "ok", "message_id": sent.get("id")}

    except HttpError as e:
        return {"status": "error", "error": str(e)}

# Calendar + Meet

def create_event_with_meet(
    summary: str,
    description: str,
    start: str,
    end: str,
    attendees: List[str],
) -> Dict:
    try:
        creds = get_credentials()
        service = build("calendar", "v3", credentials=creds)

        event = {
            "summary": summary,
            "description": description,
            "start": {"dateTime": start, "timeZone": "UTC"},
            "end": {"dateTime": end, "timeZone": "UTC"},
            "attendees": [{"email": a} for a in attendees if a],
            "conferenceData": {
                "createRequest": {
                    "requestId": f"{summary}-{datetime.utcnow().timestamp()}",
                    "conferenceSolutionKey": {"type": "hangoutsMeet"},
                }
            },
        }
        created_event = service.events().insert(
            calendarId="primary",
            body=event,
            conferenceDataVersion=1,
        ).execute()
        return created_event

    except HttpError as e:
        return {"status": "error", "error": str(e)}

# Google Sheets (Audit)

def append_to_sheet(row: Dict) -> Dict:
    """
    Append audit row to Google Sheets in fixed column order.
    """
    try:
        creds = get_credentials()
        service = build("sheets", "v4", credentials=creds)

        ordered_values = [[
            row.get("timestamp"),
            row.get("customer_id"),
            row.get("decision_type"),
            row.get("churn_prob"),
            row.get("urgency"),
            row.get("action_suggestion"),
            row.get("value"),
            row.get("flags"),
        ]]

        body = {"values": ordered_values}

        result = service.spreadsheets().values().append(
            spreadsheetId=SPREADSHEET_ID,
            range="Sheet1!A1",
            valueInputOption="RAW",
            insertDataOption="INSERT_ROWS",
            body=body,
        ).execute()

        return {"status": "ok", "updates": result.get("updates")}

    except HttpError as e:
        return {"status": "error", "error": str(e)}
