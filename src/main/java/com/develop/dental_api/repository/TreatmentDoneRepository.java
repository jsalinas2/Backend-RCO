package com.develop.dental_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develop.dental_api.model.entity.TreatmentDone;

@Repository
public interface TreatmentDoneRepository extends JpaRepository<TreatmentDone, Long> {
}