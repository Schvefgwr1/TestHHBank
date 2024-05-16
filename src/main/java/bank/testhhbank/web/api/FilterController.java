package bank.testhhbank.web.api;

import bank.testhhbank.web.DTO.FilterResponse;
import bank.testhhbank.web.services.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/api/filters", produces="application/json")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @GetMapping(path="/{parameter}")
    public ResponseEntity<FilterResponse> getUsersOlderDate(@PathVariable("parameter") String parameter) {
        FilterResponse response = filterService.findUsersByParameter(parameter);
        if(response.getMessage().contains("Success")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
