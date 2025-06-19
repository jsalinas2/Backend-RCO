package com.develop.dental_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.develop.dental_api.model.entity.ClinicalRecord;
import com.develop.dental_api.model.entity.User;

@Repository
public interface ClinicalRecordRepository extends JpaRepository<ClinicalRecord, Integer> {
    Optional<ClinicalRecord> findByUser(User user);
    Optional<ClinicalRecord> findByUser_UserId(Integer userId);
    @Query("SELECT c FROM ClinicalRecord c WHERE c.user.profile.dni = :dni")
    Optional<ClinicalRecord> findByProfileDni(@Param("dni") String dni);
}
