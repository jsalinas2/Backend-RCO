package com.develop.dental_api.integration.pago;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.dental_api.model.dto.PaymentDetailDTO;
import com.develop.dental_api.model.dto.PaymentRequestDTO;
import com.develop.dental_api.model.dto.PaymentResponseDTO;
import com.develop.dental_api.service.PaymentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;
    /*
    @PostMapping()
    public ResponseEntity<String> recibirWebhook(@RequestBody String payload) {
        // Mostrar el webhook tal como se recibe
        System.out.println("Webhook recibido: " + payload);
    
        // Retornar una respuesta exitosa
        return ResponseEntity.ok("Webhook recibido con éxito");
    }
    */



    @PostMapping
    public ResponseEntity<Void> recibirNotificacion(@RequestBody Map<String, Object> payload) {
        try {
            if ("payment".equals(payload.get("type"))) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                String paymentId = String.valueOf(data.get("id"));
                paymentService.updatePaymentStatus(paymentId);
                System.out.println("paymentid: " + paymentId);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log en producción
        }
        return ResponseEntity.ok().build();
    }

}
