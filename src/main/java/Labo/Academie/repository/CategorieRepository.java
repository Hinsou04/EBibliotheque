package Labo.Academie.repository;

import Labo.Academie.modele.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    Optional<Categorie> findByNomIgnoreCase(String nom);
    boolean existsByNomIgnoreCase(String nom);
}
