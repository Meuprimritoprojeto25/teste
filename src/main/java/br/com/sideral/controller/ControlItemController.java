package br.com.sideral.controller;
import java.math.BigDecimal; import br.com.sideral.domain.ControlItem; import br.com.sideral.service.ControlItemService; import br.com.sideral.service.FollowUpService; import br.com.sideral.service.TrafficLightService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/itens-controle") public class ControlItemController {
 @Autowired private ControlItemService service; @Autowired private TrafficLightService traffic; @Autowired private FollowUpService followUps;
 @RequestMapping(method=RequestMethod.GET) public String list(Model m){m.addAttribute("items",service.list());return "control/list";}
 @RequestMapping(value="/novo",method=RequestMethod.GET) public String form(Model m){m.addAttribute("item",new ControlItem());return "control/form";}
 @RequestMapping(method=RequestMethod.POST) public String save(@ModelAttribute ControlItem item){service.save(item);return "redirect:/itens-controle";}
 @RequestMapping(value="/{id}",method=RequestMethod.GET) public String detail(@PathVariable Long id,Model m){m.addAttribute("item",service.get(id));m.addAttribute("followUps",followUps.list(id));return "control/detail";}
 @RequestMapping(value="/{id}/leitura",method=RequestMethod.POST) public String reading(@PathVariable Long id,@RequestParam BigDecimal value){traffic.registerReading(id,value);return "redirect:/itens-controle/"+id;}
 @RequestMapping(value="/{id}/acompanhamento",method=RequestMethod.POST) public String follow(@PathVariable Long id,@RequestParam String note,@RequestParam String author){followUps.register(id,note,author);return "redirect:/itens-controle/"+id;}
}