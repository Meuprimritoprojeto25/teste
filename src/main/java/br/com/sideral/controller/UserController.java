package br.com.sideral.controller;
import br.com.sideral.domain.User; import br.com.sideral.service.UserService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/usuarios") public class UserController {
 @Autowired private UserService service;
 @RequestMapping(method=RequestMethod.GET) public String list(Model m){m.addAttribute("users",service.list());return "users/list";}
 @RequestMapping(value="/novo",method=RequestMethod.GET) public String form(Model m){m.addAttribute("user",new User());return "users/form";}
 @RequestMapping(method=RequestMethod.POST) public String save(@ModelAttribute User user){service.save(user);return "redirect:/usuarios";}
}