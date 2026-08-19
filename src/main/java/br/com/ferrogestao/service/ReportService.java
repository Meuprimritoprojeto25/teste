package br.com.ferrogestao.service;

import br.com.ferrogestao.domain.FollowUp;
import br.com.ferrogestao.domain.R3GReport;
import br.com.ferrogestao.domain.Deployment;
import br.com.ferrogestao.domain.enums.TrafficStatus;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Paint;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {
    @Autowired private IndicatorService indicatorService;
    @Autowired private R3GService r3gService;
    @Autowired private DeploymentService deploymentService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final DecimalFormat numberFormat = new DecimalFormat("#,##0.00");

    @Transactional(readOnly = true)
    public byte[] trafficLightPdf() {
        List<FollowUp> rows = indicatorService.latest(500);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            Paragraph title = new Paragraph("Relatório gerencial de farol", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Acompanhamento dos itens de controle da operação siderúrgica"));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(new float[] {1.3f, 3.8f, 1.4f, 1.4f, 1.4f});
            table.setWidthPercentage(100);
            header(table, "Código"); header(table, "Indicador"); header(table, "Meta");
            header(table, "Realizado"); header(table, "Farol");
            for (FollowUp value : rows) {
                table.addCell(value.getItem().getCode());
                table.addCell(value.getItem().getName());
                table.addCell(numberFormat.format(value.getItem().getTarget()));
                table.addCell(numberFormat.format(value.getActualValue()));
                PdfPCell status = new PdfPCell(new Phrase(value.getStatus().getLabel()));
                status.setBackgroundColor(pdfColor(value.getStatus()));
                table.addCell(status);
            }
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Desdobramento de metas", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));
            PdfPTable deploymentTable = new PdfPTable(new float[] {3f, 3f, 1f});
            deploymentTable.setWidthPercentage(100);
            header(deploymentTable, "Indicador pai"); header(deploymentTable, "Indicador filho"); header(deploymentTable, "Peso");
            for (Deployment deployment : deploymentService.list()) {
                deploymentTable.addCell(deployment.getParentItem().getCode() + " - " + deployment.getParentItem().getName());
                deploymentTable.addCell(deployment.getChildItem().getCode() + " - " + deployment.getChildItem().getName());
                deploymentTable.addCell(numberFormat.format(deployment.getWeight()) + "%");
            }
            document.add(deploymentTable);
            document.close();
            return out.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar relatório PDF", e); }
    }

    @Transactional(readOnly = true)
    public byte[] trafficLightExcel() {
        List<FollowUp> rows = indicatorService.latest(500);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Farol");
            String[] headers = {"Código", "Indicador", "Área", "Data", "Meta", "Realizado", "Atingimento %", "Farol", "Análise", "Plano de ação"};
            Row header = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern((short) 1);
            org.apache.poi.ss.usermodel.Font white = workbook.createFont();
            white.setColor(IndexedColors.WHITE.getIndex()); white.setBold(true);
            headerStyle.setFont(white);
            for (int i = 0; i < headers.length; i++) { Cell cell = header.createCell(i); cell.setCellValue(headers[i]); cell.setCellStyle(headerStyle); }
            int index = 1;
            for (FollowUp value : rows) {
                Row row = sheet.createRow(index++);
                row.createCell(0).setCellValue(value.getItem().getCode());
                row.createCell(1).setCellValue(value.getItem().getName());
                row.createCell(2).setCellValue(value.getItem().getArea());
                row.createCell(3).setCellValue(dateFormat.format(value.getReferenceDate()));
                row.createCell(4).setCellValue(value.getItem().getTarget());
                row.createCell(5).setCellValue(value.getActualValue());
                row.createCell(6).setCellValue(indicatorService.calculateAchievement(value.getItem(), value.getActualValue()));
                row.createCell(7).setCellValue(value.getStatus().getLabel());
                row.createCell(8).setCellValue(safe(value.getAnalysis()));
                row.createCell(9).setCellValue(safe(value.getActionPlan()));
            }
            for (int i = 0; i < headers.length; i++) { sheet.autoSizeColumn(i); }
            XSSFSheet deploymentSheet = workbook.createSheet("Desdobramentos");
            Row deploymentHeader = deploymentSheet.createRow(0);
            String[] deploymentHeaders = {"Indicador pai", "Nome do pai", "Indicador filho", "Nome do filho", "Peso %", "Justificativa"};
            for (int i = 0; i < deploymentHeaders.length; i++) {
                Cell cell = deploymentHeader.createCell(i); cell.setCellValue(deploymentHeaders[i]); cell.setCellStyle(headerStyle);
            }
            int deploymentIndex = 1;
            for (Deployment deployment : deploymentService.list()) {
                Row row = deploymentSheet.createRow(deploymentIndex++);
                row.createCell(0).setCellValue(deployment.getParentItem().getCode());
                row.createCell(1).setCellValue(deployment.getParentItem().getName());
                row.createCell(2).setCellValue(deployment.getChildItem().getCode());
                row.createCell(3).setCellValue(deployment.getChildItem().getName());
                row.createCell(4).setCellValue(deployment.getWeight());
                row.createCell(5).setCellValue(safe(deployment.getRationale()));
            }
            for (int i = 0; i < deploymentHeaders.length; i++) { deploymentSheet.autoSizeColumn(i); }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out); workbook.close();
            return out.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar relatório Excel", e); }
    }

    @Transactional(readOnly = true)
    public byte[] trafficLightWord() {
        List<FollowUp> rows = indicatorService.latest(500);
        try {
            XWPFDocument word = new XWPFDocument();
            XWPFParagraph title = word.createParagraph();
            title.setStyle("Title");
            title.createRun().setText("Relatório gerencial de farol");
            word.createParagraph().createRun().setText("Itens de controle, metas e acompanhamento consolidado.");
            XWPFTable table = word.createTable(rows.size() + 1, 5);
            String[] headers = {"Código", "Indicador", "Meta", "Realizado", "Farol"};
            for (int i = 0; i < headers.length; i++) { table.getRow(0).getCell(i).setText(headers[i]); }
            for (int r = 0; r < rows.size(); r++) {
                FollowUp value = rows.get(r);
                table.getRow(r + 1).getCell(0).setText(value.getItem().getCode());
                table.getRow(r + 1).getCell(1).setText(value.getItem().getName());
                table.getRow(r + 1).getCell(2).setText(numberFormat.format(value.getItem().getTarget()));
                table.getRow(r + 1).getCell(3).setText(numberFormat.format(value.getActualValue()));
                table.getRow(r + 1).getCell(4).setText(value.getStatus().getLabel());
            }
            XWPFParagraph deploymentTitle = word.createParagraph();
            deploymentTitle.createRun().setBold(true);
            deploymentTitle.createRun().setText("Desdobramento de metas");
            List<Deployment> deployments = deploymentService.list();
            XWPFTable deploymentTable = word.createTable(deployments.size() + 1, 3);
            deploymentTable.getRow(0).getCell(0).setText("Indicador pai");
            deploymentTable.getRow(0).getCell(1).setText("Indicador filho");
            deploymentTable.getRow(0).getCell(2).setText("Peso");
            for (int i = 0; i < deployments.size(); i++) {
                Deployment deployment = deployments.get(i);
                deploymentTable.getRow(i + 1).getCell(0).setText(deployment.getParentItem().getCode() + " - " + deployment.getParentItem().getName());
                deploymentTable.getRow(i + 1).getCell(1).setText(deployment.getChildItem().getCode() + " - " + deployment.getChildItem().getName());
                deploymentTable.getRow(i + 1).getCell(2).setText(numberFormat.format(deployment.getWeight()) + "%");
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            word.write(out); word.close();
            return out.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar relatório Word", e); }
    }

    @Transactional(readOnly = true)
    public byte[] chart(String format) {
        try {
            JFreeChart chart = createChart(indicatorService.statusCounts());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            if ("jpg".equals(format)) { ChartUtilities.writeChartAsJPEG(out, chart, 1100, 520); }
            else { ChartUtilities.writeChartAsPNG(out, chart, 1100, 520); }
            return out.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar gráfico", e); }
    }

    @Transactional(readOnly = true)
    public byte[] chartSvg() {
        Map<TrafficStatus, Long> counts = indicatorService.statusCounts();
        String[] names = {"Superado", "No alvo", "Atenção", "Crítico"};
        TrafficStatus[] statuses = {TrafficStatus.BLUE, TrafficStatus.GREEN, TrafficStatus.YELLOW, TrafficStatus.RED};
        String[] colors = {"#2563eb", "#16a34a", "#f59e0b", "#dc2626"};
        long max = 1;
        for (TrafficStatus status : statuses) { max = Math.max(max, counts.get(status)); }
        StringBuilder svg = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1100\" height=\"520\" viewBox=\"0 0 1100 520\">");
        svg.append("<rect width=\"1100\" height=\"520\" fill=\"#fff\"/><text x=\"50\" y=\"45\" font-size=\"24\" font-family=\"Arial\" fill=\"#17202a\">Distribuição do farol</text>");
        for (int i = 0; i < statuses.length; i++) {
            int height = (int) (counts.get(statuses[i]) * 350 / max);
            int x = 110 + i * 240;
            int y = 430 - height;
            svg.append("<rect x=\"").append(x).append("\" y=\"").append(y).append("\" width=\"130\" height=\"").append(height)
                    .append("\" rx=\"8\" fill=\"").append(colors[i]).append("\"/>");
            svg.append("<text x=\"").append(x + 65).append("\" y=\"").append(y - 12).append("\" text-anchor=\"middle\" font-size=\"22\">")
                    .append(counts.get(statuses[i])).append("</text>");
            svg.append("<text x=\"").append(x + 65).append("\" y=\"465\" text-anchor=\"middle\" font-size=\"17\" font-family=\"Arial\">")
                    .append(names[i]).append("</text>");
        }
        svg.append("</svg>");
        try { return svg.toString().getBytes("UTF-8"); } catch (Exception e) { throw new IllegalStateException(e); }
    }

    @Transactional(readOnly = true)
    public byte[] r3gWord(Long id) {
        R3GReport report = r3gService.get(id);
        if (report == null) { throw new BusinessException("R3G não encontrado."); }
        try {
            XWPFDocument word = new XWPFDocument();
            XWPFParagraph title = word.createParagraph(); title.setStyle("Title"); title.createRun().setText(report.getTitle());
            section(word, "Resultado", report.getResultSummary());
            section(word, "Gaps", report.getGaps());
            section(word, "Ganhos", report.getGains());
            section(word, "Próximos passos", report.getNextSteps());
            ByteArrayOutputStream out = new ByteArrayOutputStream(); word.write(out); word.close();
            return out.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar R3G", e); }
    }

    @Transactional(readOnly = true)
    public byte[] r3gPdf(Long id) {
        R3GReport report = r3gService.get(id);
        if (report == null) { throw new BusinessException("R3G não encontrado."); }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document pdf = new Document(); PdfWriter.getInstance(pdf, out); pdf.open();
            pdf.add(new Paragraph(report.getTitle(), new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
            pdf.add(new Paragraph(report.getArea() + " | " + dateFormat.format(report.getReferenceDate())));
            pdf.add(new Paragraph(" "));
            pdfSection(pdf, "Resultado", report.getResultSummary());
            pdfSection(pdf, "Gaps", report.getGaps());
            pdfSection(pdf, "Ganhos", report.getGains());
            pdfSection(pdf, "Próximos passos", report.getNextSteps());
            pdf.close(); return out.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar R3G em PDF", e); }
    }

    @Transactional(readOnly = true)
    public byte[] r3gExcel(Long id) {
        R3GReport report = r3gService.get(id);
        if (report == null) { throw new BusinessException("R3G não encontrado."); }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("R3G");
            String[][] values = {
                {"Título", report.getTitle()}, {"Área", report.getArea()},
                {"Referência", dateFormat.format(report.getReferenceDate())},
                {"Status", report.getStatus().getLabel()}, {"Resultado", safe(report.getResultSummary())},
                {"Gaps", safe(report.getGaps())}, {"Ganhos", safe(report.getGains())},
                {"Próximos passos", safe(report.getNextSteps())}
            };
            for (int i = 0; i < values.length; i++) {
                Row row = sheet.createRow(i); row.createCell(0).setCellValue(values[i][0]); row.createCell(1).setCellValue(values[i][1]);
            }
            sheet.setColumnWidth(0, 6000); sheet.setColumnWidth(1, 24000);
            ByteArrayOutputStream out = new ByteArrayOutputStream(); workbook.write(out); workbook.close();
            return out.toByteArray();
        } catch (Exception e) { throw new IllegalStateException("Falha ao gerar R3G em Excel", e); }
    }

    private void section(XWPFDocument word, String title, String content) {
        XWPFParagraph p = word.createParagraph(); p.createRun().setBold(true); p.createRun().setText(title);
        word.createParagraph().createRun().setText(safe(content));
    }

    private void pdfSection(Document pdf, String title, String content) throws Exception {
        pdf.add(new Paragraph(title, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        pdf.add(new Paragraph(safe(content))); pdf.add(new Paragraph(" "));
    }

    private JFreeChart createChart(Map<TrafficStatus, Long> values) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(values.get(TrafficStatus.BLUE), "Itens", "Superado");
        dataset.addValue(values.get(TrafficStatus.GREEN), "Itens", "No alvo");
        dataset.addValue(values.get(TrafficStatus.YELLOW), "Itens", "Atenção");
        dataset.addValue(values.get(TrafficStatus.RED), "Itens", "Crítico");
        JFreeChart chart = ChartFactory.createBarChart("Distribuição do farol", "Situação", "Itens",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(220, 225, 230));
        BarRenderer renderer = new BarRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Paint getItemPaint(int row, int column) {
                if (column == 0) return new Color(37, 99, 235);
                if (column == 1) return new Color(22, 163, 74);
                if (column == 2) return new Color(245, 158, 11);
                return new Color(220, 38, 38);
            }
        };
        renderer.setShadowVisible(false);
        plot.setRenderer(renderer);
        return chart;
    }

    private void header(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        cell.setBackgroundColor(new BaseColor(20, 45, 66)); table.addCell(cell);
    }
    private BaseColor pdfColor(TrafficStatus status) {
        if (status == TrafficStatus.GREEN) return new BaseColor(187, 247, 208);
        if (status == TrafficStatus.YELLOW) return new BaseColor(254, 240, 138);
        if (status == TrafficStatus.RED) return new BaseColor(254, 202, 202);
        if (status == TrafficStatus.BLUE) return new BaseColor(191, 219, 254);
        return new BaseColor(229, 231, 235);
    }
    private String safe(String value) { return value == null ? "" : value; }
}