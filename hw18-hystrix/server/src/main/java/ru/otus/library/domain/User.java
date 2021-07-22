package ru.otus.library.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("User")
public class User implements UserDetails {

    @EqualsAndHashCode.Include
    @Id
    private String id;
    @Field("password")
    private String password;
    @Field("username")
    private String username;
    @Field("authorities")
    private Set<GrantedAuthority> authorities = new HashSet<>();
    @Field("accountNonExpired")
    private boolean accountNonExpired;
    @Field("accountNonLocked")
    private boolean accountNonLocked;
    @Field("credentialsNonExpired")
    private boolean credentialsNonExpired;
    @Field("enabled")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
