package Labo.Academie.repository;

import Labo.Academie.modele.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre,Long> {
    @Query("SELECT l FROM Livre l WHERE " + "LOWER(l.titre)LIKE LOWER(CONCAT('%',:query,'%'))OR "+"LOWER(l.isbn)LIKE LOWER(CONCAT('%',:query,'%'))")
    List<Livre> rechercherParMotCle(@Param("query")String query);
    List<Livre>findByCategorieId(Long categorieId);
    List<Livre>findByAuteurContainingIgnoreCase(String auteur);
    Optional<Livre> findById(Long id);

    @Query("SELECT l FROM Livre l JOIN l.emprunts e GROUP BY l  ORDER BY COUNT(e)DESC")
    List<Livre>findLivresLesPlusEmpruntes();
    List<Livre>findByTitreContaining(String titre);
    List<Livre>findByExemplaireDisponiblesGreaterThan(int nombre);
//    List<Livre>findByDisponibleTrue();
}
