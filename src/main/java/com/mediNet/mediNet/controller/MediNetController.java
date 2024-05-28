package com.mediNet.mediNet.controller;

import com.mediNet.mediNet.model.Hospital;
import com.mediNet.mediNet.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/hospital/")
public class MediNetController {

    private final HospitalService hospitalService;

    public MediNetController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping("")
    public String addHospital(@RequestBody Hospital hospital) {
        log.info("Add Hospital: {}", hospital.toString());
        return hospitalService.addHospital(hospital);
    }

    @GetMapping("gethospitalbyid/{patientId}")
    public Hospital getHospitalByPatientId(@PathVariable Long patientId){
        log.info("Get the hospital by patient Id : {}", patientId);
        return hospitalService.getHospitalByPatientId(patientId);
    }
    @GetMapping("getpatientCount/{hospitalId}")
    public int getPatientCount(@PathVariable Long hospitalId){
        log.info("Get the patient count : {}", hospitalId);
        return hospitalService.getPatientCount(hospitalId);
    }
}

