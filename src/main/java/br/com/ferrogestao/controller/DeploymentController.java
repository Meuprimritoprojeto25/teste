package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.Deployment;
import br.com.ferrogestao.service.DeploymentService;
import br.com.ferrogestao.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/desdobramentos")
public class DeploymentController {
    @Autowired private DeploymentService service;
    @Autowired private IndicatorService indicatorService;
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) { model.addAttribute("deployments", service.list()); return "deployments/list"; }
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("deployment", new Deployment()); model.addAttribute("items", indicatorService.listItems());
        return "deployments/form";
    }
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String save(@ModelAttribute Deployment deployment, RedirectAttributes redirect) {
        service.save(deployment); redirect.addFlashAttribute("message", "Desdobramento criado."); return "redirect:/desdobramentos";
    }
}