import hashlib
from flask import Flask, request, redirect, session
import urllib.request
import urllib.parse
import json
import base64
import os
from config import *

def logout():
    access_token = request.args.get('access_token')
    body = urllib.parse.urlencode({
        'client_id': LINE_CHANNEL_ID,
        'client_secret': LINE_CHANNEL_SECRET,
        'access_token': access_token
    }).encode('utf-8')
    req = urllib.request.Request(REVOKE_URL, data=body, method="POST")
    with urllib.request.urlopen(req) as f:
        res = f.read()
    return {'status_code': res.getcode()}