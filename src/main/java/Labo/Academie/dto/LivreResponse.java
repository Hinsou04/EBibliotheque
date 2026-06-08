package Labo.Academie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivreResponse {
    private Long id;
    private String titre;
    private String auteur;
    private String isbn;
    private String description;
    private LocalDate datePublication;
    private Integer nombreExemplaires;
    private Integer exemplairesDisponibles;
    private String imageUrl;
    private String categorie;
    private boolean disponible;
}
