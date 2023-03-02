package com.mediscreen.assessment.service;

import com.mediscreen.assessment.proxy.NoteProxy;
import com.mediscreen.assessment.proxy.PatientProxy;
import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.library.dto.PatientDto;
import com.mediscreen.library.dto.RecordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTest {

    @Mock
    private PatientProxy patientProxy;
    @Mock
    private NoteProxy noteProxy;
    @Spy
    @InjectMocks
    private AssessmentService assessmentService;



    @Test
    @DisplayName("Should assert equality")
    public void calculateAgeTest() {

        // Arrange
        LocalDate birthDate = LocalDate.of(2000, 01, 01);

        // Act
        int age = assessmentService.calculateAge(birthDate);

        // Assert
        assertEquals(23, age);
    }


    @Test
    @DisplayName("Should assert trigger count")
    public void countTriggersTest() {

        // Arrange
        List<NoteDto> notes = new ArrayList<>();
        notes.add(new NoteDto(1, "note 1"));
        notes.add(new NoteDto(1, "note 2"));
        notes.add(new NoteDto(1, "note 2"));
        List<String> triggers = List.of("1", "2");
        doReturn(triggers).when(assessmentService).readTriggersFromFile();

        // Act
        int triggerCount = assessmentService.countTriggers(notes);

        // Assert
        assertEquals(2, triggerCount);
    }


    @Test
    @DisplayName("Should display Patient not found")
    public void calculateDiabetesRiskNegativeTest() {

        //Arrange
        int patientId = 999;
        when(patientProxy.findPatientById(patientId)).thenReturn(null);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("Patient not found", result);
    }


    @Test
    @DisplayName("Should display None when less 1 trigger")
    public void calculateDiabetesRiskTest() {

        //Arrange
        int patientId = 1;
        int age = 20;
        int triggers = 1;
        PatientDto patient = new PatientDto("John", "Doe");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("None", result);
    }


    @Test
    @DisplayName("Should display Borderline when more 30 and more 2 triggers")
    public void calculateDiabetesRiskTest2() {

        //Arrange
        int patientId = 1;
        int age = 30;
        int triggers = 4;
        PatientDto patient = new PatientDto("John", "Doe");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("Borderline", result);
    }


    @Test
    @DisplayName("Should display In Danger when male, less 30 and more 3 triggers")
    public void calculateDiabetesRiskTest3() {

        //Arrange
        int patientId = 1;
        int age = 20;
        int triggers = 4;
        PatientDto patient = new PatientDto("John", "Doe");
        patient.setSex("M");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("In Danger", result);
    }


    @Test
    @DisplayName("Should display In Danger when female, less 30 and more 4 triggers")
    public void calculateDiabetesRiskTest4() {

        //Arrange
        int patientId = 1;
        int age = 20;
        int triggers = 6;
        PatientDto patient = new PatientDto("John", "Doe");
        patient.setSex("F");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("In Danger", result);
    }


    @Test
    @DisplayName("Should display In Danger when more 30 and more 6 triggers")
    public void calculateDiabetesRiskTest5() {

        //Arrange
        int patientId = 1;
        int age = 38;
        int triggers = 7;
        PatientDto patient = new PatientDto("John", "Doe");
        patient.setSex("F");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("In Danger", result);
    }


    @Test
    @DisplayName("Should display Early onset when male, less 30 and more 5 triggers")
    public void calculateDiabetesRiskTest6() {

        //Arrange
        int patientId = 1;
        int age = 20;
        int triggers = 5;
        PatientDto patient = new PatientDto("John", "Doe");
        patient.setSex("M");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("Early onset", result);
    }


    @Test
    @DisplayName("Should display Early onset when female, less 30 and more 7 triggers")
    public void calculateDiabetesRiskTest7() {

        //Arrange
        int patientId = 1;
        int age = 20;
        int triggers = 7;
        PatientDto patient = new PatientDto("John", "Doe");
        patient.setSex("F");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("Early onset", result);
    }


    @Test
    @DisplayName("Should display Early onset when more 30 and more 8 triggers")
    public void calculateDiabetesRiskTest8() {

        //Arrange
        int patientId = 1;
        int age = 30;
        int triggers = 9;
        PatientDto patient = new PatientDto("John", "Doe");
        patient.setSex("F");
        when(assessmentService.calculateAge(any())).thenReturn(age);
        when(assessmentService.countTriggers(any())).thenReturn(triggers);
        when(patientProxy.findPatientById(patientId)).thenReturn(patient);

        //Act
        String result = assessmentService.calculateDiabetesRisk(patientId);

        //Assert
        assertEquals("Early onset", result);
    }


    @Test
    @DisplayName("Should assert equal record")
    public void testCreateRecord() {

        // Arrange
        int patientId = 1;
        LocalDate birthDate = LocalDate.of(2000, 01, 01);
        PatientDto patient = new PatientDto("John", "Doe", birthDate, "M", "a", "p");
        patient.setId(1);

        List<NoteDto> notes = new ArrayList<>();
        notes.add(new NoteDto(1,"note 1"));
        notes.add(new NoteDto(1,"note 2"));

        when(patientProxy.findPatientById(patientId)).thenReturn(patient);
        when(noteProxy.getNotesByPatientId(patientId)).thenReturn(notes);
        doReturn(30).when(assessmentService).calculateAge(any());
        doReturn("None").when(assessmentService).calculateDiabetesRisk(1);

        RecordDto expected = new RecordDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getSex(), patient.getBirthdate(),
                patient.getAddress(), patient.getPhone(), 30, "None", notes);

        // Act
        RecordDto actual = assessmentService.createRecord(patientId);

        // Assert
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getNotes(), actual.getNotes());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPhone(), actual.getPhone());
    }
}
