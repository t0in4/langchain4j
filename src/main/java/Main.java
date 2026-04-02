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
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.output.JsonSchemas;
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
                .authClient(authClient)
                .responseFormat(ResponseFormat.builder()
                        .type(ResponseFormatType.JSON)
                        .jsonSchema(JsonSchemas.jsonSchemaFrom(IssueClassification.class).get())
                        .build())
                .build();
        IssueClassification.LabelDetector labelDetector = AiServices.builder(IssueClassification.LabelDetector.class)
                .chatModel(model)
                .build();
        IssueClassification label1 = labelDetector
                .categorizeIssue("When storing a user in the database, it throws an exception");
        System.out.println(label1);
        IssueClassification label2 = labelDetector
                .categorizeIssue("JDBC connection exception thrown");
        System.out.println(label2);
        IssueClassification label3 = labelDetector
                .categorizeIssue("Math operation fails when divide by 0");
        System.out.println(label3);
    }
}
