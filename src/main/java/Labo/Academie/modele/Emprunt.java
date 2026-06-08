package Labo.Academie.modele;

import Labo.Academie.enume.StatutEmprunt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "emprunts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id",nullable = false)
    private Livre livre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @Column(name = "date_emprunt",nullable = false)
    private LocalDateTime dateEmprunt;

    @Column(name = "date_retour_prevue",nullable = false)
    private LocalDateTime dateRetourPrevue;

    @Column(name = "date_retour_effectif")
    private LocalDateTime dateRetourEffectif;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutEmprunt statutEmprunt= StatutEmprunt.EN_COURS;

    @Column(name = "penalite",precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal penalite = BigDecimal.ZERO;

    @Column(name = "nombre_prolongations")
    @Builder.Default
    private Integer nombreProlongations = 0;

    // Duree standard d'emprunte: 14jours
    public static  final int DUREE_EMPRUNT_JOURS = 14;

    // Duree de prolongation: 7jours
    public static final int DUREE_PROLONGATION_JOURS = 7;

    // Nombre max de prolongations
    public static final int MAX_PROLONGATIONS = 2;

    // Penalite par jour de retard : 250f
    public static final BigDecimal PENALITE_PAR_JOUR = new BigDecimal(250);

    @PrePersist
    protected void onCreate(){
        this.dateEmprunt = LocalDateTime.now();
        if(this.dateRetourPrevue == null){
            this.dateRetourPrevue = LocalDateTime.now().plusDays(DUREE_EMPRUNT_JOURS);
        }
    }

    public Boolean isEnRetard(){
        return statutEmprunt == StatutEmprunt.EN_COURS && LocalDateTime.now().isAfter(dateRetourPrevue);
    }

    public BigDecimal calculerPenalite(){
        if (!isEnRetard() && statutEmprunt != StatutEmprunt.RETOURNE) return BigDecimal.ZERO;

        LocalDateTime dateFin = (dateRetourEffectif != null)?dateRetourEffectif:LocalDateTime.now();
        if (dateFin.isAfter(dateRetourPrevue)){
            long joursRetard = ChronoUnit.DAYS.between(dateRetourPrevue,dateFin);
            return PENALITE_PAR_JOUR.multiply(BigDecimal.valueOf(joursRetard));
        }
        return BigDecimal.ZERO;
    }
}
