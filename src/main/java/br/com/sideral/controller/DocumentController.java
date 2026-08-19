package br.com.sideral.controller;
import br.com.sideral.domain.DocumentRecord; import br.com.sideral.service.DocumentService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/documentos") public class DocumentController {
 @Autowired private DocumentService service;
 @RequestMapping(method=RequestMethod.GET) public String list(Model m){m.addAttribute("documents",service.list());return "document/list";}
 @RequestMapping(value="/novo",method=RequestMethod.GET) public String form(Model m){m.addAttribute("document",new DocumentRecord());return "document/form";}
 @RequestMapping(value="/{id}/editar",method=RequestMethod.GET) public String edit(@PathVariable Long id,Model m){m.addAttribute("document",service.get(id));return "document/form";}
 @RequestMapping(method=RequestMethod.POST) public String save(@ModelAttribute DocumentRecord document){service.save(document);return "redirect:/documentos";}
}