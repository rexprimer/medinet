package com.mediNet.mediNet.service;

import com.mediNet.mediNet.exception.NotFoundException;
import com.mediNet.mediNet.exception.ValidationException;
import com.mediNet.mediNet.model.Hospital;
import com.mediNet.mediNet.model.Patient;
import com.mediNet.mediNet.repository.HospitalRepository;
import com.mediNet.mediNet.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, PatientRepository patientRepository) {
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public String addHospital(Hospital hospital) {
        if (ObjectUtils.isEmpty(hospital.getName())) {
            throw new ValidationException("Patient validation failed: Missing first name");
        }
        for(Patient patient : hospital.getPatients()) {
            patient.setHospital(hospital);
        }
        Hospital hospital1 = hospitalRepository.save(hospital);
        return "Hospital Details Added !";
    }

    @Override
    public Hospital getHospitalByPatientId(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new NotFoundException("Patient Not Found !");
        }
        Patient patient = patientRepository.findById(patientId).get();
        Hospital hospital = patient.getHospital();
        if (hospital == null) {
            throw new NotFoundException("Hospital Not Exist !");
        }

        Hospital hospital1 = new Hospital();
        hospital1.setId(hospital.getId());
        hospital1.setName(hospital.getName());
        hospital1.setAddress(hospital.getAddress());
        hospital1.setPhoneNumber(hospital.getPhoneNumber());

        return hospital1;
    }

    @Override
    public int getPatientCount(Long hospitalId) {
        if(!hospitalRepository.existsById(hospitalId)){
            throw new NotFoundException("Hospital Not Found !");
        }
        return hospitalRepository.findById(hospitalId).get().getPatients().size();
    }

}
