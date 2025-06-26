package com.develop.dental_api.service.implement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import com.develop.dental_api.model.dto.PaymentDetailDTO;
import com.develop.dental_api.model.dto.UserPaymentHistoryDTO;
import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.Payment;
import com.develop.dental_api.model.mapper.PaymentMapper;
import com.develop.dental_api.repository.AppointmentRepository;
import com.develop.dental_api.repository.PaymentRepository;
import com.develop.dental_api.service.PaymentService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.payment.PaymentStatus;
import com.mercadopago.resources.preference.Preference;
import com.tenpisoft.n2w.MoneyConverters;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AppointmentRepository appointmentRepository;

    //@Value("${app.mercadopago.access-token}")
    private String mercadoPagoToken = "TEST-7258364631669820-050611-6dac61aefb032d3bdcd16b3fe2c965b9-2422988911";

    //@Value("${app.mercadopago.success-url}")
    private String successUrl = "https://odontologiaweb.netlify.app/payment-success";

    //@Value("${app.mercadopago.failure-url}")
    private String failureUrl = "https://www.google.com.pe/";

    //@Value("${app.mercadopago.pending-url}")
    private String pendingUrl = "https://www.google.com.pe/";

    //@Value("${app.mercadopago.notification-url}")
    private String notificationUrl = "https://backend-rco.onrender.com/api/v1/api/webhook";

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final com.develop.dental_api.service.PdfGeneratorService pdfGeneratorService;

    @Override
    public String registerPayment(Integer appointmentId) throws Exception {
        try {
        MercadoPagoConfig.setAccessToken("TEST-7258364631669820-050611-6dac61aefb032d3bdcd16b3fe2c965b9-2422988911");

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        BigDecimal amount = appointment.getService().getPrice();

        // Crear la preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(
                        PreferenceItemRequest.builder()
                                .title("Pago por servicio: " + appointment.getService().getName())
                                .quantity(1)
                                .unitPrice(amount)
                                .currencyId("PEN")
                                .build()
                ))
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("https://odontologiaweb.netlify.app/payment-success")
                        .failure("https://www.google.com.pe/")
                        .pending("https://www.google.com.pe/")
                        .build())
                .autoReturn("approved")
                .notificationUrl("https://backend-rco.onrender.com/api/v1/api/webhook")
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        // Guardar el pago como PENDING
        // Verificar si ya existe un pago para esta cita
        if (paymentRepository.findByAppointment(appointment).isPresent()) {
            throw new RuntimeException("Esta cita ya tiene un pago registrado");
        }

        Payment payment = new Payment();
        payment.setAppointment(appointment);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setMercadoPagoId(preference.getId()); // ID de la preferencia
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        return preference.getInitPoint();
        } catch (com.mercadopago.exceptions.MPApiException e) {
        System.out.println("MercadoPago API error: " + e.getApiResponse().getContent());
        throw new RuntimeException("MercadoPago API error: " + e.getApiResponse().getContent(), e);
    } catch (Exception e) {
        e.printStackTrace();
        throw e;
    }
    }

    @Override
    public void updatePaymentStatus(String paymentId) throws Exception {
/*         MercadoPagoConfig.setAccessToken(mercadoPagoToken);

        PaymentClient paymentClient = new PaymentClient();
        com.mercadopago.resources.payment.Payment mpPayment = paymentClient.get(Long.parseLong(paymentId));
        System.out.println("mpPayment: " + mpPayment.getStatus());
        System.out.println("mpPayment: " + mpPayment.getPaymentMethodOptionId());
        System.out.println("mpPayment: " + mpPayment.getPaymentMethodId());
        System.out.println("mpPayment: " + mpPayment.getId());
        
        // Buscar en tu BD el pago que tenga el ID de MercadoPago
        Optional<Payment> optional = paymentRepository.findByMercadoPagoId(String.valueOf(mpPayment.getId()));
        if (optional.isPresent()) {
            Payment pago = optional.get();
            pago.setStatus(mpPayment.getStatus().toUpperCase()); // PENDING, APPROVED, etc.
            pago.setPaymentMethod(mpPayment.getPaymentMethodId());
            pago.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(pago);
*/


                Optional<Payment> optional = paymentRepository.findById(Integer.valueOf(paymentId));
                Payment pago = optional.get();
                Appointment appointment = pago.getAppointment();
                String email = appointment.getPatient().getEmail();

                // Generar PDF del comprobante
                Context context = new Context();
                context.setVariable("appointment", appointment);
                context.setVariable("payment", pago);
                context.setVariable("service", appointment.getService());
                context.setVariable("currentDate", LocalDateTime.now());

                // Monto en letras (usa tu lógica o librería preferida)
                MoneyConverters converter = MoneyConverters.SPANISH_BANKING_MONEY_VALUE;
                String moneyAsWords = converter.asWords(new BigDecimal("" + appointment.getPayment().getAmount()));
                context.setVariable("montoLetras", moneyAsWords.toUpperCase());

                byte[] pdfBytes = pdfGeneratorService.generatePdf("comprobante-venta-pdf", context);

                // Enviar correo con PDF adjunto
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(email);
                helper.setSubject("Comprobante de Pago - Dental Esthetic");
                helper.setText(
                    templateEngine.process("correo-confirmacion-cita.html", context), // crea esta plantilla para el cuerpo del correo
                    true
                );
                helper.addAttachment("comprobante-pago.pdf", new org.springframework.core.io.ByteArrayResource(pdfBytes));

                mailSender.send(message);
            
        
    }


    @Override
    public PaymentDetailDTO getPaymentDetail(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return paymentMapper.toPaymentDetailDTO(payment);
    }

    @Override
    public List<UserPaymentHistoryDTO> getUserPayments(Integer userId) {
        List<Payment> payments = paymentRepository.findByAppointment_Patient_UserId(userId);
        
        return payments.stream()
            .map(payment -> {
                UserPaymentHistoryDTO dto = new UserPaymentHistoryDTO();
                dto.setPaymentId(payment.getPaymentId());
                dto.setPaymentDate(payment.getPaymentDate());
                dto.setServiceName(payment.getAppointment().getService().getName());
                dto.setAmount(payment.getAmount());
                dto.setStatus(payment.getStatus());
                return dto;
            })
            .collect(Collectors.toList());
    }
}
