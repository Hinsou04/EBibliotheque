package Labo.Academie.repository;

import Labo.Academie.enume.StatutEmprunt;
import Labo.Academie.modele.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt,Long> {
    List<Emprunt> findByMembreId(Long menbreId);
    List<Emprunt>findByLivreId(Long livreId);
    List<Emprunt>findByStatutEmprunt(StatutEmprunt statutEmprunt);
    Optional<Emprunt> findById(Long empruntId);
    long countByMembreId(Long membreId);

    @Query("SELECT e FROM Emprunt e WHERE e.membre.id = :membreId AND e.statutEmprunt IN ('EN_COURS','PROLONGE',EN_RETARD)")
    List<Emprunt>findEmpruntsActifsByMembre(@Param("membreId")Long membreId);

    @Query("SELECT COUNT(e) FROM Emprunt e WHERE e.statutEmprunt = 'EN_COURS'")
    long countEmpruntsEncours();

    @Query("SELECT COUNT(e) FROM Emprunt e WHERE e.statutEmprunt = 'EN_COURS'AND e.dateRetourPrevue<:today")
    long countEmpruntsEnretard(@Param("today") LocalDate today);
    @Query("SELECT e FROM Emprunt e WHERE e.dateRetourPrevue<:date AND e.statutEmprunt='EN_COURS'")
    List<Emprunt>findEmpruntsEnRetard(@Param("date")LocalDate date);

    long countByStatutEmprunt(StatutEmprunt statutEmprunt);
}
