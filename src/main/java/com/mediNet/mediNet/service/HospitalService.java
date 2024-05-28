package com.mediNet.mediNet.service;

import com.mediNet.mediNet.model.Hospital;

public interface HospitalService {
    String addHospital(Hospital hospital);
    Hospital getHospitalByPatientId(Long patientId);
    int getPatientCount(Long hospitalId);
}
