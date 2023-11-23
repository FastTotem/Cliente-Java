package slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;

public class Notification {

    private static final String SLACK_API_TOKEN = "";
    private static final String CHANNEL_ID = "";

    public static void enviarNotificacao(String mensagem) {
        Slack slack = Slack.getInstance();

        try {
            MethodsClient methods = slack.methods(SLACK_API_TOKEN);

            ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                    .channel(CHANNEL_ID)
                    .text(mensagem)
                    .build();

            ChatPostMessageResponse messageResponse = methods.chatPostMessage(messageRequest);

            if (messageResponse.isOk()) {
                System.out.println("Notificação enviada com sucesso!");
            } else {
                System.out.println("Erro ao enviar notificação: " + messageResponse.getError());
            }
        } catch (IOException | com.slack.api.methods.SlackApiException e) {
            e.printStackTrace();
        }
    }
}