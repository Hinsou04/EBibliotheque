package Labo.Academie.service;

import Labo.Academie.dto.CategorieRequest;
import Labo.Academie.dto.CategorieResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategorieService {
    List<CategorieResponse> getAllCategorie();

    List<CategorieResponse> getAllCategories();

    CategorieResponse getCategorieById(Long id);

    @Transactional
    CategorieResponse createCategorie(CategorieRequest request);

    void deleteCategorie(Long id);
    CategorieResponse updateCategorie(Long id, CategorieRequest resquest);
}
