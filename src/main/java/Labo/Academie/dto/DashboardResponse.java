package Labo.Academie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalLivres;
    private Long totalMembres;
    private Long empruntEncours;
    private Long empruntEnRetard;
    private  Long totalCategories;
    private List<LivreResponse> livresLesPlusEmpruntes;
    private List<EmpruntResponse>empruntsEnRetardDetails;
    private List<MembreResponse>membresActifs;
}
