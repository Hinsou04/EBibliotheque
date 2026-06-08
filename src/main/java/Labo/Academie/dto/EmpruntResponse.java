package Labo.Academie.dto;

import Labo.Academie.enume.StatutEmprunt;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpruntResponse {
    private Long id;
    private Long livreId;
    private String LivreTitre;
    private String livreAuteur;
    private Long membreId;
    private String membreNomComplet;
    private String membreUsername;
    private LocalDateTime dateEmprunt;
    private LocalDateTime dateRetourPrevue;
    private LocalDateTime dateRetourEffectif;
    private String statut;
    private BigDecimal penalite;
}
