package pl.javastart.equipy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javastart.equipy.dto.AssignmentDto;
import pl.javastart.equipy.exceptions.InvalidAssignmentException;
import pl.javastart.equipy.service.AssignmentsService;

import java.net.URI;
import java.time.LocalDateTime;

@RequestMapping("/api/assignments")
@RestController
public class AssignmentController {

    private AssignmentsService assignmentsService;

    public AssignmentController(AssignmentsService assignmentsService) {
        this.assignmentsService = assignmentsService;
    }

    @PostMapping("")
    public ResponseEntity<AssignmentDto> saveAssignment(@RequestBody AssignmentDto assignmentDto){
        AssignmentDto createdAssignment;
        try{
            createdAssignment = assignmentsService.createAssignment(assignmentDto);
        }catch (InvalidAssignmentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(createdAssignment.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdAssignment);
    }
    @PostMapping("/{assignmentId}/end")
    public ResponseEntity<LocalDateTime> updateEndTime(@PathVariable Long assignmentId){
        LocalDateTime endTime;
        try {
            endTime = assignmentsService.updateEndTime(assignmentId);
        }catch (InvalidAssignmentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(endTime);
    }
}
