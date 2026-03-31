import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
@SystemMessage("""
    ТЫ ОБЯЗАН отвечать ТОЛЬКО валидным JSON:
    {"name":"","iban":"","transactionDate":"YYYY-MM-DD","amount":0.0}
    
    НЕТ markdown, НЕТ пояснений, НЕТ русского текста!
    """)
public interface Transaction {


    //@UserMessage("Extract information about a transaction from {{it}}")
    //@Tool
    TransactionInfo extract(String message);
}
