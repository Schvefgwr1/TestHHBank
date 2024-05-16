package bank.testhhbank.data;

import bank.testhhbank.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "WHERE u.email = :email OR " +
            "u.login = :login OR " +
            "u.phoneNumber = :phone_number")
    Optional<User> findByParam(
            @Param("email") String email,
            @Param("login") String login,
            @Param("phone_number") String phoneNumber
    );

    @Query("SELECT u FROM User u " +
            "WHERE u.firstname LIKE :firstname% AND " +
            "u.lastname LIKE :lastname% AND " +
            "u.surname LIKE :surname%")
    List<User> findUsersByFIO(
           @Param("firstname") String firstname,
           @Param("lastname") String lastname,
           @Param("surname") String surname
    );

    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    List<User> findUsersByDateOfBirthAfter(LocalDate date);

    Optional<User> findUsersByLogin(String login);
}
