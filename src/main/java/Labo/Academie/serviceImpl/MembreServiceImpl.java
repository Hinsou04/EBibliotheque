package Labo.Academie.serviceImpl;

import Labo.Academie.dto.MembreRequest;
import Labo.Academie.dto.MembreResponse;
import Labo.Academie.enume.Role;
import Labo.Academie.modele.Membre;
import Labo.Academie.repository.EmpruntRepository;
import Labo.Academie.repository.MembreRepository;
import Labo.Academie.service.MembreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembreServiceImpl implements MembreService {
    private final MembreRepository membreRepository;
    private final EmpruntRepository empruntRepository;

    @Override
    public List<MembreResponse> getAllMembres() {
        return membreRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MembreResponse getMembreById(Long id) {
        Membre membre = membreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        return mapToResponse(membre);
    }

    @Override
    public MembreResponse updateMembre(Long id, MembreRequest request) {
        Membre membre = membreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));

        membre.setNom(request.getNom());
        membre.setEmail(request.getEmail());

        return mapToResponse(membreRepository.save(membre));
    }

    @Override
    public void deleteMembreById(Long id) {
        membreRepository.deleteById(id);
    }

    @Override
    public MembreResponse toggleActivation(Long id) {
        Membre membre = membreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));

        membre.setActif(!membre.isActif());
        return mapToResponse(membreRepository.save(membre));
    }

    @Override
    public MembreResponse changerRole(Long id, String role){
        Membre membre = membreRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Membre introuvable"));
        membre.setRole(Role.valueOf(role));
        return mapToResponse(membreRepository.save(membre));
    }

    private MembreResponse mapToResponse(Membre membre) {
        return MembreResponse.builder()
                .id(membre.getId())
                .nom(membre.getNom())
                .prenom(membre.getPrenom())
                .email(membre.getEmail())
                .username(membre.getUsername())
                .role(membre.getRole().name())
                .actif(membre.isActif())
                .dateInscription(membre.getDateInscription())
                .nombreEmprunts(empruntRepository.countByMembreId(membre.getId()))
                .build();
    }
}
