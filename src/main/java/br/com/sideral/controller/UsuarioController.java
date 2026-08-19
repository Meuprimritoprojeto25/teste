package br.com.sideral.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.sideral.service.UsuarioService;

@WebServlet(urlPatterns = {"/usuarios"})
public class UsuarioController extends ControladorBase {
    private final UsuarioService service=new UsuarioService();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        prepararMensagem(req); req.setAttribute("usuarios",service.listar()); pagina(req,resp,"usuarios.jsp");
    }
    protected void doPost(final HttpServletRequest req,HttpServletResponse resp)throws IOException {
        final String acao=req.getParameter("acao");
        executarPost(req,resp,new OperacaoPost(){public void executar(){
            if("alternar".equals(acao)) service.alternarAtivacao(id(req));
            else service.cadastrar(req.getParameter("nome"),req.getParameter("matricula"),req.getParameter("email"),req.getParameter("perfil"));
        }},"/usuarios");
    }
}