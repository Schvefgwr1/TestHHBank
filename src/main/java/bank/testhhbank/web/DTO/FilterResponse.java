package bank.testhhbank.web.DTO;

import bank.testhhbank.models.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
public class FilterResponse {
    private List<User> users;
    private String message;
    private String type;
}
