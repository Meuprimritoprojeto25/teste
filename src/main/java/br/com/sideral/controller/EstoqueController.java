package br.com.sideral.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.sideral.service.EstoqueService;

@WebServlet(urlPatterns = {"/estoque"})
public class EstoqueController extends ControladorBase {
    private final EstoqueService service=new EstoqueService();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        prepararMensagem(req); req.setAttribute("itens",service.listar()); pagina(req,resp,"estoque.jsp");
    }
    protected void doPost(final HttpServletRequest req,HttpServletResponse resp)throws IOException {
        executarPost(req,resp,new OperacaoPost(){public void executar(){
            service.cadastrar(req.getParameter("descricao"),req.getParameter("categoria"),decimal(req,"quantidade"),decimal(req,"minimo"));
        }},"/estoque");
    }
}