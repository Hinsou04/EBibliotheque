package Labo.Academie.serviceImpl;

import Labo.Academie.dto.EmpruntRequest;
import Labo.Academie.dto.EmpruntResponse;
import Labo.Academie.enume.StatutEmprunt;
import Labo.Academie.modele.Emprunt;
import Labo.Academie.modele.Livre;
import Labo.Academie.modele.Membre;
import Labo.Academie.repository.EmpruntRepository;
import Labo.Academie.repository.LivreRepository;
import Labo.Academie.repository.MembreRepository;
import Labo.Academie.service.EmpruntService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpruntServiceImpl implements EmpruntService {
    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;

    @Override
    public List<EmpruntResponse> getAllEmprunt() {
        return empruntRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmpruntResponse getEmpruntById(Long id) {
        return mapToResponse(empruntRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable")));
    }

    @Override
    public EmpruntResponse createEmprunt(EmpruntRequest request) {

        Livre livre = livreRepository.findById(request.getLivreId())
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        if (livre.getExemplaireDisponibles()<=0){
            throw new RuntimeException("Aucun exemplaire disponible");
        }

        Membre membre = membreRepository.findById(request.getMembreId())
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));

        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(livre);
        emprunt.setMembre(membre);
        emprunt.setDateEmprunt(LocalDate.now().atStartOfDay());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14).atStartOfDay());
        emprunt.setStatutEmprunt(StatutEmprunt.EN_COURS);

        livre.setExemplaireDisponibles(livre.getExemplaireDisponibles()-1);
        livreRepository.save(livre);

        return mapToResponse(empruntRepository.save(emprunt));
    }

    @Override
    public EmpruntResponse retournerLivre(Long empruntId, Map<String, String> body) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));

        // Prendre la date utiliser aujourd'hui
        String dateStr = body.get("dateRetourEffectif");
        if (dateStr!=null){
            emprunt.setDateRetourEffectif(LocalDateTime.now());
        } else {
            emprunt.setDateRetourEffectif(LocalDateTime.now());
        }

        // Mettre RETOURNE par defaut
        String statut = body.get("statut");
        if (statut!=null){
            emprunt.setStatutEmprunt(StatutEmprunt.valueOf(statut));
        } else {
            emprunt.setStatutEmprunt(StatutEmprunt.RETOURNE);
        }
        Livre livre = emprunt.getLivre();
        livre.setExemplaireDisponibles(livre.getExemplaireDisponibles()+1);
        livreRepository.save(livre);
        return mapToResponse(empruntRepository.save(emprunt));
    }

    @Override
    public EmpruntResponse prolongerEmprunt(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));

        emprunt.setDateRetourPrevue(emprunt.getDateRetourPrevue().plusDays(7));

        return mapToResponse(empruntRepository.save(emprunt));
    }

    @Override
    public List<EmpruntResponse> getEmpruntByMembre(Long membreId) {
        return empruntRepository.findByMembreId(membreId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpruntResponse> getEmpruntsEnRetard() {
        return empruntRepository.findEmpruntsEnRetard(LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void mettreAJourStatutsEnRetard() {
        List<Emprunt> emprunts = empruntRepository.findEmpruntsEnRetard(LocalDate.now());
        emprunts.forEach(e -> e.setStatutEmprunt(StatutEmprunt.EN_RETARD));
        empruntRepository.saveAll(emprunts);
    }

    private EmpruntResponse mapToResponse(Emprunt e) {
        return EmpruntResponse.builder()
                .id(e.getId())
                .livreId(e.getLivre().getId())
                .LivreTitre(e.getLivre().getTitre())
                .livreAuteur(e.getLivre().getAuteur())
                .membreId(e.getMembre().getId())
                .membreNomComplet(e.getMembre().getPrenom()+""+e.getMembre().getNom())
                .membreUsername(e.getMembre().getUsername())
                .dateEmprunt(e.getDateEmprunt())
                .dateRetourPrevue(e.getDateRetourPrevue())
                .dateRetourEffectif(e.getDateRetourEffectif())
                .statut(e.getStatutEmprunt().name())
                .penalite(e.getPenalite())
                .build();
    }
}
