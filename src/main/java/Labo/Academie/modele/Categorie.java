package Labo.Academie.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la categorie est obligatoire")
    @Column(nullable = false,unique = true)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Livre> livres = new ArrayList<>();

    private Integer nombreLivre = 0;
}
