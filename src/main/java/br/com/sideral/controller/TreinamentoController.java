package br.com.sideral.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.sideral.service.TreinamentoService;

@WebServlet(urlPatterns = {"/treinamentos"})
public class TreinamentoController extends ControladorBase {
    private final TreinamentoService service=new TreinamentoService();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        prepararMensagem(req); req.setAttribute("treinamentos",service.listar()); pagina(req,resp,"treinamentos.jsp");
    }
    protected void doPost(final HttpServletRequest req,HttpServletResponse resp)throws IOException {
        executarPost(req,resp,new OperacaoPost(){public void executar(){
            service.registrar(req.getParameter("motorista"),req.getParameter("curso"),data(req,"validade"));
        }},"/treinamentos");
    }
}