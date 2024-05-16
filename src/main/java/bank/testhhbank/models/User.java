package bank.testhhbank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @Email
    private String email;

    @Column(name="phone_number")
    @Pattern(regexp="^\\+7 \\(9\\d{2}\\) \\d{3}-\\d{2}-\\d{2}$", message="Must be formatted +7 (999) 999-99-99")
    private String phoneNumber;

    @Pattern(regexp="^[A-Z][a-z]*$", message="Must start with capital letters")
    private String firstname;

    @Pattern(regexp="^[A-Z][a-z]*$", message="Must start with capital letters")
    private String lastname;

    @Pattern(regexp="^[A-Z][a-z]*$", message="Must start with capital letters")
    private String surname;

    @DateTimeFormat
    private LocalDate dateOfBirth;

    @NotNull
    @PositiveOrZero
    @Column(name="initial_price")
    private double initialPrice;

    @PositiveOrZero
    private double price = 0;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
