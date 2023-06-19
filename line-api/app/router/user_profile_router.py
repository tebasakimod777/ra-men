import hashlib
from flask import Flask, request, redirect, session
import urllib.request
import urllib.parse
import json
import base64
import os
from config import *

def user_profile():
    access_token = request.args.get('access_token')
    headers = {
        'Authorization': 'Bearer ' + access_token
    }
    req, res = '', ''
    req = urllib.request.Request(USER_INFO_URL, headers=headers, method='GET')
    with urllib.request.urlopen(req) as f:
        res = f.read()
    return json.loads(res)