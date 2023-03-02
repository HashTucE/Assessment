package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.service.AssessmentService;
import com.mediscreen.library.dto.RecordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssessmentControllerTest {


    @InjectMocks
    private AssessmentController assessmentController;
    @Mock
    private AssessmentService assessmentService;


    @Test
    @DisplayName("Should assert equality")
    public void generateRecordTest() {

        // Arrange
        int patientId = 1;
        RecordDto expectedRecord = new RecordDto();
        when(assessmentService.createRecord(patientId)).thenReturn(expectedRecord);

        // Act
        ResponseEntity<RecordDto> actualResponse = assessmentController.generateRecord(patientId);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(actualResponse.getBody(), expectedRecord);
    }
}

