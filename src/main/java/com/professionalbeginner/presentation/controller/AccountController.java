package com.professionalbeginner.presentation.controller;

import com.professionalbeginner.domain.applicationlayer.UserService;
import com.professionalbeginner.domain.applicationlayer.exception.ExistingUserException;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.core.user.UserInfo;
import com.professionalbeginner.presentation.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Kempenich Florian
 */
@RestController
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO addNewUser(@RequestBody @Valid UserDTO userDTO) throws ExistingUserException {
        UserInfo userInfo = new UserInfo(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getBirthdate()
        );
        UserId username = new UserId(userDTO.getUsername());

        userService.createNewUser(username, userInfo);

        return userDTO;
    }

}
