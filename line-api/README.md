# API サーバの建て方

## line-api 側

### 0. ngrok で localhost:80 を公開
以下を実行して ngrok をインストールする。
```
brew install ngrok/ngrok/ngrok
```
インストールしたら
```
ngrok http 8080
```
を実行。
```
...
Forwarding                    https://xxxxxxxxx -> http://localhost:8080 
...
```
というのが表示されるので `https://xxxxxxxxx` の部分をメモしとく。


### 1. LINE Developers API で自身の callback URL を追加する
callback URL に `https://xxxxxxxxx/callback` を追加。
TODO (まだチャンネルに招待していない)

### 2. line-api/key.env を追加

`line-api/key.env` を作成し以下を内容を追加する。
```
APP_KEY_SECRET = 'secret key'

LINE_CHANNEL_ID = 'KeyMinder Login のチャネルID'
LINE_CHANNEL_SECRET = 'KeyMinder Login のチャネルシークレット'
HOST_URL = 'callback URL に登録した https://xxxxxxxxx/callback の https://xxxxxxxxx の部分'
BOT_ACCESS_TOKEN = 'KeyMinderNotify の チャネルアクセストークン'
```

### 3. line-api/app/main.py を実行


## android 側
### 1. android/gradle.properties に HOST を追加
`android/gradle.properties` に
```
...
HOST=xxxxxxxxx (https://xxxxxxxxx/callback の xxxxxxxxx)
```
を追加する。

### 2. android/build.gradle を編集
`android/build.gradle` に
```
...
    buildTypes {
        release {
            ...
            manifestPlaceholders = [HOST: project.property("HOST")];
            buildConfigField "String", "HOST", "\"${project.property("HOST")}\""
        }
        debug {
            ...
            manifestPlaceholders = [HOST: project.property("HOST")];
            buildConfigField "String", "HOST", "\"${project.property("HOST")}\""
        }
    }
...
```
を追加。

### 3. build する