
# 主要な機能

## LoginSettingActivity
LINE のログイン・ログアウトを行うActivity。

ログアウト状態だとログインボタンが表示され、ログアウト状態だとログインボタンが表示される。

ログイン・ログアウトを行うと新しい LoginSettingActivity を開始する。

LoginSettingActivity の完了ボタンを押すと MainActivity に遷移する。

## package com.example.keyminder.notification
### NotificationManager
画面に通知またはLINEに通知が可能。

Activity内で
```
NotificationManager notificationManager = new NotificationManager(this);
notificationManager.Notify("メッセージ");
```
とするとActivity上に通知を送れる。

LINEアカウントにログインした状態だと LINE に通知が送信される。
また
```
NotificationManager notificationManager = new NotificationManager(this);
notificationManager.Notify("メッセージ", "native");
```
と第2引数に `native` を指定することで LINE のログイン状態に関わらず Activity上に通知を送れる。

LINEの通知は LINE API サーバーと通信が必要なのでデバッグ時は第2引数に `native` を指定した状態で利用したほうがよさそう。

## package com.example.keyminder.line

LINE に関する処理をまとめた package。基本的に使わないほうがよさそう。
### LineLoginTask
LINE ログインを行うAsyncTask。Activity内で
```
LineLoginTask loginTask = new LineLoginTask(this);
loginTask.execute();
```
とすると LINE のログイン認証画面に飛ぶ。
LINE のログイン認証が終了すると LoginSettingActivity に自動的に遷移するので注意。

### LineLogoutTask
LINE ログアウトを行うAsyncTask。
```
LineLogoutTask logoutTask = new LineLogoutTask(LoginSettingActivity.this);
logoutTask.execute(access_token);
```
とすることでログインしている LINE アカウントからログアウトする。
LineLoginTask と違ってログアウト後に別の Activity に遷移しない。

### LineNotification Task
ログインしている LINE アカウントに通知をする AsyncTask。

通知を行う前に 取得している `access_token` が有効であることを検証する必要がある。
Activity 内で以下のように実行する。
```
SharedPreferences pref = this.getSharedPreferences("prefs", MODE_PRIVATE);
String access_token = pref.getString("access_token", "");
String userId = pref.getString("userId", "");

VerifyTokenTask verifyTokenTask = new VerifyTokenTask();
String token_state = "";
try {
    token_state = verifyTokenTask.execute(access_token).get();
} catch (ExecutionException e) {
    throw new RuntimeException(e);
} catch (InterruptedException e) {
    throw new RuntimeException(e);
}
if (token_state == "valid") {
    LineNotificationTask lineNotificationTask = new LineNotificationTask(this);
    lineNotificationTask.execute(userId, message);
} else {
    // invalid
}
```