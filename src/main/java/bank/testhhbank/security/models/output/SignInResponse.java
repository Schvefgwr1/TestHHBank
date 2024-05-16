package bank.testhhbank.security.models.output;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {
    private Integer status;
    private String token;
    private String refreshToken;
    private String message;
}
