# agents/telegram_agent.py

import requests
import html
import os
from dotenv import load_dotenv

load_dotenv()

TELEGRAM_TOKEN = os.getenv("TELEGRAM_BOT_TOKEN")
TELEGRAM_CHAT_ID = os.getenv("TELEGRAM_CHAT_ID")

def telegram_enabled() -> bool:
    return bool(TELEGRAM_TOKEN and TELEGRAM_CHAT_ID)

def escape_telegram_text(text: str) -> str:
    return html.escape(text)

def send_telegram_message(text: str) -> dict:
    if not telegram_enabled():
        return {"status": "skipped", "reason": "Telegram not configured"}

    url = f"https://api.telegram.org/bot{TELEGRAM_TOKEN}/sendMessage"
    payload = {
        "chat_id": TELEGRAM_CHAT_ID,
        "text": escape_telegram_text(text),
        "parse_mode": "HTML"
    }

    try:
        response = requests.post(url, json=payload, timeout=10)
        response.raise_for_status()
        return {"status": "ok", "response": response.json()}
    except requests.exceptions.RequestException as e:
        return {"status": "error", "error": str(e)}
