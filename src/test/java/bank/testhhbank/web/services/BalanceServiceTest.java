package bank.testhhbank.web.services;

import bank.testhhbank.data.UserRepository;
import bank.testhhbank.models.User;
import bank.testhhbank.web.services.BalanceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BalanceServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    public void testTransferMoney_SuccessfulTransfer() {
        long fromUserId = 1L;
        long toUserId = 2L;
        double initialAmount = 100.0;
        double transferAmount = 50.0;

        User fromUser = new User();
        fromUser.setId(fromUserId);
        fromUser.setPrice(initialAmount);

        User toUser = new User();
        toUser.setId(toUserId);
        toUser.setPrice(initialAmount);

        when(userRepository.findById(fromUserId)).thenReturn(Optional.of(fromUser));
        when(userRepository.findById(toUserId)).thenReturn(Optional.of(toUser));

        String result = balanceService.transferMoney(fromUserId, toUserId, transferAmount);

        assertEquals("Successful transfer", result);
        assertEquals(initialAmount - transferAmount, fromUser.getPrice());
        assertEquals(initialAmount + transferAmount, toUser.getPrice());
    }

    @Test
    public void testTransferMoney_IncorrectAmount() {
        long fromUserId = 1L;
        long toUserId = 2L;
        double initialAmount = 30.0;
        double transferAmount = 50.0;

        User fromUser = new User();
        fromUser.setId(fromUserId);
        fromUser.setPrice(initialAmount);

        User toUser = new User();
        toUser.setId(toUserId);

        when(userRepository.findById(fromUserId)).thenReturn(Optional.of(fromUser));
        when(userRepository.findById(toUserId)).thenReturn(Optional.of(toUser));
        String result = balanceService.transferMoney(fromUserId, toUserId, transferAmount);
        assertEquals("Incorrect amount of money", result);
    }

    @Test
    public void testTransferMoney_InvalidUserId() {
        long fromUserId = 1L;
        long toUserId = 999L;  // Invalid user id
        double amount = 50.0;

        when(userRepository.findById(fromUserId)).thenReturn(Optional.of(new User()));
        when(userRepository.findById(toUserId)).thenReturn(Optional.empty());

        String result = balanceService.transferMoney(fromUserId, toUserId, amount);

        assertEquals("Incorrect user`s ids", result);
    }

}

