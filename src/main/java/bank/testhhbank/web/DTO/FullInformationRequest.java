package bank.testhhbank.web.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FullInformationRequest {
    private String firstname;
    private String lastname;
    private String surname;
    private String dateOfBirth;
}
