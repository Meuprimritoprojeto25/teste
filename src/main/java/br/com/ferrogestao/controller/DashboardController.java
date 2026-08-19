package br.com.ferrogestao.controller;

import br.com.ferrogestao.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {
    @Autowired private DashboardService dashboardService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String dashboard(Model model) {
        model.addAllAttributes(dashboardService.summary());
        model.addAttribute("recent", dashboardService.recent());
        return "dashboard";
    }
}