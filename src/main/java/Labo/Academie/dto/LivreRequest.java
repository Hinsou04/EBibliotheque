package Labo.Academie.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LivreRequest {
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "L'auteur est obligatoire")
    private String auteur;

    private String isbn;
    private String description;

    private LocalDate datePublication;

    @Min(value = 1, message = "Au moins 1 exemplaire requis")
    private Integer nombreExemplaires;
    private Integer exemplaireDisponibles;
    private String imageUrl;
    private Long categorieId;
}
