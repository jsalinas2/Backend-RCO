package com.develop.dental_api.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {
    
    private final TemplateEngine templateEngine;

    public byte[] generatePdf(String templateName, Context context) {
        try {
            String processedHtml = templateEngine.process(templateName, context);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            
            // Configurar el renderer
            renderer.setDocumentFromString(processedHtml);
            
            // Establecer el tamaño de página personalizado
            renderer.getSharedContext().setBaseURL("classpath:/templates/");
            renderer.getSharedContext().setPrint(true);
            renderer.getSharedContext().setInteractive(false);
            
            renderer.layout();
            renderer.createPDF(outputStream);
            
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }
}