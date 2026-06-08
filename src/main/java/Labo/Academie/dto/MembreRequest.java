package Labo.Academie.dto;

import Labo.Academie.enume.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MembreRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    private String prenom;

    @NotBlank
    private String nom;

    @NotBlank @Email
    private String email;

    private  String password;
    private Role role;
    private Boolean actif;
}
