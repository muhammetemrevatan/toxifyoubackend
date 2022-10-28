package com.memrevatan.toxifyou.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.memrevatan.toxifyou.core.annotation.UniqueUsername;
import com.memrevatan.toxifyou.core.jsonView.BaseView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(BaseView.Base.class)
    private long id;
    @NotNull(message = "{toxifyou.constraint.username.NotNull.message}")
    @Size(min = 6, max = 32)
    @Column(name = "username")
    @UniqueUsername
    @JsonView(BaseView.Base.class)
    private String username;
    @NotNull
    @Size(min = 4, max = 32)
    @Column(name = "display_name", unique = true)
    @JsonView(BaseView.Base.class)
    private String displayName;
    @NotNull
    @Size(min = 8, max = 64)
    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{toxifyou.constraint.username.Pattern.message}")
    @JsonView(BaseView.Base.Sensitive.class)
    private String password;
    @JsonView(BaseView.Base.class)
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
