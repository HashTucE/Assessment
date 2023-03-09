package com.mediscreen.assessment.controller;

import com.mediscreen.assessment.service.AssessmentService;
import com.mediscreen.library.dto.RecordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {


    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    private static final Logger log = LogManager.getLogger(AssessmentController.class);



    /**
     * Generate a report from a patient id.
     * @param patientId the id of a patient
     * @return the response entity
     */
    @GetMapping("/record/{patientId}")
    @Operation(summary = "Generate patient record", description = "Generates a record for the patient with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record generated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecordDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RecordDto> generateRecord(@PathVariable int patientId) {

        log.debug("Generate record request received, id: {}", patientId);
        RecordDto record = assessmentService.createRecord(patientId);
        log.debug("Generate record request processed, id: {}", patientId);
        return ResponseEntity.ok(record);
    }
}
