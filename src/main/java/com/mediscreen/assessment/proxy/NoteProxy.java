package com.mediscreen.assessment.proxy;

import com.mediscreen.library.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "note.ms", url = "${proxy.note.url}")
public interface NoteProxy {


    @GetMapping("/api/note/list/{patientId}")
    List<NoteDto> getNotesByPatientId(@PathVariable int patientId);
}
