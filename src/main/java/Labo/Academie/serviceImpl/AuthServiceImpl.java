package Labo.Academie.serviceImpl;

import Labo.Academie.dto.AuthResponse;
import Labo.Academie.dto.LoginRequest;
import Labo.Academie.dto.RegisterRequest;
import Labo.Academie.enume.Role;
import Labo.Academie.modele.Membre;
import Labo.Academie.repository.MembreRepository;
import Labo.Academie.service.AuthService;
import Labo.Academie.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MembreRepository membreRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        Membre membre = membreRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(request.getPassword(), membre.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        if (!membre.isActif()) {
            throw new RuntimeException("Compte desactive");
        }
        String token = jwtService.generateToken(membre);
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(membre.getId())
                .username(membre.getUsername())
                .email(membre.getEmail())
                .role(membre.getRole().name())
                .nom(membre.getNom())
                .prenom(membre.getPrenom())
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request){
        if (membreRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username deja utilise");
        }
        if (membreRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email deja utilise");
        }
        Membre membre = new Membre();
        membre.setUsername(request.getUsername());
        membre.setNom(request.getNom());
        membre.setPrenom(request.getPrenom());
        membre.setEmail(request.getEmail());
        membre.setPassword(passwordEncoder.encode(request.getPassword()));
        membre.setRole(Role.MEMBRE);
        membre.setActif(true);

        membreRepository.save(membre);
        String token = jwtService.generateToken(membre);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(membre.getId())
                .username(membre.getUsername())
                .email(membre.getEmail())
                .role(membre.getRole().name())
                .nom(membre.getNom())
                .prenom(membre.getPrenom())
                .build();
    }
}
