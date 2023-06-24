from flask import Flask, request, redirect, session
from config import *
from router import login_router, logout_router, token_router, user_profile_router, bot_router

app = Flask(__name__)
app.secret_key = APP_KEY_SECRET

@app.route("/login", methods=["GET"])
def login():
    return login_router.login()

@app.route("/callback")
def callback():
    return login_router.callback()

@app.route("/token", methods=["GET"])
def token():
    return token_router.token()

@app.route("/verify", methods=["GET"])
def verify():
    return token_router.verify()

@app.route("/userProfile", methods=["GET"])
def user_profile():
    return user_profile_router.user_profile()

@app.route("/logout", methods=["POST"])
def logout():
    return logout_router.logout()

@app.route("/sendMessage", methods=["POST"])
def send_message():
    return bot_router.send_message()

@app.route("/weight", methods=["GET"])
def weight():
    return {
        'weight': "100"
    }

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8080, debug=True)