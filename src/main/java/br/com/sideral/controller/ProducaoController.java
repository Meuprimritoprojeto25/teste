package br.com.sideral.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.sideral.service.ProducaoService;

@WebServlet(urlPatterns = {"/producao"})
public class ProducaoController extends ControladorBase {
    private final ProducaoService service=new ProducaoService();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        prepararMensagem(req); req.setAttribute("producoes",service.recentes()); pagina(req,resp,"producao.jsp");
    }
    protected void doPost(final HttpServletRequest req,HttpServletResponse resp)throws IOException {
        executarPost(req,resp,new OperacaoPost(){public void executar(){
            service.apontar(data(req,"data"),req.getParameter("forno"),decimal(req,"toneladas"),decimal(req,"qualidade"));
        }},"/producao");
    }
}