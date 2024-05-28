package com.mediNet.mediNet.repository;

import com.mediNet.mediNet.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    int countByHospitalId(Long hospitalId);
}
