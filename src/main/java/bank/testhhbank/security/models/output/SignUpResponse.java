package bank.testhhbank.security.models.output;


import bank.testhhbank.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResponse {
    private String validationExceptions;
    private String message;
    private User user;
    private Integer status;
}
