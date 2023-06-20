//package com.example.keyminder;
//
//import android.os.AsyncTask;
//
//import com.slack.api.Slack;
//
//import com.slack.api.methods.MethodsClient;
//import com.slack.api.methods.SlackApiException;
//import com.slack.api.methods.request.chat.ChatPostMessageRequest;
//import com.slack.api.methods.response.chat.ChatPostMessageResponse;
//
//import java.io.IOException;
//
//public class SlackNotificationTask extends AsyncTask<String, Void, Void> {
//
//    Slack slack;
//    String token;
//    MethodsClient methods;
//
//    public SlackNotificationTask() {
//        slack = Slack.getInstance();
//        token = "";
//        methods = slack.methods(token);
//    }
//
//    @Override
//    protected Void doInBackground(String... strings) {
//        try {
//            postMessage(strings[0], strings[1]);
//        } catch (SlackApiException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//
//    void postMessage(String channelName, String message) throws SlackApiException, IOException {
//        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
//                .channel(channelName)
//                .text(message)
//                .build();
//        ChatPostMessageResponse response = methods.chatPostMessage(request);
//    }
//}
