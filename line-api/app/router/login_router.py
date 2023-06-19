import hashlib
from flask import Flask, request, redirect, session
import urllib.request
import urllib.parse
import json
import base64
import os
from config import *

def login():
    state = hashlib.sha256(os.urandom(32)).hexdigest()
    session['state'] = state
    return redirect(AUTHORIZATION_URL +'?{}'.format(urllib.parse.urlencode({
        'client_id': LINE_CHANNEL_ID,
        'redirect_uri': REDIRECT_URL,
        'state': state,
        'response_type': 'code',
        'scope': 'profile openid',
        'prompt': 'consent',
        'bot_prompt': 'normal'
    })))

def callback():
    state = request.args.get('state')
    code = request.args.get('code')
    if state != session['state']:
        print("invalid_redirect")
    return {
        'code': code
    }