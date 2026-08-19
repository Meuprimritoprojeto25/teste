package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.R3GReport;
import br.com.ferrogestao.domain.enums.RecordStatus;
import br.com.ferrogestao.service.R3GService;
import br.com.ferrogestao.service.ReportService;
import br.com.ferrogestao.service.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/r3g")
public class R3GController {
    @Autowired private R3GService service;
    @Autowired private ReportService reportService;
    @Autowired private UserService userService;
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) { model.addAttribute("reports", service.list()); return "r3g/list"; }
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String create(Model model) { prepare(model, new R3GReport()); return "r3g/form"; }
    @RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) { prepare(model, service.get(id)); return "r3g/form"; }
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String save(@ModelAttribute R3GReport report, RedirectAttributes redirect) {
        service.save(report); redirect.addFlashAttribute("message", "Relatório R3G salvo."); return "redirect:/r3g";
    }
    @RequestMapping(value = "/{id}/word", method = RequestMethod.GET)
    public void word(@PathVariable Long id, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-r3g-" + id + ".docx");
        response.getOutputStream().write(reportService.r3gWord(id));
    }
    @RequestMapping(value = "/{id}/pdf", method = RequestMethod.GET)
    public void pdf(@PathVariable Long id, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-r3g-" + id + ".pdf");
        response.getOutputStream().write(reportService.r3gPdf(id));
    }
    @RequestMapping(value = "/{id}/excel", method = RequestMethod.GET)
    public void excel(@PathVariable Long id, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-r3g-" + id + ".xlsx");
        response.getOutputStream().write(reportService.r3gExcel(id));
    }
    private void prepare(Model model, R3GReport value) {
        model.addAttribute("report", value); model.addAttribute("users", userService.list());
        model.addAttribute("statuses", RecordStatus.values());
    }
}