package com.storeeverythin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.storeeverythin.model.UserEntity;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@GetMapping
    public String dashboard(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal(); // Pobranie obiektu UserEntity z Authentication
        System.out.print(user.getUsername());
        model.addAttribute("user", user.getUsername()); // Przekazanie obiektu UserEntity do modelu
        return "dashboard";
    }
}