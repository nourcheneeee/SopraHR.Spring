package com.stage.HRplatform.service;

import com.stage.HRplatform.entity.Conge;
import com.stage.HRplatform.entity.User;
import com.stage.HRplatform.repository.CongeRepository;
import com.stage.HRplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Conge createLeaveRequest(Conge conge) {

        User user = userRepository.findById(conge.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + conge.getUser().getId()));
        conge.setUser(user);


        return congeRepository.save(conge);
    }

    public List<Conge> getAllLeaveRequests() {
        return congeRepository.findAll();
    }


    public Optional<Conge> getLeaveRequestById(Long id) {
        return congeRepository.findById(id);
    }


    public Conge updateLeaveRequestStatus(Long id, String status) {
        Optional<Conge> congeOptional = congeRepository.findById(id);
        if (congeOptional.isPresent()) {
            Conge conge = congeOptional.get();
            conge.setStatut(status);
            return congeRepository.save(conge);
        }
        return null;
    }


    public void deleteLeaveRequest(Long id) {
        congeRepository.deleteById(id);
    }
}
