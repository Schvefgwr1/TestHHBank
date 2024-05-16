package bank.testhhbank.web.DTO;

import bank.testhhbank.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private User user;
    private String message;
}
