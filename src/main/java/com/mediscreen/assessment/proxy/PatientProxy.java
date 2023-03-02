package com.mediscreen.assessment.proxy;

import com.mediscreen.library.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient.ms", url = "${proxy.patient.url}")
public interface PatientProxy {


    @GetMapping("/api/patient/find/{id}")
    PatientDto findPatientById(@PathVariable int id);

    @GetMapping("/api/patient/finder/{familyName}")
    PatientDto findPatientByFamilyName(@PathVariable String familyName);
}


