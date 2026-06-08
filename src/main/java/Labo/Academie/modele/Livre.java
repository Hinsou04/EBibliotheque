package Labo.Academie.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livres")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String titre;

    @NotBlank(message = "L'auteur est obligatoire")
    @Column(nullable = false)
    private String auteur;

    @Column(unique = true)
    private String isbn;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @Column(name = "nombre_exemplaire", nullable = false)
    @Min(value = 0, message = "le nombre d'exemplaire ne peut pas etre negatif")
    private Integer nombreExemplaires = 1;

    @Column(name = "exemplaire_disponible", nullable = false)
    private  Integer exemplaireDisponibles = 0;

    @Column(name = "imageUrl")
    private String image_Url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emprunt> emprunts = new ArrayList<>();
}
