# API サーバの建て方
## 1. LINE Developers API で　自身の callback URL を追加する
TODO (まだチャンネルに招待していない)

## line-api/key.env を追加
```
APP_KEY_SECRET = 'secret key'

LINE_CHANNEL_ID = 'KeyMinder Login のチャネルID'
LINE_CHANNEL_SECRET = 'KeyMinder Login のチャネルシークレット'
HOST_URL = 'callback URL に登録した https://xxxxxxxxx/callback の https://xxxxxxxxx の部分'
BOT_ACCESS_TOKEN = 'KeyMinderNotify の チャネルアクセストークン'
```