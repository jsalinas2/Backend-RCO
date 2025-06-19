package com.develop.dental_api.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.ClinicalRecord;
import com.develop.dental_api.repository.AppointmentRepository;
import com.develop.dental_api.repository.ClinicalRecordRepository;
import com.develop.dental_api.service.PdfGeneratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "https://odontologiaweb.netlify.app")
@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfGeneratorService pdfGeneratorService;
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/clinical-record/{dni}")
    public ResponseEntity<byte[]> generateClinicalRecordPdf(@PathVariable String dni) {
        ClinicalRecord record = clinicalRecordRepository.findByProfileDni(dni)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));

        Context context = new Context();
        context.setVariable("clinicalRecord", record);
        context.setVariable("patient", record.getUser().getProfile());
        context.setVariable("user", record.getUser()); // Agregar datos del usuario
        context.setVariable("currentDate", java.time.LocalDateTime.now());
        context.setVariable("treatmentsDone", record.getTreatmentsDone()); // Agregar tratamientos explícitamente

        byte[] pdf = pdfGeneratorService.generatePdf("historial-clinico-pdf", context);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "historial-clinico.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }

    
    @GetMapping("/appointment/{appointmentId}")
    @Operation(summary = "Generar comprobante de cita en PDF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "PDF generado correctamente",
        content = @Content(mediaType = "application/pdf")),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada",
        content = @Content)
        })
    public ResponseEntity<byte[]> generateAppointmentPdf(@PathVariable Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        Context context = new Context();
        context.setVariable("appointment", appointment);
        context.setVariable("patient", appointment.getPatient().getProfile()); // Datos del paciente
        context.setVariable("dentist", appointment.getDentist().getProfile()); // Datos del dentista
        context.setVariable("service", appointment.getService()); // Datos del servicio
        context.setVariable("payment", appointment.getPayment()); // Datos del pago
        context.setVariable("currentDate", java.time.LocalDateTime.now());

        byte[] pdf = pdfGeneratorService.generatePdf("comprobante-cita-pdf", context);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "comprobante-cita.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}