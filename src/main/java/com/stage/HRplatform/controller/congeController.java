package com.stage.HRplatform.controller;

import com.stage.HRplatform.entity.Conge;
import com.stage.HRplatform.service.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conges")
public class congeController {

    private final CongeService congeService;

    @Autowired
    public congeController(CongeService congeService) {
        this.congeService = congeService;
    }

    @PostMapping
    public Conge createLeaveRequest(@RequestBody Conge conge) {
        return congeService.createLeaveRequest(conge);
    }


    @GetMapping
    public List<Conge> getAllLeaveRequests() {
        return congeService.getAllLeaveRequests();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Conge> getLeaveRequestById(@PathVariable Long id) {
        Optional<Conge> conge = congeService.getLeaveRequestById(id);
        return conge.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Conge>> getLeaveRequestsByEmployeeId(@PathVariable Long employeeId) {
        List<Conge> leaveRequests = congeService.getLeaveRequestsByEmployeeId(employeeId);
        return leaveRequests.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(leaveRequests);
    }
    @PutMapping("/accept/{id}")
    public ResponseEntity<Conge> acceptLeaveRequest(@PathVariable Long id) {
        try {
            Conge updatedConge = congeService.acceptLeaveRequest(id);
            return ResponseEntity.ok(updatedConge);  // Return the updated leave request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Error if not found
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Conge> rejectLeaveRequest(@PathVariable Long id) {
        try {
            Conge updatedConge = congeService.rejectLeaveRequest(id);
            return ResponseEntity.ok(updatedConge);  // Return the updated leave request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Error if not found
        }
    }

    @PutMapping("/conges/{id}")
    public ResponseEntity<Conge> updateLeaveRequestStatus(@PathVariable Long id, @RequestBody String status) {
        Conge updatedConge = congeService.updateLeaveRequestStatus(id, status);
        if (updatedConge != null) {
            return ResponseEntity.ok(updatedConge);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        congeService.deleteLeaveRequest(id);
        return ResponseEntity.noContent().build();
    }
}
