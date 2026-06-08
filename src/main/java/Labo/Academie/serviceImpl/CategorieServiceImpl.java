package Labo.Academie.serviceImpl;

import Labo.Academie.dto.CategorieRequest;
import Labo.Academie.dto.CategorieResponse;
import Labo.Academie.modele.Categorie;
import Labo.Academie.repository.CategorieRepository;
import Labo.Academie.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {
    private final CategorieRepository categorieRepository;

    @Override
    public List<CategorieResponse> getAllCategorie() {
        return categorieRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategorieResponse> getAllCategories() {
        return categorieRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategorieResponse getCategorieById(Long id) {
        return mapToResponse(categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable")));
    }

    @Override
    public CategorieResponse createCategorie(CategorieRequest request) {
        Categorie c = new Categorie();
        c.setNom(request.getNom());
        c.setDescription(request.getDescription());

        return mapToResponse(categorieRepository.save(c));
    }

    @Override
    public CategorieResponse updateCategorie(Long id, CategorieRequest request) {
        Categorie c = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        c.setNom(request.getNom());

        return mapToResponse(categorieRepository.save(c));
    }

    @Override
    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

    private CategorieResponse mapToResponse(Categorie c) {
        return new CategorieResponse(
                c.getId(),
                c.getNom(),
                c.getDescription(),
                c.getNombreLivre()
        );
    }
}
