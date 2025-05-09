package com.pierrecode.debtcontrolapi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pierrecode.debtcontrolapi.model.entity.Deuda;
import com.pierrecode.debtcontrolapi.repository.DeudaRepository;

@Service
public class NotificacionService {

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Envía recordatorios diarios para deudas próximas a vencer (3 días antes)
     * Se ejecuta todos los días a las 8:00 AM
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void enviarRecordatoriosDeudasProximas() {
        LocalDate fechaLimite = LocalDate.now().plusDays(3);

        // Buscar todas las deudas que vencen en 3 días y no están pagadas
        List<Deuda> deudasProximas = deudaRepository.findByFechaVencAndEstadoPago(fechaLimite, false);

        // Enviar recordatorio para cada deuda
        for (Deuda deuda : deudasProximas) {
            emailService.enviarRecordatorioDeuda(deuda);
        }
    }

    /**
     * Envía notificaciones diarias para deudas vencidas
     * Se ejecuta todos los días a las 9:00 AM
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void enviarNotificacionesDeudasVencidas() {
        LocalDate hoy = LocalDate.now();

        // Buscar todas las deudas vencidas y no pagadas
        List<Deuda> deudasVencidas = deudaRepository.findVencidas(hoy);

        // Enviar notificación para cada deuda vencida
        for (Deuda deuda : deudasVencidas) {
            emailService.enviarNotificacionDeudaVencida(deuda);
        }
    }
}

