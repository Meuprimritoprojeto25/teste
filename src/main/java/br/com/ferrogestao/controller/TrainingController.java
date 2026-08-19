package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.Driver;
import br.com.ferrogestao.domain.DriverTraining;
import br.com.ferrogestao.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/treinamentos")
public class TrainingController {
    @Autowired private TrainingService service;
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("trainings", service.list()); model.addAttribute("drivers", service.drivers());
        model.addAttribute("expiring", service.expiring()); return "training/list";
    }
    @RequestMapping(value = "/motorista/novo", method = RequestMethod.GET)
    public String driver(Model model) { model.addAttribute("driver", new Driver()); return "training/driver-form"; }
    @RequestMapping(value = "/motorista/salvar", method = RequestMethod.POST)
    public String saveDriver(@ModelAttribute Driver driver, RedirectAttributes redirect) {
        service.saveDriver(driver); redirect.addFlashAttribute("message", "Motorista cadastrado."); return "redirect:/treinamentos";
    }
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String training(Model model) {
        model.addAttribute("training", new DriverTraining()); model.addAttribute("drivers", service.drivers());
        return "training/form";
    }
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String saveTraining(@ModelAttribute DriverTraining training, RedirectAttributes redirect) {
        service.saveTraining(training); redirect.addFlashAttribute("message", "Treinamento registrado."); return "redirect:/treinamentos";
    }
}