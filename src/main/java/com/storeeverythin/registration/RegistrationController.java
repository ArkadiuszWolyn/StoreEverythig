package com.storeeverythin.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@ModelAttribute("registrationRequest") RegistrationRequest request, Model model) {
        String result = registrationService.register(request);
        model.addAttribute("message", result);
        return "registrationResult"; // Nazwa widoku (np. registrationResult.html)
    }

    @GetMapping
    public String register(Model model) {
    	model.addAttribute("registrationRequest", new RegistrationRequest());
        return "registrationForm";
    }
}