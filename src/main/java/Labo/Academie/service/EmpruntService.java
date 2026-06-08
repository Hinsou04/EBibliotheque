package Labo.Academie.service;

import Labo.Academie.dto.EmpruntRequest;
import Labo.Academie.dto.EmpruntResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmpruntService {
    List<EmpruntResponse> getAllEmprunt();

    EmpruntResponse getEmpruntById(Long id);
    EmpruntResponse createEmprunt(EmpruntRequest resquest);
    EmpruntResponse retournerLivre(Long empruntId, Map<String, String> body);
    EmpruntResponse prolongerEmprunt(Long empruntId);

    List<EmpruntResponse>getEmpruntsEnRetard();
    List<EmpruntResponse>getEmpruntByMembre(Long membreId);

    void mettreAJourStatutsEnRetard();
}
