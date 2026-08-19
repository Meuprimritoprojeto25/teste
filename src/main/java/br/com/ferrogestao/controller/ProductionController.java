package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.ProductionRecord;
import br.com.ferrogestao.service.ProductionService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producao")
public class ProductionController {
    @Autowired private ProductionService service;
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("records", service.list()); model.addAttribute("totals", service.totalsByPlant());
        return "production/list";
    }
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String create(Model model) {
        ProductionRecord record = new ProductionRecord(); record.setProductionDate(new Date());
        model.addAttribute("record", record); return "production/form";
    }
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String save(@ModelAttribute ProductionRecord record, RedirectAttributes redirect) {
        service.save(record); redirect.addFlashAttribute("message", "Turno de produção apontado."); return "redirect:/producao";
    }
}