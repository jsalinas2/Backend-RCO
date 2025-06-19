package com.develop.dental_api.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/config-test")
@RequiredArgsConstructor
public class ConfigTestController {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${app.security.jwt.expiration}")
    private Long jwtExpiration;

    @Value("${app.mercadopago.access-token}")
    private String mpAccessToken;

    @Value("${app.mercadopago.success-url}")
    private String mpSuccessUrl;

    @Value("${app.mercadopago.failure-url}")
    private String mpFailureUrl;

    @Value("${app.mercadopago.pending-url}")
    private String mpPendingUrl;

    @Value("${app.mercadopago.notification-url}")
    private String mpNotificationUrl;

    @GetMapping("/values")
    public ResponseEntity<Map<String, Object>> getConfigValues() {
        Map<String, Object> configs = new LinkedHashMap<>();
        
        // Frontend URL
        configs.put("Frontend Configuration", Map.of(
            "frontendUrl", frontendUrl
        ));
        
        // Security Configuration
        configs.put("Security Configuration", Map.of(
            "jwtSecretKey", maskSensitiveData(jwtSecretKey),
            "jwtExpiration", jwtExpiration + " ms (" + (jwtExpiration/3600000) + " hours)"
        ));
        
        // MercadoPago Configuration
        configs.put("MercadoPago Configuration", Map.of(
            "accessToken", maskSensitiveData(mpAccessToken),
            "successUrl", mpSuccessUrl,
            "failureUrl", mpFailureUrl,
            "pendingUrl", mpPendingUrl,
            "notificationUrl", mpNotificationUrl
        ));

        return ResponseEntity.ok(configs);
    }

    private String maskSensitiveData(String data) {
        if (data == null || data.length() < 8) return "***";
        return data.substring(0, 4) + "..." + data.substring(data.length() - 4);
    }
}