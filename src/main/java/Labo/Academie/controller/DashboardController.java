package Labo.Academie.controller;

import Labo.Academie.dto.DashboardResponse;
import Labo.Academie.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Statistique et tableau de bord")
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasAnayRole('BIBLIOTHECAIRE','ADMINA')")
    @Operation(summary = "Obtenir les statistiques du tableau de bord")
    public ResponseEntity<DashboardResponse> getDashboard(){
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }
}
