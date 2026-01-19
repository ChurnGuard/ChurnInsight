# tests/agents/generate_refresh_token.py
import os
from google_auth_oauthlib.flow import InstalledAppFlow

SCOPES = [
    "https://www.googleapis.com/auth/gmail.send",
    "https://www.googleapis.com/auth/calendar",
    "https://www.googleapis.com/auth/spreadsheets",
]

CREDENTIALS_PATH = os.path.join(os.path.dirname(__file__), "../../config/credentials.json")

flow = InstalledAppFlow.from_client_secrets_file(
    CREDENTIALS_PATH,
    SCOPES
)

creds = flow.run_local_server(port=0)
print("✅ Generate a new REFRESH TOKEN and put it in .env")
print("refresh_token:", creds.refresh_token)
