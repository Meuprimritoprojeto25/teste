package br.com.sideral.controller;
import br.com.sideral.service.R3GReportService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/r3g") public class R3GController {
 @Autowired private R3GReportService service;
 @RequestMapping(method=RequestMethod.GET) public String list(Model m){m.addAttribute("reports",service.list());return "report/r3g";}
 @RequestMapping(method=RequestMethod.POST) public String generate(@RequestParam String period,@RequestParam String area,@RequestParam String analysis,@RequestParam String decisions){service.generate(period,area,analysis,decisions);return "redirect:/r3g";}
}