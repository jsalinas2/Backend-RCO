package com.develop.dental_api.api;

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

@CrossOrigin(origins = "https://odontologiaweb.netlify.app")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create/{appointmentId}")
    public ResponseEntity<Map<String, String>> iniciarPago(@PathVariable Integer appointmentId) {
        try {
            String url = paymentService.registerPayment(appointmentId);
            return ResponseEntity.ok(Map.of("init_point", url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<PaymentDetailDTO> getPayment(@PathVariable Integer payment_id) {
        return ResponseEntity.ok(paymentService.getPaymentDetail(payment_id));
    }
}
