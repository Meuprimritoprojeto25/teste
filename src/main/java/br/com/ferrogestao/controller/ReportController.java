package br.com.ferrogestao.controller;

import br.com.ferrogestao.service.ReportService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class ReportController {
    @Autowired private ReportService service;

    @RequestMapping(value = "/relatorios", method = RequestMethod.GET)
    public String index() { return "reports/index"; }

    @RequestMapping(value = "/relatorios/farol.{format}", method = RequestMethod.GET)
    public void trafficLight(@PathVariable String format, HttpServletResponse response) throws Exception {
        byte[] bytes;
        if ("pdf".equals(format)) {
            response.setContentType("application/pdf"); bytes = service.trafficLightPdf();
        } else if ("xlsx".equals(format)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            bytes = service.trafficLightExcel();
        } else if ("docx".equals(format)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            bytes = service.trafficLightWord();
        } else { response.sendError(404); return; }
        response.setHeader("Content-Disposition", "attachment; filename=farol-gerencial." + format);
        response.setContentLength(bytes.length); response.getOutputStream().write(bytes);
    }

    @RequestMapping(value = "/graficos/farol.{format}", method = RequestMethod.GET)
    public void chart(@PathVariable String format, HttpServletResponse response) throws Exception {
        byte[] bytes;
        if ("svg".equals(format)) { response.setContentType("image/svg+xml"); bytes = service.chartSvg(); }
        else if ("jpg".equals(format)) { response.setContentType("image/jpeg"); bytes = service.chart("jpg"); }
        else { response.setContentType("image/png"); bytes = service.chart("png"); }
        response.setContentLength(bytes.length); response.getOutputStream().write(bytes);
    }
}