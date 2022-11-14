package com.endava.wallet.controller;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.User;
import com.endava.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("authorities", Authority.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);
        Set<String> authorities = Arrays.stream(Authority.values())
                .map(Authority::name)
                .collect(Collectors.toSet());

        user.getAuthority().clear();

        for (String key : form.keySet()) {
            if (authorities.contains(key)) {
                user.getAuthority().add(Authority.valueOf(key));
            }
        }


        userService.save(user);

        return "redirect:/user";
    }

    @GetMapping("/delete/{userID}")
    public String transactionDelete(@PathVariable Long userID) {

        userService.deleteById(userID);

        return "redirect:/user";
    }
}
