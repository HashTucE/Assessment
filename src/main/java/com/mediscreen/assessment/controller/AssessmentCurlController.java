package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.proxy.PatientProxy;
import com.mediscreen.assessment.service.AssessmentService;
import com.mediscreen.library.dto.PatientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssessmentCurlController {


    private final AssessmentService assessmentService;
    private final PatientProxy patientProxy;

    public AssessmentCurlController(AssessmentService assessmentService, PatientProxy patientProxy) {
        this.assessmentService = assessmentService;
        this.patientProxy = patientProxy;
    }

    private static final Logger log = LogManager.getLogger(AssessmentCurlController.class);


    /**
     * Endpoint for performing a diabetes assessment on a patient based on their id
     * @param patId the ID of the patient to assess
     * @return a response with the assessment result
     */
    @PostMapping("/assess/id")
    @Operation(summary = "Assess patient by ID", description = "Calculates diabetes risk level for a patient with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessment completed successfully", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> assessPatientById(@RequestParam int patId) {

        PatientDto patient = patientProxy.findPatientById(patId);
        if (patient == null) {
            log.error("Patient not found for ID: {}", patId);
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
        int age = assessmentService.calculateAge(patient.getBirthdate());
        String diabetesRisk = assessmentService.calculateDiabetesRisk(patient.getId());
        log.info("Assessment completed for patient {} {} with risk level: {}", patient.getFirstName(),
                patient.getLastName(), diabetesRisk);
        return new ResponseEntity<>(String.format("Patient: %s %s (age %d) diabetes assessment is: %s",
                patient.getFirstName(), patient.getLastName(), age, diabetesRisk),
                HttpStatus.OK);
    }


    /**
     * Endpoint for performing a diabetes assessment on a patient based on their family name
     * @param familyName the family name of the patient to assess
     * @return a response with the assessment result
     */
    @PostMapping("/assess/familyName")
    @Operation(summary = "Assess patient by family name", description = "Assesses the risk of diabetes for a patient by their family name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assessment successful",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> assessPatientByFamilyName(@RequestParam String familyName) {

        PatientDto patient = patientProxy.findPatientByFamilyName(familyName);
        if (patient == null) {
            log.error("Patient not found for family name: {}", familyName);
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
        int age = assessmentService.calculateAge(patient.getBirthdate());
        String diabetesRisk = assessmentService.calculateDiabetesRisk(patient.getId());
        log.info("Assessment completed for patient {} {} with risk level: {}", patient.getFirstName(),
                patient.getLastName(), diabetesRisk);
        return new ResponseEntity<>(String.format("Patient: %s %s (age %d) diabetes assessment is: %s",
                patient.getFirstName(), patient.getLastName(), age, diabetesRisk),
                HttpStatus.OK);
    }
}
