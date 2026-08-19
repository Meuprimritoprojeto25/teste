package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.ControlItem;
import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.enums.Direction;
import br.com.ferrogestao.service.IndicatorService;
import br.com.ferrogestao.service.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndicatorController {
    @Autowired private IndicatorService service;
    @Autowired private UserService userService;

    @RequestMapping(value = "/itens-controle", method = RequestMethod.GET)
    public String items(Model model) { model.addAttribute("items", service.listItems()); return "indicators/list"; }
    @RequestMapping(value = "/itens-controle/novo", method = RequestMethod.GET)
    public String newItem(Model model) { prepareItemForm(model, new ControlItem()); return "indicators/form"; }
    @RequestMapping(value = "/itens-controle/{id}/editar", method = RequestMethod.GET)
    public String editItem(@PathVariable Long id, Model model) { prepareItemForm(model, service.getItem(id)); return "indicators/form"; }
    @RequestMapping(value = "/itens-controle/salvar", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute ControlItem item, RedirectAttributes redirect) {
        service.saveItem(item); redirect.addFlashAttribute("message", "Item de controle salvo."); return "redirect:/itens-controle";
    }
    @RequestMapping(value = "/acompanhamentos", method = RequestMethod.GET)
    public String followUps(Model model) {
        model.addAttribute("followUps", service.latest(200)); model.addAttribute("lights", service.statusCountsByName());
        return "followups/list";
    }
    @RequestMapping(value = "/acompanhamentos/novo", method = RequestMethod.GET)
    public String newFollowUp(Model model) {
        FollowUp followUp = new FollowUp(); followUp.setReferenceDate(new Date());
        model.addAttribute("followUp", followUp); model.addAttribute("items", service.listItems());
        return "followups/form";
    }
    @RequestMapping(value = "/acompanhamentos/salvar", method = RequestMethod.POST)
    public String saveFollowUp(@ModelAttribute FollowUp followUp, RedirectAttributes redirect) {
        service.record(followUp);
        redirect.addFlashAttribute("message", "Apontamento registrado e KPI recalculado.");
        return "redirect:/acompanhamentos";
    }
    private void prepareItemForm(Model model, ControlItem item) {
        model.addAttribute("item", item); model.addAttribute("users", userService.list());
        model.addAttribute("directions", Direction.values());
    }
}