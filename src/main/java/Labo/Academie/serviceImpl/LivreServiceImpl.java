package Labo.Academie.serviceImpl;

import Labo.Academie.dto.LivreRequest;
import Labo.Academie.dto.LivreResponse;
import Labo.Academie.modele.Categorie;
import Labo.Academie.modele.Livre;
import Labo.Academie.repository.CategorieRepository;
import Labo.Academie.repository.LivreRepository;
import Labo.Academie.service.LivreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivreServiceImpl implements LivreService {
    private final LivreRepository livreRepository;
    private final CategorieRepository categorieRepository;
    @Override
    public List<LivreResponse> getAllLivres() {
        return livreRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LivreResponse getLivreById(Long id) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));
        return mapToResponse(livre);
    }

    @Override
    public LivreResponse createLivre(LivreRequest request) {
        Livre livre = new Livre();
        livre.setTitre(request.getTitre());
        livre.setAuteur(request.getAuteur());
        livre.setIsbn(request.getIsbn());
        livre.setDescription(request.getDescription());
        Integer nombreExemplaire = request.getNombreExemplaires()!= null?
                request.getNombreExemplaires() : 1;
        livre.setNombreExemplaires(request.getNombreExemplaires());
        livre.setExemplaireDisponibles(nombreExemplaire);
        livre.setDatePublication(request.getDatePublication());
        livre.setImage_Url(request.getImageUrl());

        if (request.getCategorieId()!=null){
            Categorie categorie = categorieRepository.findById(request.getCategorieId())
                    .orElseThrow(()->new RuntimeException("Categorie introuvable"));
            livre.setCategorie(categorie);
        }

        return mapToResponse(livreRepository.save(livre));
    }

    @Override
    public LivreResponse updateLivre(Long id, LivreRequest request) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        livre.setTitre(request.getTitre());
        livre.setAuteur(request.getAuteur());

        return mapToResponse(livreRepository.save(livre));
    }

    @Override
    public void deleteLivre(Long id) {
        livreRepository.deleteById(id);
    }

    @Override
    public List<LivreResponse> searchLivre(String query) {
        return livreRepository.findByTitreContaining(query).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LivreResponse> getLivreDisponible() {
        return livreRepository.findByExemplaireDisponiblesGreaterThan(0)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LivreResponse> getLivresByCategorie(Long categorieId) {
        return livreRepository.findByCategorieId(categorieId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private LivreResponse mapToResponse(Livre livre) {
        return LivreResponse.builder()
                .id(livre.getId())
                .titre(livre.getTitre())
                .auteur(livre.getAuteur())
                .isbn(livre.getIsbn())
                .description(livre.getDescription())
                .datePublication(livre.getDatePublication())
                .nombreExemplaires(livre.getNombreExemplaires())
                .exemplairesDisponibles(livre.getExemplaireDisponibles()!=null?
                        livre.getExemplaireDisponibles():0)
                .imageUrl(livre.getImage_Url())
                .categorie(livre.getCategorie()!=null?
                        livre.getCategorie().getNom():null)
                .disponible(livre.getExemplaireDisponibles()>0)
                .build();
    }
}
