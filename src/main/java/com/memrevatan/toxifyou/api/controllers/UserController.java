package com.memrevatan.toxifyou.api.controllers;

import com.memrevatan.toxifyou.business.abstracts.UserService;
import com.memrevatan.toxifyou.core.annotation.CurrentUser;
import com.memrevatan.toxifyou.entities.User;
import com.memrevatan.toxifyou.entities.userViewModel.UserDisplayNameDto;
import com.memrevatan.toxifyou.entities.userViewModel.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/1.0")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        return this.userService.createUser(user);
    }

    @GetMapping("/users")
//    @JsonView(BaseView.Base.class)
    public Page<UserDto> getUsers(Pageable page, @CurrentUser User user) {
        return userService.getUsers(page, user).map(UserDto::new);
    }

    @GetMapping("/users/{username}")
    public UserDto getUser(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return new UserDto(user);
    }

    @PutMapping("/users/{username}")
//  @PreAuthorize("#username == #loggedInUser.username") // @CurrentUser User loggedInUser -> bunu parametrelerden kaldırdır gerek kalmadı.
    /*
     * Burası için SecurityConfiguration içerisine @EnableGlobalMethodSecurity(prePostEnabled = true) ekledik
     * ++NOT: spring 3 version üzeri ise EnableMethodSecurity yazmalısın.
     *
     * PreAuthorize -> Yetki vermeden önce bunu kontrol et gibi bir işlevi var.
     * Eğer koşul sağlanırsa otomatik olarak error mesajını veya success mesajını ayarlıyor.
     * Daha önce spring'in default davranışını (hata mesajları için) değiştirdiğim için istediğim formatta cevap veriyor.
     * ResponseEntity ve koşullu işleme gerek kalmıyor. Aynı zamanda geri dönecek cevabı UserDto olarak değiştirdin.
     * @CurrentUser User loggedInUser parametresi kaldırıldı. Bu kontrol 'principal.username' ile sağlandı.
     * Login olan kullanıcı username ve password bilgisine principal ile ulaşabiliyoruz. (UserDetails için aynısı yapılmıştı.)
     * */
    @PreAuthorize("#username == principal.username") // Bu anatastonun yakalanabilmesi için configuration class'ında tek bir satır annotation eklendi.
    public UserDto updatedUser(@RequestBody UserDisplayNameDto userDisplayNameDto, @PathVariable String username) {
        return new UserDto(userService.updatedUser(userDisplayNameDto, username));
    }

}
