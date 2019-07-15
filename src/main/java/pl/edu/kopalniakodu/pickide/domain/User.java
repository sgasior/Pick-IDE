package pl.edu.kopalniakodu.pickide.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.kopalniakodu.pickide.domain.validator.ConfirmPasswords;
import pl.edu.kopalniakodu.pickide.domain.validator.NonWhitespace;
import pl.edu.kopalniakodu.pickide.domain.validator.PasswordStrength;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode()
@ConfirmPasswords
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty(message = "Please enter your name")
    @Column(nullable = false, unique = true)
    @NonWhitespace(message = "Your name cannot contain whitespaces")
    private String nickName;

    @NonNull
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Please enter mail")
    @NonWhitespace(message = "Your email cannot contain whitespaces")
    private String email;

    @NonNull
    @NotEmpty(message = "Please enter password")
    @PasswordStrength
    @NonWhitespace(message = "Your password cannot contain whitespaces")
    private String password;

    @Transient
    @NotEmpty(message = "Please enter password confirmation")
    private String confirmPassword;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "user", orphanRemoval = true)
    private List<Survey> surveys = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void addSurvey(Survey survey) {
        surveys.add(survey);
    }

    public void addSurveys(List<Survey> surveys) {
        surveys.forEach(this::addSurvey);
    }

    public User() {
    }

    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public User(@NonNull String email, @NonNull String password, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

}
