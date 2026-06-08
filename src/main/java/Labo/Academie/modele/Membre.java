package Labo.Academie.modele;

import Labo.Academie.enume.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "membres",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Membre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50)
    @Column(nullable = false,unique = true)
    private String username;

    @NotBlank(message = "Le prenom est obligatoire")
    @Column(nullable = false)
    private String prenom;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    @Column(nullable = false,unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.MEMBRE;

    @Column(name = "date_inscription", nullable = false,updatable = false)
    private LocalDateTime dateInscription;

    @Column(name = "actif", nullable = false)
    @Builder.Default
    private boolean actif = true;

    @OneToMany(mappedBy = "membre",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Builder.Default
    private List<Emprunt> emprunts=new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if (dateInscription == null){
            dateInscription = LocalDateTime.now();
        }
        if (role == null){
            role = Role.MEMBRE;
        }
    }

}
