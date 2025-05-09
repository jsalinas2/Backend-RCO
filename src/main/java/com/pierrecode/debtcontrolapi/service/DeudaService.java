package com.pierrecode.debtcontrolapi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pierrecode.debtcontrolapi.dto.DeudaDTO;
import com.pierrecode.debtcontrolapi.dto.ResumenDeudaDTO;
import com.pierrecode.debtcontrolapi.model.entity.CategoriaDeuda;
import com.pierrecode.debtcontrolapi.model.entity.Deuda;
import com.pierrecode.debtcontrolapi.model.entity.Usuario;
import com.pierrecode.debtcontrolapi.repository.CategoriaDeudaRepository;
import com.pierrecode.debtcontrolapi.repository.DeudaRepository;
import com.pierrecode.debtcontrolapi.repository.UsuarioRepository;

@Service
public class DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaDeudaRepository categoriaRepository;

    public List<DeudaDTO> obtenerTodasDeudas() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Deuda> deudas = deudaRepository.findByUsuarioId(usuario.getId());

        return deudas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<DeudaDTO> obtenerDeudasPorCategoria(int categoriaId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Deuda> deudas = deudaRepository.findByUsuarioIdAndCategoriaId(usuario.getId(), categoriaId);

        return deudas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<DeudaDTO> obtenerDeudasPorCategoriaYEstado(int categoriaId, boolean estadoPago) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Deuda> deudas = deudaRepository.findByUsuarioIdAndCategoriaIdAndEstadoPago(usuario.getId(), categoriaId, estadoPago);

        return deudas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<DeudaDTO> obtenerDeudasVencidas() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Deuda> deudas = deudaRepository.findVencidasByUsuarioId(usuario.getId(), LocalDate.now());

        return deudas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<DeudaDTO> obtenerDeudasProximas(int dias) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(dias);

        List<Deuda> deudas = deudaRepository.findProximasByUsuarioId(usuario.getId(), hoy, limite);

        return deudas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<DeudaDTO> obtenerDeudasDelMesActual() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Deuda> deudas = deudaRepository.findDeudasDelMesActual(usuario.getId());

        return deudas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public DeudaDTO obtenerDeudaPorId(int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con ID: " + id));

        if (deuda.getUsuario().getId() != usuario.getId()) {
            throw new RuntimeException("No tienes permiso para acceder a esta deuda");
        }

        return convertirADTO(deuda);
    }

    public DeudaDTO crearDeuda(DeudaDTO deudaDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CategoriaDeuda categoria = categoriaRepository.findById(deudaDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + deudaDTO.getCategoriaId()));

        Deuda deuda = new Deuda();
        deuda.setMonto(deudaDTO.getMonto());
        deuda.setDescripcion(deudaDTO.getDescripcion());
        deuda.setFechaVenc(deudaDTO.getFechaVenc());
        deuda.setEstadoPago(deudaDTO.isEstadoPago());
        deuda.setCategoria(categoria);
        deuda.setUsuario(usuario);

        Deuda nuevaDeuda = deudaRepository.save(deuda);

        return convertirADTO(nuevaDeuda);
    }

    public DeudaDTO actualizarDeuda(int id, DeudaDTO deudaDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con ID: " + id));

        if (deuda.getUsuario().getId() != usuario.getId()) {
            throw new RuntimeException("No tienes permiso para modificar esta deuda");
        }

        CategoriaDeuda categoria = categoriaRepository.findById(deudaDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + deudaDTO.getCategoriaId()));

        deuda.setMonto(deudaDTO.getMonto());
        deuda.setDescripcion(deudaDTO.getDescripcion());
        deuda.setFechaVenc(deudaDTO.getFechaVenc());
        deuda.setEstadoPago(deudaDTO.isEstadoPago());
        deuda.setCategoria(categoria);

        Deuda deudaActualizada = deudaRepository.save(deuda);

        return convertirADTO(deudaActualizada);
    }

    public DeudaDTO marcarComoEstadoPago(int id, boolean estadoPago) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con ID: " + id));

        if (deuda.getUsuario().getId() != usuario.getId()) {
            throw new RuntimeException("No tienes permiso para modificar esta deuda");
        }

        deuda.setEstadoPago(estadoPago);
        Deuda deudaActualizada = deudaRepository.save(deuda);

        return convertirADTO(deudaActualizada);
    }

    public void eliminarDeuda(int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con ID: " + id));

        if (deuda.getUsuario().getId() != usuario.getId()) {
            throw new RuntimeException("No tienes permiso para eliminar esta deuda");
        }

        deudaRepository.deleteById(id);
    }

    private DeudaDTO convertirADTO(Deuda deuda) {
        DeudaDTO deudaDTO = new DeudaDTO();
        deudaDTO.setId(deuda.getId());
        deudaDTO.setMonto(deuda.getMonto());
        deudaDTO.setDescripcion(deuda.getDescripcion());
        deudaDTO.setFechaVenc(deuda.getFechaVenc());
        deudaDTO.setEstadoPago(deuda.isEstadoPago());
        deudaDTO.setCategoriaId(deuda.getCategoria().getId());
        deudaDTO.setCategoriaNombre(deuda.getCategoria().getNombre());
        return deudaDTO;
    }

    public ResumenDeudaDTO obtenerResumen() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Deuda> deudasPendientes = deudaRepository.findByUsuarioIdAndEstadoPago(usuario.getId(), false);
        List<Deuda> deudasVencidas = deudaRepository.findVencidasByUsuarioId(usuario.getId(), LocalDate.now());
        List<Deuda> deudasProximas = deudaRepository.findProximasByUsuarioId(usuario.getId(), LocalDate.now(), LocalDate.now().plusDays(7));

        double totalPendiente = deudasPendientes.stream().mapToDouble(Deuda::getMonto).sum();
        double totalVencido = deudasVencidas.stream().mapToDouble(Deuda::getMonto).sum();
        double totalProximo = deudasProximas.stream().mapToDouble(Deuda::getMonto).sum();

        Map<Integer, Map<String, Object>> resumenPorCategoria = new HashMap<>();

        for (Deuda deuda : deudasPendientes) {
            int categoriaId = deuda.getCategoria().getId();
            String categoriaNombre = deuda.getCategoria().getNombre();

            if (!resumenPorCategoria.containsKey(categoriaId)) {
                Map<String, Object> categoriaResumen = new HashMap<>();
                categoriaResumen.put("id", categoriaId);
                categoriaResumen.put("nombre", categoriaNombre);
                categoriaResumen.put("total", 0.0);
                categoriaResumen.put("cantidad", 0);
                resumenPorCategoria.put(categoriaId, categoriaResumen);
            }

            Map<String, Object> categoriaResumen = resumenPorCategoria.get(categoriaId);
            double total = (double) categoriaResumen.get("total");
            int cantidad = (int) categoriaResumen.get("cantidad");

            categoriaResumen.put("total", total + deuda.getMonto());
            categoriaResumen.put("cantidad", cantidad + 1);
        }

        for (Map<String, Object> categoriaResumen : resumenPorCategoria.values()) {
            double total = (double) categoriaResumen.get("total");
            double porcentaje = totalPendiente > 0 ? (total / totalPendiente) * 100 : 0;
            categoriaResumen.put("porcentaje", porcentaje);
        }

        ResumenDeudaDTO resumen = new ResumenDeudaDTO();
        resumen.setTotalPendiente(totalPendiente);
        resumen.setTotalVencido(totalVencido);
        resumen.setTotalProximo(totalProximo);
        resumen.setCantidadPendientes(deudasPendientes.size());
        resumen.setCantidadVencidas(deudasVencidas.size());
        resumen.setCantidadProximas(deudasProximas.size());
        resumen.setResumenPorCategoria(new ArrayList<>(resumenPorCategoria.values()));

        return resumen;
    }

}
