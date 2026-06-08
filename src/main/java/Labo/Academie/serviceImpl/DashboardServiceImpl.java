package Labo.Academie.serviceImpl;

import Labo.Academie.dto.DashboardResponse;
import Labo.Academie.dto.EmpruntResponse;
import Labo.Academie.dto.LivreResponse;
import Labo.Academie.dto.MembreResponse;
import Labo.Academie.enume.StatutEmprunt;
import Labo.Academie.repository.CategorieRepository;
import Labo.Academie.repository.EmpruntRepository;
import Labo.Academie.repository.LivreRepository;
import Labo.Academie.repository.MembreRepository;
import Labo.Academie.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;
    private final EmpruntRepository empruntRepository;
    private final CategorieRepository categorieRepository;

    @Override
    public DashboardResponse getDashboardStats() {

        List<LivreResponse> livresLesPlusEmpruntes = livreRepository
                .findAll()
                .stream()
                .limit(5)
                .map(l->LivreResponse.builder()
                        .id(l.getId())
                        .titre(l.getTitre())
                        .auteur(l.getAuteur())
                        .isbn(l.getIsbn())
                        .description(l.getDescription())
                        .datePublication(l.getDatePublication())
                        .nombreExemplaires(l.getNombreExemplaires())
                        .imageUrl(l.getImage_Url())
                        .categorie(l.getCategorie()!=null?
                                l.getCategorie().getNom():null)
                        .disponible(l.getExemplaireDisponibles()!=null &&
                                l.getExemplaireDisponibles()>0)
                        .build())
                .collect(Collectors.toList());

        List<EmpruntResponse> empruntsEnRetardDetails = empruntRepository
                .findByStatutEmprunt(StatutEmprunt.EN_RETARD)
                .stream()
                .map(e->EmpruntResponse.builder()
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
                        .build())
                .collect(Collectors.toList());

        List<MembreResponse> membresActifs = membreRepository
                .findByActif(true)
                .stream()
                .map(m -> MembreResponse.builder()
                        .id(m.getId())
                        .nom(m.getNom())
                        .prenom(m.getPrenom())
                        .email(m.getEmail())
                        .username(m.getUsername())
                        .role(m.getRole().name())
                        .actif(m.isActif())
                        .build())
                .collect(Collectors.toList());
        return DashboardResponse.builder()
                .totalLivres(livreRepository.count())
                .totalMembres(membreRepository.count())
                .empruntEncours(empruntRepository.countByStatutEmprunt(StatutEmprunt.EN_COURS))
                .empruntEnRetard(empruntRepository.countByStatutEmprunt(StatutEmprunt.EN_RETARD))
                .totalCategories(categorieRepository.count())
                .livresLesPlusEmpruntes(livresLesPlusEmpruntes)
                .empruntsEnRetardDetails(empruntsEnRetardDetails)
                .membresActifs(List.of())
                .build();
    }
}
