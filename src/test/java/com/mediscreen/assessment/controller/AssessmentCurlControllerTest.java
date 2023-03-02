package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.proxy.PatientProxy;
import com.mediscreen.assessment.service.AssessmentService;
import com.mediscreen.library.dto.PatientDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssessmentCurlControllerTest {


    @Mock
    private AssessmentService assessmentService;
    @Mock
    private PatientProxy patientProxy;
    @InjectMocks
    private AssessmentCurlController assessmentCurlController;


    @Test
    @DisplayName("Should return patient when found with message")
    public void assessPatientByIdTest() {

        // Arrange
        int patId = 1;
        PatientDto patient = new PatientDto();
        patient.setId(patId);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthdate(LocalDate.of(1980, 01, 01));
        when(patientProxy.findPatientById(patId)).thenReturn(patient);
        when(assessmentService.calculateAge(patient.getBirthdate())).thenReturn(43);
        when(assessmentService.calculateDiabetesRisk(patient.getId())).thenReturn("High");

        // Act
        ResponseEntity<String> result = assessmentCurlController.assessPatientById(patId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Patient: John Doe (age 43) diabetes assessment is: High", result.getBody());
    }


    @Test
    @DisplayName("Should display patient not found")
    public void assessPatientByIdNegativeTest() {

        // Arrange
        int patId = 1;
        when(patientProxy.findPatientById(patId)).thenReturn(null);

        // Act
        ResponseEntity<String> result = assessmentCurlController.assessPatientById(patId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Patient not found", result.getBody());
    }


    @Test
    @DisplayName("Should return patient with message")
    public void assessPatientByFamilyNameTest() {

        // Arrange
        String familyName = "Doe";
        PatientDto patient = new PatientDto();
        patient.setId(1);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthdate(LocalDate.of(1980, 01, 01));
        when(patientProxy.findPatientByFamilyName(familyName)).thenReturn(patient);
        when(assessmentService.calculateAge(patient.getBirthdate())).thenReturn(43);
        when(assessmentService.calculateDiabetesRisk(patient.getId())).thenReturn("High");

        // Act
        ResponseEntity<String> result = assessmentCurlController.assessPatientByFamilyName(familyName);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Patient: John Doe (age 43) diabetes assessment is: High", result.getBody());
    }
}
