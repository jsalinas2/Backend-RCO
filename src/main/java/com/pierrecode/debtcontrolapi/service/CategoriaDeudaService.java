package com.pierrecode.debtcontrolapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pierrecode.debtcontrolapi.dto.CategoriaDeudaDTO;
import com.pierrecode.debtcontrolapi.model.entity.CategoriaDeuda;
import com.pierrecode.debtcontrolapi.repository.CategoriaDeudaRepository;

@Service
public class CategoriaDeudaService {

    @Autowired
    private CategoriaDeudaRepository categoriaRepository;

    public List<CategoriaDeudaDTO> obtenerTodasCategorias() {
        List<CategoriaDeuda> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CategoriaDeudaDTO obtenerCategoriaPorId(int id) {
        CategoriaDeuda categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        return convertirADTO(categoria);
    }

    public CategoriaDeudaDTO crearCategoria(CategoriaDeudaDTO categoriaDTO) {

        if (categoriaRepository.existsByNombre(categoriaDTO.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + categoriaDTO.getNombre());
        }

        CategoriaDeuda categoria = new CategoriaDeuda();
        categoria.setNombre(categoriaDTO.getNombre());
        System.out.println(categoria);
        CategoriaDeuda nuevaCategoria = categoriaRepository.save(categoria);

        return convertirADTO(nuevaCategoria);
    }

    public CategoriaDeudaDTO actualizarCategoria(int id, CategoriaDeudaDTO categoriaDTO) {
        CategoriaDeuda categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));


        if (!categoria.getNombre().equals(categoriaDTO.getNombre()) &&
                categoriaRepository.existsByNombre(categoriaDTO.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + categoriaDTO.getNombre());
        }

        categoria.setNombre(categoriaDTO.getNombre());

        CategoriaDeuda categoriaActualizada = categoriaRepository.save(categoria);

        return convertirADTO(categoriaActualizada);
    }

    public void eliminarCategoria(int id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }

        categoriaRepository.deleteById(id);
    }

    private CategoriaDeudaDTO convertirADTO(CategoriaDeuda categoria) {
        CategoriaDeudaDTO categoriaDTO = new CategoriaDeudaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        return categoriaDTO;
    }
}
