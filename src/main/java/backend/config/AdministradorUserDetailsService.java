package backend.config;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.model.Administrador;
import backend.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministradorUserDetailsService implements UserDetailsService {

    private static final String ROL = "ADMIN";

    private final AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

        Administrador administrador = administradorRepository
            .findByUsuario(usuario)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User
            .withUsername(administrador.getUsuario())
            .password(administrador.getPassword())
            .authorities(List.of(new SimpleGrantedAuthority(ROL)))
            .disabled(!administrador.isEstado())
            .build();
    }
}
