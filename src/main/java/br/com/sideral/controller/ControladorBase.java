package br.com.sideral.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.sideral.service.RegraNegocioException;

public abstract class ControladorBase extends HttpServlet {
    protected void pagina(HttpServletRequest req, HttpServletResponse resp, String arquivo) throws ServletException, IOException {
        RequestDispatcher dispatcher=req.getRequestDispatcher("/WEB-INF/jsp/"+arquivo); dispatcher.forward(req, resp);
    }
    protected void mensagem(HttpServletRequest req, String texto) { req.getSession().setAttribute("mensagem", texto); }
    protected void redirecionar(HttpServletRequest req, HttpServletResponse resp, String rota) throws IOException { resp.sendRedirect(req.getContextPath()+rota); }
    protected void prepararMensagem(HttpServletRequest req) { Object m=req.getSession().getAttribute("mensagem"); if(m != null){req.setAttribute("mensagem",m);req.getSession().removeAttribute("mensagem");} }
    protected Long id(HttpServletRequest req) { try { return Long.valueOf(req.getParameter("id")); } catch (Exception e) { throw new RegraNegocioException("Identificador inválido."); } }
    protected BigDecimal decimal(HttpServletRequest req, String campo) { try { return new BigDecimal(req.getParameter(campo).replace(",", ".")); } catch(Exception e){throw new RegraNegocioException("Valor inválido para "+campo+".");} }
    protected Date data(HttpServletRequest req, String campo) {
        try { return new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter(campo)); }
        catch(Exception e){throw new RegraNegocioException("Data inválida.");}
    }
    protected void executarPost(HttpServletRequest req, HttpServletResponse resp, OperacaoPost operacao, String rota) throws IOException {
        try { operacao.executar(); mensagem(req, "Registro salvo com sucesso."); } catch(RegraNegocioException e) { mensagem(req, e.getMessage()); }
        redirecionar(req,resp,rota);
    }
    protected interface OperacaoPost { void executar(); }
}