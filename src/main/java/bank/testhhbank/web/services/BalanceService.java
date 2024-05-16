package bank.testhhbank.web.services;

import bank.testhhbank.data.UserRepository;
import bank.testhhbank.models.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceService {

    private final UserRepository userRepository;

    @Scheduled(fixedRate=60000)
    public void increaseBalances() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            double newBalance = Math.min(
                    user.getPrice() * 1.05,
                    user.getInitialPrice() * 2.07
            );
            newBalance = Double.parseDouble(
                    String.format("%.2f", newBalance).replace(",", ".")
            );
            if(user.getPrice() != newBalance) {
                user.setPrice(newBalance);
                log.info("User with new rating {}", user);
                userRepository.save(user);
            }
        }
    }

    @Transactional
    public String transferMoney(long fromUserId, long toUserId, double amount) {

        Optional<User> fromUserCont = userRepository.findById(fromUserId);
        Optional<User> toUserCont = userRepository.findById(toUserId);

        if(fromUserCont.isPresent() && toUserCont.isPresent()) {

            User fromUser = fromUserCont.get();
            User toUser = toUserCont.get();

            if (fromUser.getPrice() < amount) {
                return "Incorrect amount of money";
            }
            else {
                fromUser.setPrice(fromUser.getPrice() - amount);
                toUser.setPrice(toUser.getPrice() + amount);
                userRepository.save(fromUser);
                userRepository.save(toUser);
                log.info("Amount {} set from {} to {}", amount, fromUser, toUser);
                return "Successful transfer";
            }
        }
        else {
            return "Incorrect user`s ids";
        }
    }
}
