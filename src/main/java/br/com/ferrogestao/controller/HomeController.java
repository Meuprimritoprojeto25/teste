package br.com.ferrogestao.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Entry point for the browser preview.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public void index(ServletContext servletContext, HttpServletResponse response) throws IOException {
        InputStream index = servletContext.getResourceAsStream("/index.html");
        if (index == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        try {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = index.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, read);
            }
        } finally {
            index.close();
        }
    }
}