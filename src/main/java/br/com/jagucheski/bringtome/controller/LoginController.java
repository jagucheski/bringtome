package br.com.jagucheski.bringtome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
    
    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
      model.addAttribute("loginError", true);
      return "login";
    }

    @GetMapping("403")
    public String error403() {
    	return "error/403";
    }

    
}
