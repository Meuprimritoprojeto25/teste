package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.User;
import br.com.ferrogestao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UserController {
    @Autowired private UserService service;
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) { model.addAttribute("users", service.list()); return "users/list"; }
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String create(Model model) { model.addAttribute("user", new User()); return "users/form"; }
    @RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) { model.addAttribute("user", service.get(id)); return "users/form"; }
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String save(@ModelAttribute User user, @RequestParam(required = false) String password, RedirectAttributes redirect) {
        service.save(user, password);
        redirect.addFlashAttribute("message", "Usuário salvo com sucesso.");
        return "redirect:/usuarios";
    }
}