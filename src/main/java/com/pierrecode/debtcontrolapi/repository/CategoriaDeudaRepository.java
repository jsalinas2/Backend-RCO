package com.pierrecode.debtcontrolapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pierrecode.debtcontrolapi.model.entity.CategoriaDeuda;

@Repository
public interface CategoriaDeudaRepository extends JpaRepository<CategoriaDeuda, Integer> {

    Optional<CategoriaDeuda> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);


    List<CategoriaDeuda> findAll();
}
