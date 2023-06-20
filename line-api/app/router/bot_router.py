import hashlib
from flask import Flask, request, redirect, session
import urllib.request
import urllib.parse
import json
import base64
import os
from config import *

from linebot import LineBotApi
from linebot.models import TextSendMessage

def send_message():

    user_id = request.args.get('userId')
    messages = request.args.get('messages')

    line_bot_api = LineBotApi(BOT_ACCESS_TOKEN)

    line_bot_api.push_message(user_id, messages=TextSendMessage(text=messages))

    return {
        'status_code': 200
    }

    