package Labo.Academie.repository;

import Labo.Academie.enume.Role;
import Labo.Academie.modele.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MembreRepository extends JpaRepository<Membre,Long> {
    Optional<Membre> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<Membre> findByRole(Role role);
    void deleteById(Long id);
    Optional<Membre>findByUsername(String username);

    @Query("SELECT m FROM Membre m WHERE m.actif = true ORDER BY m.dateInscription DESC")
    List<Membre>findByActif(Boolean actif);
}
