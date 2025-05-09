package com.pierrecode.debtcontrolapi.api;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pierrecode.debtcontrolapi.dto.DeudaDTO;
import com.pierrecode.debtcontrolapi.dto.ResumenDeudaDTO;
import com.pierrecode.debtcontrolapi.service.DeudaService;

@CrossOrigin(origins = "https://debttrackerweb.netlify.app")
@RestController
@RequestMapping("/deudas")
public class DeudaController {

    @Autowired
    private DeudaService deudaService;

    @GetMapping
    public ResponseEntity<List<DeudaDTO>> obtenerDeudas(
            @RequestParam(required = false) boolean estadoPago,
            @RequestParam(required = false) Integer categoriaId) { 

        List<DeudaDTO> deudas;
        
        if (estadoPago && categoriaId != null) { 
            deudas = deudaService.obtenerDeudasPorCategoriaYEstado(categoriaId, estadoPago);
        } else if (estadoPago) {
            deudas = deudaService.obtenerDeudasPorCategoriaYEstado(0,estadoPago);
        } else if (categoriaId != null) { 
            deudas = deudaService.obtenerDeudasPorCategoria(categoriaId);
        } else {
            deudas = deudaService.obtenerTodasDeudas();
        }

        return ResponseEntity.ok(deudas);
    }

    @GetMapping("/vencidas")
    public ResponseEntity<List<DeudaDTO>> obtenerDeudasVencidas() {
        List<DeudaDTO> deudas = deudaService.obtenerDeudasVencidas();
        return ResponseEntity.ok(deudas);
    }

    @GetMapping("/proximas")
    public ResponseEntity<List<DeudaDTO>> obtenerDeudasProximas(
            @RequestParam(defaultValue = "7") int dias) {
        List<DeudaDTO> deudas = deudaService.obtenerDeudasProximas(dias);
        return ResponseEntity.ok(deudas);
    }

    @GetMapping("/mes-actual")
    public ResponseEntity<List<DeudaDTO>> obtenerDeudasDelMesActual() {
        List<DeudaDTO> deudas = deudaService.obtenerDeudasDelMesActual();
        return ResponseEntity.ok(deudas);
    }

    @GetMapping("/resumen")
    public ResponseEntity<ResumenDeudaDTO> obtenerResumen() {
        ResumenDeudaDTO resumen = deudaService.obtenerResumen();
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeudaDTO> obtenerDeudaPorId(@PathVariable int id) { 
        DeudaDTO deudaDTO = deudaService.obtenerDeudaPorId(id);
        return ResponseEntity.ok(deudaDTO);
    }

    @PostMapping
    public ResponseEntity<DeudaDTO> crearDeuda(@Valid @RequestBody DeudaDTO deudaDTO) {
        DeudaDTO nuevaDeuda = deudaService.crearDeuda(deudaDTO);
        return new ResponseEntity<>(nuevaDeuda, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeudaDTO> actualizarDeuda(
            @PathVariable int id, 
            @Valid @RequestBody DeudaDTO deudaDTO) {
        DeudaDTO deudaActualizada = deudaService.actualizarDeuda(id, deudaDTO);
        return ResponseEntity.ok(deudaActualizada);
    }

    @PutMapping("/{id}/estado-pago")
    public ResponseEntity<DeudaDTO> marcarComoEstadoPago(
            @PathVariable int id, 
            @RequestParam boolean estadoPago) {
        DeudaDTO deudaActualizada = deudaService.marcarComoEstadoPago(id, estadoPago);
        return ResponseEntity.ok(deudaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDeuda(@PathVariable int id) { 
        deudaService.eliminarDeuda(id);
        return ResponseEntity.noContent().build();
    }
}
