package slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.channels.ChannelsInviteRequest;
import com.slack.api.methods.request.conversations.ConversationsInviteRequest;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.channels.ChannelsInviteResponse;
import com.slack.api.methods.response.conversations.ConversationsInviteResponse;
import com.slack.api.methods.response.conversations.ConversationsJoinResponse;

import java.io.IOException;
import java.util.Collections;

public class Bot {

    private static final String SLACK_API_TOKEN = "";
    private static final String BOT_ID = "";
    private static final String CHANNEL_ID = "";

    public static void main(String[] args) {
        // Obter informações de autenticação para obter o ID do bot
        AuthTestResponse authTestResponse = getAuthTestResponse(SLACK_API_TOKEN);

        if (authTestResponse != null && authTestResponse.isOk()) {
            String botId = authTestResponse.getBotId();

            // Adicionar o bot ao canal
            addBotToChannel(SLACK_API_TOKEN, botId, CHANNEL_ID);
        } else {
            System.out.println("Erro ao obter informações de autenticação: " + (authTestResponse != null ? authTestResponse.getError() : ""));
        }
    }

    private static AuthTestResponse getAuthTestResponse(String token) {
        try {
            Slack slack = Slack.getInstance();
            return slack.methods(token).authTest(r -> r.token(token));
        } catch (IOException | com.slack.api.methods.SlackApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addBotToChannel(String token, String botId, String channelId) {
        Slack slack = Slack.getInstance();

        try {
            // Use conversationsJoin para adicionar o bot ao canal
            ConversationsJoinResponse joinResponse = slack.methods().conversationsJoin(r -> r
                    .token(token)
                    .channel(channelId)
            );

            if (joinResponse.isOk()) {
                System.out.println("Bot adicionado ao canal com sucesso!");
            } else {
                System.out.println("Erro ao adicionar o bot ao canal: " + joinResponse.getError());
            }
        } catch (IOException | com.slack.api.methods.SlackApiException e) {
            e.printStackTrace();
        }
    }
}