package slack;

import okhttp3.*;
import java.io.File;
import java.io.IOException;

public class FileUploader {
    private static final String SLACK_API_TOKEN = OauthCript.getOauthToken();
    private static final String CHANNEL_ID = "C066MKGGPBR";


    public static void enviarArquivoParaSlack(String caminhoArquivo) {
        OkHttpClient client = new OkHttpClient();

        File arquivo = new File(caminhoArquivo);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", SLACK_API_TOKEN)
                .addFormDataPart("channels", CHANNEL_ID)
                .addFormDataPart("file", arquivo.getName(), RequestBody.create(MediaType.parse("text/plain"), arquivo))
                .build();

        Request request = new Request.Builder()
                .url("https://slack.com/api/files.upload")
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("Log enviado no slack");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}