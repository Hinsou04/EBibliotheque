package Labo.Academie.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpruntRequest {
    @NotNull(message = "L'ID du livre est requis")
    private Long livreId;

    @NotNull(message = "L'ID du membre est requis")
    private Long membreId;
}
