package Labo.Academie.service;

import Labo.Academie.dto.MembreRequest;
import Labo.Academie.dto.MembreResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MembreService {
    List<MembreResponse> getAllMembres();
    MembreResponse getMembreById(Long id);
    MembreResponse updateMembre(Long id, MembreRequest request);
    void deleteMembreById(Long id);
    MembreResponse toggleActivation(Long id);
    MembreResponse changerRole(Long id, String role);
}
