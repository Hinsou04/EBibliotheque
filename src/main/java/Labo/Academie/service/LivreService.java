package Labo.Academie.service;

import Labo.Academie.dto.LivreRequest;
import Labo.Academie.dto.LivreResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LivreService {
    List<LivreResponse> getAllLivres();
    LivreResponse createLivre(LivreRequest request);

    LivreResponse updateLivre(Long id, LivreRequest request);

    void deleteLivre(Long id);
    List<LivreResponse>searchLivre(String query);
    List<LivreResponse>getLivreDisponible();

    List<LivreResponse>getLivresByCategorie(Long categorieId);
//    LivreResponse updateLivre(LivreRequest request);
    LivreResponse getLivreById(Long livreId);
}
