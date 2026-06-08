package Labo.Academie.controller;

import Labo.Academie.dto.EmpruntRequest;
import Labo.Academie.dto.EmpruntResponse;
import Labo.Academie.service.EmpruntService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprunts")
@RequiredArgsConstructor
@Tag(name = "Emprunts", description = "Gestion des emprunts de livres")
@SecurityRequirement(name = "Bearer Autehntication")
public class EmpruntController {
    private final EmpruntService empruntService;

    @GetMapping
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMINA')")
    @Operation(summary = "Obtenir tous les emprunts")
    public ResponseEntity<List<EmpruntResponse>> getAllEmprunts(){
        return ResponseEntity.ok(empruntService.getAllEmprunt());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un emprunt par ID")
    public ResponseEntity<EmpruntResponse>getEmpruntById(@PathVariable Long id){
        return ResponseEntity.ok(empruntService.getEmpruntById(id));
    }

    @GetMapping("/membre/{membreId}")
    @Operation(summary = "Obtenir les emprunts d'un membre")
    public ResponseEntity<EmpruntResponse>getEmpruntsByMembre(@PathVariable Long membreId){
        return ResponseEntity.ok((EmpruntResponse) empruntService.getEmpruntByMembre(membreId));
    }

    @GetMapping("/en_retard")
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMINA')")
    @Operation(summary = "Obtenir les en retard")
    public ResponseEntity<List<EmpruntResponse>>getEmpruntsEnRetard(){
        return ResponseEntity.ok(empruntService.getEmpruntsEnRetard());
    }

    @PostMapping
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMINA')")
    @Operation(summary = "Créer un nouvel emprunt")
    public ResponseEntity<EmpruntResponse>creatEmprunt(@Valid @RequestBody EmpruntRequest request){
        return ResponseEntity.status(201).body(empruntService.createEmprunt(request));
    }

    @PatchMapping("/{id}/retour")
    @Operation(summary = "Enregistrer le retour d'un livre")
    public ResponseEntity<EmpruntResponse>retournerLivre(@PathVariable Long id, @RequestBody Map<String, String>body){
        return ResponseEntity.ok(empruntService.retournerLivre(id,body));
    }

    @PatchMapping("/{id}/prolonger")
    @Operation(summary = "Prolonger un emprunt")
    public ResponseEntity<EmpruntResponse>prolongerEmrpunt(@PathVariable Long id){
        return ResponseEntity.ok(empruntService.prolongerEmprunt(id));
    }
}
