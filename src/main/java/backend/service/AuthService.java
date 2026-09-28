package backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.dto.LoginRequest;
import backend.model.Administrador;
import backend.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AdministradorRepository administradorRepository;

    public boolean login(LoginRequest request) {

        Optional<Administrador> administrador =
        administradorRepository.findByUsuario(request.usuario());
    
        if (administrador.isEmpty()) return false;         
        if (!administrador.get().isEstado()) return false;

        return passwordEncoder.matches(
            request.password(),
            administrador.get().getPassword());
    }
}
