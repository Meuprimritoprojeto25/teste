package br.com.sideral.controller;
import java.text.SimpleDateFormat; import br.com.sideral.domain.Deployment; import br.com.sideral.service.ControlItemService; import br.com.sideral.service.DeploymentService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.beans.propertyeditors.CustomDateEditor; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.WebDataBinder; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/desdobramentos") public class DeploymentController {
 @Autowired private DeploymentService service; @Autowired private ControlItemService controlItems;
 @InitBinder public void bind(WebDataBinder b){b.registerCustomEditor(java.util.Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));}
 @RequestMapping(method=RequestMethod.GET) public String list(Model m){m.addAttribute("deployments",service.listOpen());return "deployment/list";}
 @RequestMapping(value="/novo",method=RequestMethod.GET) public String form(Model m){m.addAttribute("deployment",new Deployment());m.addAttribute("items",controlItems.list());return "deployment/form";}
 @RequestMapping(method=RequestMethod.POST) public String save(@ModelAttribute Deployment deployment){service.save(deployment);return "redirect:/desdobramentos";}
}