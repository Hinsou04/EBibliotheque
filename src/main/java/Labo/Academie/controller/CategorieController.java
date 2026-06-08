package Labo.Academie.controller;

import Labo.Academie.dto.CategorieRequest;
import Labo.Academie.dto.CategorieResponse;
import Labo.Academie.service.CategorieService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Gestion des categories de livres")
public class CategorieController {
    private final CategorieService categorieService;

    @GetMapping
    @Operation(summary = "Obtenir toutes les categories")
    public ResponseEntity<List<CategorieResponse>> getAllCategorie(){
        return ResponseEntity.ok(categorieService.getAllCategorie());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une categorie par ID")
    public ResponseEntity<CategorieResponse>getCategorieById(@PathVariable Long id){
        return ResponseEntity.ok(categorieService.getCategorieById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMINA')")
    @Operation(summary = "Créer une categorie", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<CategorieResponse>createCategorie(@Valid @RequestBody CategorieRequest resquest) {
        return ResponseEntity.status(201).body(categorieService.createCategorie(resquest));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMINA')")
    @Operation(summary = "Modifier une categorie", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<CategorieResponse> updateCategorie(@PathVariable Long id, @Valid @RequestBody CategorieRequest resquest){
        return ResponseEntity.ok(categorieService.updateCategorie(id,resquest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMINA')")
    @Operation(summary = "Supprimer une categorie", security = @SecurityRequirement(name = "Bearer"))
    public ResponseEntity<Void>deleteCategorie(@PathVariable Long id){
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
