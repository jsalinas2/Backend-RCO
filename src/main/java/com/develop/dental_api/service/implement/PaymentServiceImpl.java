package com.develop.dental_api.service.implement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.develop.dental_api.model.dto.PaymentDetailDTO;
import com.develop.dental_api.model.dto.PaymentRequestDTO;
import com.develop.dental_api.model.dto.PaymentResponseDTO;
import com.develop.dental_api.model.dto.UserPaymentHistoryDTO;
import com.develop.dental_api.model.entity.Appointment;
import com.develop.dental_api.model.entity.Payment;
import com.develop.dental_api.model.mapper.PaymentMapper;
import com.develop.dental_api.repository.AppointmentRepository;
import com.develop.dental_api.repository.PaymentRepository;
import com.develop.dental_api.service.PaymentService;
import com.mercadopago.resources.payment.PaymentStatus;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AppointmentRepository appointmentRepository;

    @Value("${app.mercadopago.access-token}")
    private String mercadoPagoToken;

    @Value("${app.mercadopago.success-url}")
    private String successUrl;

    @Value("${app.mercadopago.failure-url}")
    private String failureUrl;

    @Value("${app.mercadopago.pending-url}")
    private String pendingUrl;

    @Value("${app.mercadopago.notification-url}")
    private String notificationUrl;

    @Override
    public String registerPayment(Integer appointmentId) throws Exception {
        MercadoPagoConfig.setAccessToken(mercadoPagoToken);

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
                        .success(successUrl)
                        .failure(failureUrl)
                        .pending(pendingUrl)
                        .build())
                .autoReturn("approved")
                .notificationUrl(notificationUrl)
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
        paymentRepository.save(payment);

        return preference.getInitPoint();
    }

    @Override
    public void updatePaymentStatus(String paymentId) throws Exception {
        MercadoPagoConfig.setAccessToken(mercadoPagoToken);

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
        }
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
