import com.fasterxml.jackson.annotation.JsonProperty;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public record IssueClassification(@JsonProperty("classification") String category) {

    public interface LabelDetector {
        @SystemMessage("""
                ТОЛЬКО JSON без markdown: {"classification": "PERSISTENCE"}
                """)
        @UserMessage("""
                Классифицируйте: {{it}}
                """)
        IssueClassification categorizeIssue(String issue);
    }
}
