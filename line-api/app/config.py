import os
from os.path import join, dirname
from dotenv import load_dotenv

dotenv_path = join(dirname(__file__), '../key.env')
load_dotenv(dotenv_path)

APP_KEY_SECRET = os.environ.get('APP_KEY_SECRET')

LINE_CHANNEL_ID = os.environ.get('LINE_CHANNEL_ID')

LINE_CHANNEL_SECRET = os.environ.get('LINE_CHANNEL_SECRET')

HOST_URL = os.environ.get('HOST_URL')

BOT_ACCESS_TOKEN = os.environ.get('BOT_ACCESS_TOKEN')

AUTHORIZATION_URL = 'https://access.line.me/oauth2/v2.1/authorize'

TOKEN_URL = 'https://api.line.me/oauth2/v2.1/token'

REVOKE_URL = 'https://api.line.me/oauth2/v2.1/revoke'

VERIFY_URL = 'https://api.line.me/oauth2/v2.1/verify'

USER_INFO_URL = 'https://api.line.me/v2/profile'

BOT_URL = "https://api.line.me/v2/bot/message/push"

REDIRECT_URL = HOST_URL + '/callback'



