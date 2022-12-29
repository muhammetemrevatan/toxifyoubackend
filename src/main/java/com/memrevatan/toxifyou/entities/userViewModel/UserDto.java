package com.memrevatan.toxifyou.entities.userViewModel;

import com.memrevatan.toxifyou.entities.User;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String displayName;
    private String image;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.image = user.getImage();
    }
}
