import chat.giga.client.auth.AccessToken;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.http.client.HttpClient;
import chat.giga.http.client.HttpRequest;
import chat.giga.langchain4j.GigaChatChatModel;
import chat.giga.langchain4j.GigaChatChatRequestParameters;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.service.AiServices;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.UUID;

public class Main {
   public static void main(String[] args) {
       Dotenv dotenv = Dotenv.load();
       String authKey = dotenv.get("GIGACHAT_AUTH_KEY");
       AuthClient authClient = AuthClient.builder()
               .withOAuth(AuthClientBuilder.OAuthBuilder.builder()  // ← Правильный класс!
                       .scope(Scope.GIGACHAT_API_PERS)
                       .authKey(authKey)  // Authorization key
                       .build()).build();
       ChatModel model = GigaChatChatModel.builder()
                .defaultChatRequestParameters(
                        GigaChatChatRequestParameters.builder()
                                
                            .modelName(ModelName.GIGA_CHAT)
                                .responseFormat(ResponseFormat.JSON)

                                .build())  // ← Работает!
               .authClient(authClient)
               .build();
       Transaction tx = AiServices.builder(Transaction.class)
               .chatModel(model)
               .build();
       TransactionInfo transactionInfo = tx.extract(
               "My name is Alex; I did a transaction on July 45h, 2023 from my account with IBAN 123456789 of $25.5"
       );
       System.out.println(transactionInfo);
   }
}
