package Labo.Academie.controller;

import Labo.Academie.dto.MembreRequest;
import Labo.Academie.dto.MembreResponse;
import Labo.Academie.service.MembreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/membres")
@Tag(name = "Membres",description = "Gestion des membres")
@SecurityRequirement(name = "Bearer Autentication")
public class MembreController {
    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    // Lister tous les membres inscrits
    @GetMapping
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMIN')")
    @Operation(summary = "Obtenire tous les membres")
    public ResponseEntity<List<MembreResponse>> getAllMembres(){
        return ResponseEntity.ok(membreService.getAllMembres());
    }

    //Lister un membre inscrit par son identifiant
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('BIBLIOTHECAIRE','ADMIN') or #id==authentication.principal.id")
    @Operation(summary = "Obtenir un membre par ID")
    public ResponseEntity<MembreResponse>getMembreById(@PathVariable Long id){
        return ResponseEntity.ok(membreService.getMembreById(id));
    }

    // Modifier un membre inscrit par son identifiant
    @PutMapping("/{id}")
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMIN')")
    @Operation(summary = "Modifier un membre")
    public ResponseEntity<MembreResponse>updateMembre(@PathVariable Long id, @RequestBody MembreRequest resquest){
        return ResponseEntity.ok(membreService.updateMembre(id, resquest));
    }

    // Supprimer un membre inscrit par son identifiant
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMIN')")
    @Operation(summary = "Supprimer un membre")
    public ResponseEntity<Void>deleteMembreById(@PathVariable Long id){
        membreService.deleteMembreById(id);
        return ResponseEntity.noContent().build();
    }

    // Modifier partiellement l'activation/desactivation d'un membre par son identifiant
    @PatchMapping("/{id}/toggle-activation")
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMIN')")
    @Operation(summary = "Activer/Desactiver un compte d'un membre")
    public ResponseEntity<MembreResponse>toggleActivation(@PathVariable Long id){
        return ResponseEntity.ok(membreService.toggleActivation(id));
    }

    // Changer le role d'un membre
    @PutMapping("/{id}/role")
    public ResponseEntity<MembreResponse>changerRole(@PathVariable Long id, @RequestBody Map<String, String>body){
        return ResponseEntity.ok(membreService.changerRole(id,body.get("role")));
    }
}
