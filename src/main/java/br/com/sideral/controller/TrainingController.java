package br.com.sideral.controller;
import br.com.sideral.domain.DriverTraining; import br.com.sideral.service.TrainingService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.WebDataBinder; import org.springframework.web.bind.annotation.*; import org.springframework.beans.propertyeditors.CustomDateEditor; import java.text.SimpleDateFormat;
@Controller @RequestMapping("/treinamentos") public class TrainingController {
 @Autowired private TrainingService service;
 @InitBinder public void bind(WebDataBinder binder){binder.registerCustomEditor(java.util.Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));}
 @RequestMapping(method=RequestMethod.GET) public String list(Model m){m.addAttribute("trainings",service.list());m.addAttribute("expired",service.expired().size());return "training/list";}
 @RequestMapping(value="/novo",method=RequestMethod.GET) public String form(Model m){m.addAttribute("training",new DriverTraining());return "training/form";}
 @RequestMapping(method=RequestMethod.POST) public String save(@ModelAttribute DriverTraining training){service.save(training);return "redirect:/treinamentos";}
}