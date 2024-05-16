package bank.testhhbank.web.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullInformationRequest {
    private String firstname;
    private String lastname;
    private String surname;
    private String dateOfBirth;
}
