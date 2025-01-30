package com.stage.HRplatform.service;

import com.stage.HRplatform.entity.Conge;
import com.stage.HRplatform.entity.CongeStatus;
import com.stage.HRplatform.entity.User;
import com.stage.HRplatform.repository.CongeRepository;
import com.stage.HRplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CongeService {

    private final CongeRepository congeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CongeService(CongeRepository congeRepository, UserRepository userRepository) {
        this.congeRepository = congeRepository;
        this.userRepository = userRepository;
    }

    // Create a leave request
    @Transactional
    public Conge createLeaveRequest(Conge conge) {
        try {
            // Fetch the user to associate with the leave request
            User user = userRepository.findById(conge.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + conge.getUser().getId()));

            conge.setUser(user);  // Set the user on the leave request

            // Save the leave request (will throw OptimisticLockingFailureException if the record was modified by another transaction)
            return congeRepository.save(conge);

        } catch (OptimisticLockingFailureException ex) {
            // Handle optimistic locking failure - inform the user or take appropriate action
            throw new RuntimeException("Optimistic lock failure: the leave request was modified by another user.");
        } catch (Exception ex) {
            // Handle other exceptions, such as user not found or any unexpected errors
            throw new RuntimeException("An error occurred while creating the leave request: " + ex.getMessage());
        }
    }

    // Get all leave requests
    public List<Conge> getAllLeaveRequests() {
        return congeRepository.findAll();
    }

    // Get leave request by ID
    public Optional<Conge> getLeaveRequestById(Long id) {
        return congeRepository.findById(id);
    }

    // Get leave requests by employee ID
    public List<Conge> getLeaveRequestsByEmployeeId(Long employeeId) {
        return congeRepository.findByUserId(employeeId);
    }

    // Update the status of a leave request
    public Conge updateLeaveRequestStatus(Long id, String status) {
        Optional<Conge> congeOptional = congeRepository.findById(id);
        if (congeOptional.isPresent()) {
            Conge conge = congeOptional.get();

            try {
                // Convert the String status to the CongeStatus enum
                CongeStatus congeStatus = CongeStatus.valueOf(status.toUpperCase());  // Ensuring it's case insensitive
                conge.setStatus(congeStatus);  // Set the status as the enum value
                return congeRepository.save(conge);
            } catch (IllegalArgumentException e) {
                // Handle the case where the status is invalid (e.g., not a valid enum value)
                System.err.println("Invalid status value: " + status);
                return null;
            }
        }
        return null;  // Return null if the Conge is not found
    }


    // Delete a leave request
    public void deleteLeaveRequest(Long id) {
        congeRepository.deleteById(id);
    }

    @Transactional
    public Conge acceptLeaveRequest(Long id) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        conge.setStatus(CongeStatus.valueOf("Validé"));  // Update the status to "ACCEPTED"
        return congeRepository.save(conge);  // Save the updated leave request
    }

    @Transactional
    public Conge rejectLeaveRequest(Long id) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        conge.setStatus(CongeStatus.valueOf("Refusé"));  // Update the status to "REJECTED"
        return congeRepository.save(conge);  // Save the updated leave request
    }
}
