package com.mediNet.mediNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediNet.mediNet.exception.NotFoundException;
import com.mediNet.mediNet.exception.ValidationException;
import com.mediNet.mediNet.model.Hospital;
import com.mediNet.mediNet.model.Patient;
import com.mediNet.mediNet.service.HospitalService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MediNetController.class)
class mediNetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalService mediNetController;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testSuccess_addHospital() throws Exception {
        Hospital hospital = new Hospital();
        hospital.setName("AIIMS");
        hospital.setAddress("New Delhi");
        hospital.setPhoneNumber("+91 22222");

        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());
        patientList.add(new Patient());
        hospital.setPatients(patientList);

        ObjectMapper objectMapper = new ObjectMapper();

        when(mediNetController.addHospital(hospital)).thenReturn("Hospital Added Successfully!");

        MvcResult result = mockMvc.perform(post("/api/hospital/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hospital)))
                        .andExpect(status().isOk())
                        .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
        assertEquals("Hospital Added Successfully!", result.getResponse().getContentAsString());
    }

    @Test
    void testAddHospital_MissingName() throws Exception {
        Hospital hospital = new Hospital();
        hospital.setAddress("New Delhi");
        hospital.setPhoneNumber("+91 22222");

        ObjectMapper objectMapper = new ObjectMapper();

        when(mediNetController.addHospital(hospital)).thenThrow(new ValidationException("Patient validation failed: Missing first name"));

        MvcResult exceptionResult = mockMvc.perform(post("/api/hospital/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hospital)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("Patient validation failed: Missing first name"))
                        .andReturn();

        assertNotNull(exceptionResult.getResponse().getContentAsString());
    }

    @Test
    void testSuccess_getHospitalByPatientId() throws Exception {
        Long patientId = 123L;
        Patient patient = new Patient();
        patient.setId(patient.getId());

        Hospital hospital = new Hospital();
        hospital.setName("BHU");
        hospital.setAddress("BSB");
        hospital.setPhoneNumber("+91 33333");
        patient.setHospital(hospital);

        when(mediNetController.getHospitalByPatientId(123L)).thenReturn(patient.getHospital());

        MvcResult result = mockMvc.perform(get("/api/hospital/gethospitalbyid/{patientID}", patientId))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
        //assertThrows(NotFoundException.class, () -> mediNetController.getHospitalByPatientId(123L));
    }

    @Test
    void testfail_getHospitalById_NotFoundException() throws Exception {
        Long patientId = 123L;

        when(mediNetController.getHospitalByPatientId(patientId)).thenThrow(new NotFoundException("Patient not found"));

        this.mockMvc.perform(get("/api/hospital/gethospitalbyid/{patientId}", patientId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThrows(NotFoundException.class, () -> mediNetController.getHospitalByPatientId(patientId));
    }

    @Test
    void testfail_getHospitalById_HospitalNotFound() throws Exception {
        Long patientId = 123L;

        when(mediNetController.getHospitalByPatientId(patientId)).thenThrow(new NotFoundException("Hospital Not Exist !"));

        MvcResult exceptionResult = mockMvc.perform(get("/api/hospital/gethospitalbyid/{patientId}", patientId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThrows(NotFoundException.class, () -> mediNetController.getHospitalByPatientId(patientId));
    }

    @Test
    void testSuccess_getPatientCount() throws Exception {
        Long hospitalId = 1L;
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId);
        hospital.setName("BHU");
        hospital.setAddress("BSB");
        hospital.setPhoneNumber("+91 33333");

        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());
        patientList.add(new Patient());
        hospital.setPatients(patientList);

        when(mediNetController.getPatientCount(hospital.getId())).thenReturn(hospital.getPatients().size());

        MvcResult result = mockMvc.perform(get("/api/hospital/getpatientCount/{hospitalId}", hospitalId))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(String.valueOf(hospital.getPatients().size()), result.getResponse().getContentAsString());
    }

    @Test
    void testfail_getPatientCount_NotFoundException() throws Exception {
        Long hospitalId = 123L;
        when(mediNetController.getPatientCount(hospitalId)).thenThrow(new NotFoundException("Hospital Not Found !"));

        MvcResult exceptionResults = mockMvc.perform(get("/api/hospital/getpatientCount/{hospitalId}", hospitalId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThrows(NotFoundException.class, () -> mediNetController.getPatientCount(hospitalId));

    }
}