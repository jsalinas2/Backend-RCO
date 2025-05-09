package com.pierrecode.debtcontrolapi.service;

import java.util.HashMap;
import java.util.Map;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.pierrecode.debtcontrolapi.model.entity.Deuda;
import com.pierrecode.debtcontrolapi.model.entity.Usuario;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.sender-name}")
    private String senderName;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    /**
     * Envía un correo de bienvenida al usuario recién registrado
     */
    @Async
    public void enviarEmailBienvenida(Usuario usuario) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, senderName);
            helper.setTo(usuario.getEmail());
            helper.setSubject("Bienvenido a DebtTracker");

            Map<String, Object> variables = new HashMap<>();
            variables.put("nombre", usuario.getNombre());
            variables.put("loginUrl", frontendUrl + "/login");

            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process("email/bienvenida", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {

            System.err.println("Error al enviar correo de bienvenida: " + e.getMessage());
        }
    }

    /**
     * Envía un recordatorio de deuda próxima a vencer
     */
    @Async
    public void enviarRecordatorioDeuda(Deuda deuda) {
        try {
            Usuario usuario = deuda.getUsuario();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, senderName);
            helper.setTo(usuario.getEmail());
            helper.setSubject("Recordatorio de Deuda Próxima a Vencer");

            Map<String, Object> variables = new HashMap<>();
            variables.put("nombre", usuario.getNombre());
            variables.put("descripcion", deuda.getDescripcion());
            variables.put("monto", deuda.getMonto());
            variables.put("fechaVencimiento", deuda.getFechaVenc());
            variables.put("categoria", deuda.getCategoria().getNombre());
            variables.put("detalleUrl", frontendUrl + "/dashboard");

            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process("email/recordatorio-deuda", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error al enviar recordatorio de deuda: " + e.getMessage());
        }
    }

    /**
     * Envía una notificación de deuda vencida
     */
    @Async
    public void enviarNotificacionDeudaVencida(Deuda deuda) {
        try {
            Usuario usuario = deuda.getUsuario();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, senderName);
            helper.setTo(usuario.getEmail());
            helper.setSubject("¡Alerta! Deuda Vencida");

            Map<String, Object> variables = new HashMap<>();
            variables.put("nombre", usuario.getNombre());
            variables.put("descripcion", deuda.getDescripcion());
            variables.put("monto", deuda.getMonto());
            variables.put("fechaVencimiento", deuda.getFechaVenc());
            variables.put("categoria", deuda.getCategoria().getNombre());
            variables.put("detalleUrl", frontendUrl + "/dashboard");

            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process("email/deuda-vencida", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error al enviar notificación de deuda vencida: " + e.getMessage());
        }
    }
}
