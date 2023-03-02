package com.mediscreen.assessment.service;

import com.mediscreen.assessment.proxy.NoteProxy;
import com.mediscreen.assessment.proxy.PatientProxy;
import com.mediscreen.library.dto.NoteDto;
import com.mediscreen.library.dto.PatientDto;
import com.mediscreen.library.dto.RecordDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentService {


    private final PatientProxy patientProxy;
    private final NoteProxy noteProxy;

    private static String filepath;

    @Value("${triggers.url}")
    public void setFilePath(String url) {
        filepath =url;
    }

    public AssessmentService(PatientProxy patientProxy, NoteProxy noteProxy) {
        this.patientProxy = patientProxy;
        this.noteProxy = noteProxy;
    }


    private static final Logger log = LogManager.getLogger(AssessmentService.class);



    /**
     * Calculates the age of a patient given their birthdate
     * @param birthDate The birthdate of the patient
     * @return The age of the patient in years
     */
    public int calculateAge(LocalDate birthDate) {

        if (birthDate == null) {
            log.debug("birthDate is null, returning 0");
            return 0;
        }
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }


    /**
     * Reads the triggers from a file and returns a list of strings representing the triggers
     * @return A list of strings representing the triggers
     */
    public List<String> readTriggersFromFile() {

        List<String> triggers = new ArrayList<>();

        try {
            triggers = Files.readAllLines(Path.of(filepath));
        } catch (IOException e) {
            log.error("Error reading triggers from file", e);
        }
        return triggers;
    }


    /**
     * Counts the number of triggers in a list of notes
     * @param notes The list of notes to count the triggers in
     * @return The number of triggers in the list of notes
     */
    public int countTriggers(List<NoteDto> notes) {

        if (notes == null) {
            log.debug("notes is null, returning 0");
            return 0;
        }
        int count = 0;
        List<String> countedTriggers = new ArrayList<>();
        List<String> triggers = readTriggersFromFile();

        for (NoteDto note : notes) {
            for (String trigger : triggers) {
                if (note.getNote().toLowerCase().contains(trigger.toLowerCase()) && !countedTriggers.contains(trigger.toLowerCase())) {
                    count++;
                    countedTriggers.add(trigger.toLowerCase());
                }
            }
        }
        return count;
    }


    /**
     * Calculates the diabetes risk of a patient
     * @param patientId The id of the patient to calculate the diabetes risk for
     * @return The calculated diabetes risk for the patient
     */
    public String calculateDiabetesRisk(int patientId) {

        log.debug("Calculating diabetes risk for patient with id: {}", patientId);
        PatientDto patient = patientProxy.findPatientById(patientId);
        if (patient == null) {
            log.error("Patient with id {} not found", patientId);
            return "Patient not found";
        }

        int age = calculateAge(patient.getBirthdate());
        List<NoteDto> notes = noteProxy.getNotesByPatientId(patientId);
        int triggerCount = countTriggers(notes);

        if (triggerCount <= 1) {
            log.info("Patient with id {} has no diabetes risk", patientId);
            return "None";
        } else if (age >= 30 && triggerCount >= 2 && triggerCount <= 5) {
            log.info("Patient with id {} has borderline diabetes risk", patientId);
            return "Borderline";
        } else if (patient.getSex().equals("M") && age < 30 && triggerCount >= 3 && triggerCount <= 4) {
            log.info("Patient with id {} is in danger of developing diabetes", patientId);
            return "In Danger";
        } else if (patient.getSex().equals("F") && age < 30 && triggerCount >= 4 && triggerCount <= 6) {
            log.info("Patient with id {} is in danger of developing diabetes", patientId);
            return "In Danger";
        } else if (age >= 30 && triggerCount >= 6 && triggerCount <= 7) {
            log.info("Patient with id {} is in danger of developing diabetes", patientId);
            return "In Danger";
        } else if (patient.getSex().equals("M") && age < 30 && triggerCount >= 5) {
            log.info("Patient with id {} is at early onset of diabetes", patientId);
            return "Early onset";
        } else if (patient.getSex().equals("F") && age < 30 && triggerCount >= 7) {
            log.info("Patient with id {} is at early onset of diabetes", patientId);
            return "Early onset";
        } else if (age >= 30 && triggerCount >= 8) {
            log.info("Patient with id {} is at early onset of diabetes", patientId);
            return "Early onset";
        }
        log.warn("Unable to determine diabetes risk for patient with id {}", patientId);
        return "Unknown";
    }


    /**
     * Creates a record for a patient
     * @param patientId The id of the patient to create a record for
     * @return The created record for the patient
     */
    public RecordDto createRecord(int patientId) {

        log.debug("Attempting to create a record for patient with id: {}", patientId);
        PatientDto patient = patientProxy.findPatientById(patientId);
        List<NoteDto> notes = noteProxy.getNotesByPatientId(patientId);
        log.debug("Successfully created a record for patient with id: {}", patientId);

        return new RecordDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getSex(), patient.getBirthdate(),
                patient.getAddress(), patient.getPhone(), calculateAge(patient.getBirthdate()), calculateDiabetesRisk(patientId), notes);
    }
}
