package com.endava.wallet.controller;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import com.endava.wallet.exception.ApiRequestException;
import com.endava.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "userList";
    }

    @GetMapping("{userID}")
    public String userEditForm(@PathVariable Long userID, Model model) {
        User user = userService.findUserById(userID);
        if (user == null)
            throw new ApiRequestException("There is no user with such id in database");
        model.addAttribute("user", user);
        model.addAttribute("authorities", Authority.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam Long id
    ) {
        User user = userService.findUserById(id);
        if (user == null)
            throw new ApiRequestException("There is no user with id = " + id + " in database");
        userService.add(username, form, userService.findUserById(id));
        return "redirect:/user";
    }

    @GetMapping("/delete/{userID}")
    public String userDelete(@PathVariable Long userID) {
        userService.deleteUserById(userID);
        return "redirect:/user";
    }
}
