package br.com.sideral.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.sideral.service.PainelService;

@WebServlet(urlPatterns = {"/painel"})
public class PainelController extends ControladorBase {
    private final PainelService service=new PainelService();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepararMensagem(req); req.setAttribute("resumo", service.resumo()); pagina(req,resp,"painel.jsp");
    }
}