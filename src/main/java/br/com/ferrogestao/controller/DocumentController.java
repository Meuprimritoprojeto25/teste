package br.com.ferrogestao.controller;

import br.com.ferrogestao.domain.ManagedDocument;
import br.com.ferrogestao.domain.enums.RecordStatus;
import br.com.ferrogestao.service.DocumentService;
import br.com.ferrogestao.service.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/documentos")
public class DocumentController {
    @Autowired private DocumentService service;
    @Autowired private UserService userService;
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) { model.addAttribute("documents", service.list()); return "documents/list"; }
    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String create(Model model) { prepare(model, new ManagedDocument()); return "documents/form"; }
    @RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        prepare(model, service.get(id)); model.addAttribute("revisions", service.revisions(id)); return "documents/form";
    }
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String save(@ModelAttribute ManagedDocument document, @RequestParam(required = false) String changeReason,
                       RedirectAttributes redirect) {
        service.save(document, changeReason, null);
        redirect.addFlashAttribute("message", "Documento salvo e revisão registrada."); return "redirect:/documentos";
    }
    @RequestMapping(value = "/{id}/word", method = RequestMethod.GET)
    public void word(@PathVariable Long id, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=documento-" + id + ".docx");
        response.getOutputStream().write(service.asWord(id));
    }
    private void prepare(Model model, ManagedDocument document) {
        model.addAttribute("document", document); model.addAttribute("users", userService.list());
        model.addAttribute("statuses", RecordStatus.values());
    }
}