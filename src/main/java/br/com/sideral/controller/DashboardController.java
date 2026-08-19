package br.com.sideral.controller;
import br.com.sideral.service.DashboardService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.RequestMapping; import org.springframework.web.bind.annotation.RequestMethod;
@Controller public class DashboardController {
 @Autowired private DashboardService dashboard;
 @RequestMapping(value={"/","/dashboard"},method=RequestMethod.GET) public String home(Model model){model.addAllAttributes(dashboard.indicators());return "dashboard";}
}