package Labo.Academie.controller;

import Labo.Academie.dto.LivreRequest;
import Labo.Academie.dto.LivreResponse;
import Labo.Academie.service.LivreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
@RequiredArgsConstructor
@Tag(name = "Livres", description = "Gestion du catalogue de livres")
public class LivreController {
    private final LivreService livreService;

    @GetMapping
    @Operation(summary = "Obtenir tous les livres")
    public ResponseEntity<List<LivreResponse>> getAllLivres(){
        return ResponseEntity.ok(livreService.getAllLivres());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un livre par ID")
    public ResponseEntity<LivreResponse>getLivreById(@PathVariable Long id){
        return ResponseEntity.ok((LivreResponse) livreService.getLivreById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des livres")
    public ResponseEntity<List<LivreResponse>>searchLivre(@RequestParam String query){
        return ResponseEntity.ok(livreService.searchLivre(query));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Obtenir les livres disponibles")
    public ResponseEntity<List<LivreResponse>>getLivresDisponibles(){
        return ResponseEntity.ok(livreService.getLivreDisponible());
    }

    @GetMapping("/categorie/{categorieId}")
    @Operation(summary = "Obtenir les livres par categorie")
    public ResponseEntity<List<LivreResponse>>getLivreByCategorie(@PathVariable Long categorieId){
        return ResponseEntity.ok(livreService.getLivresByCategorie(categorieId));
    }

    @PostMapping
    @PreAuthorize(("hasAnyRole('BIBLIOTHECAIRE','ADMIN')"))
    @Operation(summary = "Ajouter un nouveau livre", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<LivreResponse>createLivre(@Valid @RequestBody LivreRequest request){
        return ResponseEntity.status(201).body(livreService.createLivre(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('BIBLIOTHECAIRE','ADMIN')")
    @Operation(summary = "Modifier un livre",security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<LivreResponse>updateLivre(@PathVariable Long id, @Valid @RequestBody LivreRequest request){
        return ResponseEntity.ok((LivreResponse) livreService.updateLivre(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('BIBLIOTHECAIRE','ADMIN')")
    @Operation(summary = "Supprimer un livre",security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<Void>deleteLivre(@PathVariable Long id){
        livreService.deleteLivre(id);
        return ResponseEntity.noContent().build();
    }
}
