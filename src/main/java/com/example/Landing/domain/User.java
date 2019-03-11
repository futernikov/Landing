package com.example.Landing.domain;

import com.example.Landing.utils.AuthHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String[] grantedAuthorities;
    private String firstName;
    private String lastName;

    public static User of(UserInDTO data) {
        return new User(data.getUsername(), data.getPassword(),
                data.getRole(), data.getEmail(), data.getFirstName(),
                data.getLastName());
    }

    public User(String username, String password, Role grantedAuthorities, String email, String firstName,
                String lastName) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = AuthHelper.convertToAuthorities(grantedAuthorities);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static User merge(User fromDb, UserInDTO data) {
        fromDb.setEmail(data.getEmail());
        fromDb.setFirstName(data.getFirstName());
        fromDb.setLastName(data.getLastName());
        fromDb.setGrantedAuthorities(new String[] {data.getRole().name()});
        return fromDb;
    }

    public User(Long id, String username, String password, String[] grantedAuthorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public User(String username, String password, List<Role> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = AuthHelper.convertToAuthorities(grantedAuthorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(grantedAuthorities);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}
