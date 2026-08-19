package br.com.sideral.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.sideral.service.IndicadorService;

@WebServlet(urlPatterns = {"/indicadores"})
public class IndicadorController extends ControladorBase {
    private final IndicadorService service=new IndicadorService();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        prepararMensagem(req); req.setAttribute("indicadores",service.listar()); pagina(req,resp,"indicadores.jsp");
    }
    protected void doPost(final HttpServletRequest req,HttpServletResponse resp)throws IOException {
        final String acao=req.getParameter("acao");
        executarPost(req,resp,new OperacaoPost(){public void executar(){
            if("apontar".equals(acao)) service.apontar(id(req),decimal(req,"valor"));
            else service.criar(req.getParameter("nome"),req.getParameter("area"),decimal(req,"meta"),decimal(req,"atual"));
        }},"/indicadores");
    }
}