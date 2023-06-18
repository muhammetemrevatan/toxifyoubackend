package com.memrevatan.toxifyou.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.memrevatan.toxifyou.core.annotation.UniqueUsername;
//import com.memrevatan.toxifyou.core.jsonView.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
//    @JsonView(BaseView.Base.Sensitive.class)
    private long id;

    @NotNull(message = "{toxifyou.constraint.username.NotNull.message}")
    @Size(min = 6, max = 32)
    @Column(name = "username")
    @UniqueUsername
//    @JsonView(BaseView.Base.class)
    private String username;

    @NotNull
    @Size(min = 4, max = 32)
    @Column(name = "display_name", unique = true)
//    @JsonView(BaseView.Base.class)
    private String displayName;

    @NotNull
    @Size(min = 8, max = 64)
    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{toxifyou.constraint.username.Pattern.message}")
//    @JsonView(BaseView.Base.Sensitive.class)
    private String password;

//    @JsonView(BaseView.Base.class)

//    @JsonView(BaseView.Base.class)
//    @Lob // column limiti büyük obje tipplerini tutacak sekilde ayarlanır. 255 karakteri geçebilir.
    private String image;
    @NotNull(message = "{toxifyou.constraint.email.NotNull.message}")
    private String email;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_user");
    }

}
