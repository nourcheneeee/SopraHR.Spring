package com.stage.HRplatform.controller;

import com.stage.HRplatform.entity.Conge;
import com.stage.HRplatform.service.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PutMapping("/{id}")
    public ResponseEntity<Conge> updateLeaveRequestStatus(@PathVariable Long id, @RequestParam String status) {
        Conge updatedLeaveRequest = congeService.updateLeaveRequestStatus(id, status);
        if (updatedLeaveRequest != null) {
            return ResponseEntity.ok(updatedLeaveRequest);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        congeService.deleteLeaveRequest(id);
        return ResponseEntity.noContent().build();
    }
}
