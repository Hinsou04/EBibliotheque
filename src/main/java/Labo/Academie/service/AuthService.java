package Labo.Academie.service;

import Labo.Academie.dto.AuthResponse;
import Labo.Academie.dto.LoginRequest;
import Labo.Academie.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    @Autowired
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
