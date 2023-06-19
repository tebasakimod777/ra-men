import hashlib
from flask import Flask, request, redirect, session
import urllib.request
import urllib.parse
import requests
import json
import base64
import os
from config import *

def token():
    code = request.args.get('code')
    body = urllib.parse.urlencode({
        'code': code,
        'client_id': LINE_CHANNEL_ID,
        'client_secret': LINE_CHANNEL_SECRET,
        'redirect_uri': REDIRECT_URL,
        'grant_type': 'authorization_code'
    }).encode('utf-8')
    req, res = '', ''
    req = urllib.request.Request(TOKEN_URL)
    with urllib.request.urlopen(req, data=body) as f:
        res = f.read()
    return json.loads(res)

def verify():
    access_token = request.args.get('access_token')
    payload = {'access_token': access_token}
    res = requests.get(VERIFY_URL+f'?access_token={access_token}')
    return {
        'status_code': res.status_code
    }
