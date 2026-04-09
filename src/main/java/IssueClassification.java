import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public record IssueClassification(Label category) {
    @SystemMessage("""
            You are a bot in charge of categorizing issues from a bug tracker.
             Analyze and classify the issue into ONE category ONLY.
                  Respond with ONLY valid JSON: {"category": "CATEGORY_NAME"}
            
                  Categories:
                  - PERSISTENCE: database, ORM, save/store, JDBC, entities
                  - VALIDATION: divide by 0, null checks, input validation, math errors, overflow
                  - NETWORK: connection, timeout, socket, API call
                  - UI: button, layout, render, display
                  - ALGORITHM: loop, recursion, sorting, search logic
                  - GENERIC: everything else
            
                  EXAMPLES:
                  "JDBC connection exception" → {"category": "NETWORK"}
                  "When storing a user in the database" → {"category": "PERSISTENCE"}
                  "divide by 0" → {"category": "VALIDATION"}
            """)
    public interface LabelDetector {
        @UserMessage("""
                Analyze the provided issue and categorize into one of the categories.
                The issues opened are for Java projects
                so you can expect some Java acronyms,
                use them to categorize the issues as well.
                The possible values for a category must be
                PERSISTENCE, UI, EVENT or GENERIC.
                In case of not knowing how to categorize use the GENERIC label.
                Some examples of you might find:
                INPUT: Entity is not persisted
                OUTPUT: PERSISTENCE
                INPUT: JPA is failing to configure entities
                OUTPUT: PERSISTENCE
                INPUT: The element is not visible in the web
                OUTPUT: UI
                INPUT: The event is sent but never received
                OUTPUT: EVENT
                INPUT: Kafka streaming is failing in some circumstances
                OUTPUT: EVENT
                INPUT: java.lang.NullPointerException in a request
                                  OUTPUT: GENERIC
                                  INPUT: {{issueTitle}}
                                  OUTPUT:
                """)
        IssueClassification categorizeIssue(@V("issueTitle") String issueTitle);
    }
}
