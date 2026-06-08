package Labo.Academie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembreResponse {
    private Long id;
    private String  username;
    private String prenom;
    private String nom;
    private String email;
    private String role;
    private LocalDateTime dateInscription;
    private Boolean actif;
    private  long nombreEmprunts;

}
