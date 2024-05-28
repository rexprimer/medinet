package com.mediNet.mediNet.service;

import com.mediNet.mediNet.exception.NotFoundException;
import com.mediNet.mediNet.exception.ValidationException;
import com.mediNet.mediNet.model.Hospital;
import com.mediNet.mediNet.model.Patient;
import com.mediNet.mediNet.repository.HospitalRepository;
import com.mediNet.mediNet.repository.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HospitalServiceImplTest {

    @Mock
    HospitalRepository hospitalRepository;

    @Mock
    PatientRepository patientRepository;
    HospitalServiceImpl hospitalServiceImpl;
    AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        hospitalServiceImpl = new HospitalServiceImpl(hospitalRepository, patientRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    
    @Test
    void testSuccess_addHospital() {
        Hospital hospital = new Hospital();
        hospital.setName("AIIMS");

        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());
        patientList.add(new Patient());

        hospital.setPatients(patientList);

        when(hospitalRepository.save(hospital)).thenReturn(hospital);
        String result = hospitalServiceImpl.addHospital(hospital);

        assertEquals("Hospital Details Added !", result);

        for(Patient patient : hospital.getPatients()) {
            assertEquals(hospital, patient.getHospital());
        }
    }
    
    @Test
    void testAddHospital_ValidationFailed_Name(){
        Hospital hospital = new Hospital();
        hospital.setAddress("New Delhi");
        hospital.setPhoneNumber("+91 123456789");

        assertThrows(ValidationException.class, () -> hospitalServiceImpl.addHospital(hospital));
    }
    
    @Test
    void testSuccess_GetHospitalById() {
        Patient patient = new Patient();
        patient.setId(1L);
        Hospital hospital = new Hospital();
        hospital.setName("AIIMS");
        hospital.setAddress("New Delhi");
        hospital.setPhoneNumber("+91 123456789");
        patient.setHospital(hospital);

        when(patientRepository.existsById(1L)).thenReturn(true);
        when(patientRepository.findById(1L)).thenReturn(of(patient));
        Hospital returnedHospital = hospitalServiceImpl.getHospitalByPatientId(1L);

        assertNotNull(returnedHospital);
        assertEquals(hospital.getName(), returnedHospital.getName());
        assertEquals(hospital.getAddress(), returnedHospital.getAddress());
        assertEquals(hospital.getPhoneNumber(), returnedHospital.getPhoneNumber());
    }

    @Test
    void testGetHospitalByPatientId_PatientNotFound() {
        long patientId = 1L;
        when(patientRepository.existsById(patientId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> hospitalServiceImpl.getHospitalByPatientId(patientId));
    }

    @Test
    void testGetHospitalByPatientId_HospitalNotFound() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        assertThrows(NotFoundException.class, () -> hospitalServiceImpl.getHospitalByPatientId(1L));
    }

    @Test
    void testSuccess_getPatientCount(){
        Long hospitalId = 123L;
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId);
        hospital.setName("AIIMS");
        hospital.setAddress("New Delhi");
        hospital.setPhoneNumber("+91 123456789");

        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());
        patientList.add(new Patient());
        hospital.setPatients(patientList);

        when(hospitalRepository.existsById(hospitalId)).thenReturn(true);
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));
        int count = hospitalServiceImpl.getPatientCount(hospitalId);

        assertEquals(2, count);
    }

    @Test
    void testFailure_getPatientCount_HospitalNotFound(){
        Long hospitalId = 123L;
        when(patientRepository.existsById(hospitalId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> hospitalServiceImpl.getPatientCount(hospitalId));
    }

    @Test
    void testfail_getAllHospitals_HospitalNotFound() {
        Long hospitalId = 1L;
        when(patientRepository.existsById(hospitalId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> hospitalServiceImpl.getHospitalByPatientId(hospitalId));
    }


}