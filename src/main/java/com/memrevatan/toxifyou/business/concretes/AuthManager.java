package com.memrevatan.toxifyou.business.concretes;

import com.memrevatan.toxifyou.business.abstracts.AuthService;
import com.memrevatan.toxifyou.business.abstracts.UserService;
import com.memrevatan.toxifyou.core.httpResponse.error.AuthException;
import com.memrevatan.toxifyou.core.httpResponse.error.NotFoundException;
import com.memrevatan.toxifyou.dataAccess.UserDao;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.AuthResponse;
import com.memrevatan.toxifyou.entities.userViewModel.Credentials;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse handleAuthentication(Credentials credentials) {
        User user;
        try {
            user = userService.getByUsername(credentials.getUsername());
        } catch (Exception e) {
            throw new AuthException();
        }
        boolean mc = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if(!mc) {
            throw new AuthException();
        }
        UserDto userDto = new UserDto(user);
        String token = Jwts.builder().setSubject(""+user.getId()).signWith(SignatureAlgorithm.HS512, "toxify").compact();
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserDto(userDto);
        return response;
    }

    @Override
    public UserDetails getUserDetails(String token) {
        JwtParser parser = Jwts.parser().setSigningKey("toxify");
        try {
            parser.parse(token);
            Claims claims = parser.parseClaimsJws(token).getBody();
            long userId = Long.parseLong(claims.getSubject());
            Optional<User> optionalUser = userService.getOneUser(userId);
            if (optionalUser.isEmpty()) {
                throw new AuthException();
            }
            return optionalUser.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
