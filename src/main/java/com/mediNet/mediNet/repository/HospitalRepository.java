package com.mediNet.mediNet.repository;

import com.mediNet.mediNet.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
}
