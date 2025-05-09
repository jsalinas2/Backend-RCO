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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pierrecode.debtcontrolapi.dto.CategoriaDeudaDTO;
import com.pierrecode.debtcontrolapi.service.CategoriaDeudaService;

@CrossOrigin(origins = "https://debttrackerweb.netlify.app")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaDeudaController {

    @Autowired
    private CategoriaDeudaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDeudaDTO>> obtenerTodasCategorias() {
        List<CategoriaDeudaDTO> categorias = categoriaService.obtenerTodasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDeudaDTO> obtenerCategoriaPorId(@PathVariable int id) {
        CategoriaDeudaDTO categoriaDTO = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoriaDTO);
    }

    @PostMapping
    public ResponseEntity<CategoriaDeudaDTO> crearCategoria(@Valid @RequestBody CategoriaDeudaDTO categoriaDTO) {
        CategoriaDeudaDTO nuevaCategoria = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDeudaDTO> actualizarCategoria(
            @PathVariable int id,
            @Valid @RequestBody CategoriaDeudaDTO categoriaDTO) {
        CategoriaDeudaDTO categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
