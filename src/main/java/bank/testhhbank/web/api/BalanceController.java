package bank.testhhbank.web.api;

import bank.testhhbank.web.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path="/api/transfer", produces="application/json")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PatchMapping
    public ResponseEntity<String> processTransfer(
            @RequestParam Long fromUserId,
            @RequestParam Long toUserId,
            @RequestParam Double amount
    ) {
        String response = balanceService.transferMoney(
                fromUserId,
                toUserId,
                amount
        );
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if(Objects.equals(response, "Successful transfer")) {
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(response, httpStatus);
    }

}
