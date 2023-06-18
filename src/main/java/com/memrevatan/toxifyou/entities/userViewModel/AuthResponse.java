package com.memrevatan.toxifyou.entities.userViewModel;

import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private UserDto userDto;

}
