
# 主要な機能

## NotificationManager
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

## LoginSettingActivity
LINE のログイン・ログアウトを行うActivity。

ログアウト状態だとログインボタンが表示され、ログアウト状態だとログインボタンが表示される。

ログイン・ログアウトを行うと新しい LoginSettingActivity を開始する。

LoginSettingActivity の完了ボタンを押すと MainActivity に遷移する。