package bank.testhhbank.web.DTO;

import bank.testhhbank.models.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class UserResponse {
    private User user;
    private String message;
}
