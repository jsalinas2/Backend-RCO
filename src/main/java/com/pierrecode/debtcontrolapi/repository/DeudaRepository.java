package com.pierrecode.debtcontrolapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pierrecode.debtcontrolapi.model.entity.Deuda;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Integer> {

    List<Deuda> findByUsuarioId(int usuarioId);

    List<Deuda> findByUsuarioIdAndEstadoPago(int usuarioId, boolean estadoPago);

    List<Deuda> findByUsuarioIdAndCategoriaId(int usuarioId, int categoriaId);

    List<Deuda> findByUsuarioIdAndCategoriaIdAndEstadoPago(int usuarioId, int categoriaId, boolean estadoPago);

    @Query("SELECT d FROM Deuda d WHERE d.usuario.id = ?1 AND d.fechaVenc BETWEEN ?2 AND ?3")
    List<Deuda> findByUsuarioIdAndFechaVencBetween(int usuarioId, LocalDate inicio, LocalDate fin);

    @Query("SELECT d FROM Deuda d WHERE d.usuario.id = ?1 AND d.fechaVenc < ?2 AND d.estadoPago = false")
    List<Deuda> findVencidasByUsuarioId(int usuarioId, LocalDate fecha);

    @Query("SELECT d FROM Deuda d WHERE d.usuario.id = ?1 AND d.fechaVenc BETWEEN ?2 AND ?3 AND d.estadoPago = false")
    List<Deuda> findProximasByUsuarioId(int usuarioId, LocalDate inicio, LocalDate fin);

    @Query("SELECT d FROM Deuda d WHERE d.usuario.id = ?1 AND EXTRACT(MONTH FROM d.fechaVenc) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM d.fechaVenc) = EXTRACT(YEAR FROM CURRENT_DATE)")
    List<Deuda> findDeudasDelMesActual(int usuarioId);

    List<Deuda> findByFechaVencAndEstadoPago(LocalDate fechaVenc, boolean estadoPago);

    @Query("SELECT d FROM Deuda d WHERE d.fechaVenc < ?1 AND d.estadoPago = false")
    List<Deuda> findVencidas(LocalDate fecha);
}
