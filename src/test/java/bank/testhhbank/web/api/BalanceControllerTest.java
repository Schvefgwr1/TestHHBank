package bank.testhhbank.web.api;

import bank.testhhbank.web.services.BalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BalanceController.class)
public class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;

    @Test
    public void testProcessTransfer_SuccessfulTransfer() throws Exception {
        long fromUserId = 1L;
        long toUserId = 2L;
        double amount = 50.0;

        when(balanceService.transferMoney(fromUserId, toUserId, amount)).thenReturn("Successful transfer");

        mockMvc.perform(patch("/api/transfer")
                        .param("fromUserId", String.valueOf(fromUserId))
                        .param("toUserId", String.valueOf(toUserId))
                        .param("amount", String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Successful transfer"));
    }

    @Test
    public void testProcessTransfer_InvalidTransfer() throws Exception {
        long fromUserId = 1L;
        long toUserId = 2L;
        double amount = 50.0;

        when(balanceService.transferMoney(fromUserId, toUserId, amount)).thenReturn("Incorrect amount of money");

        mockMvc.perform(patch("/api/transfer")
                        .param("fromUserId", String.valueOf(fromUserId))
                        .param("toUserId", String.valueOf(toUserId))
                        .param("amount", String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Incorrect amount of money"));
    }

    @Test
    public void testProcessTransfer_InvalidUserId() throws Exception {
        long fromUserId = 1L;
        long toUserId = 999L;
        double amount = 50.0;

        when(balanceService.transferMoney(fromUserId, toUserId, amount)).thenReturn("Incorrect user`s ids");

        mockMvc.perform(patch("/api/transfer")
                        .param("fromUserId", String.valueOf(fromUserId))
                        .param("toUserId", String.valueOf(toUserId))
                        .param("amount", String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Incorrect user`s ids"));
    }

}
