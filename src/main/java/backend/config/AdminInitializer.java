package backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import backend.model.Administrador;
import backend.repository.AdministradorRepository;
import backend.service.AdministradorService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = {"app.admin.usuario", "app.admin.password"})
public class AdminInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final AdministradorRepository administradorRepository;
    private final AdministradorService administradorService;

    @Value("${app.admin.usuario}")
    private String usuario;

    @Value("${app.admin.password}")
    private String password;

    @Override
    public void run(String... args) {

        if (administradorRepository.existsByUsuario(usuario)) {
            log.info("El administrador '{}' ya existe, no se crea nada", usuario);
            return;
        }

        Administrador administrador = new Administrador();
        administrador.setUsuario(usuario);
        administrador.setPassword(password);

        administradorService.crearAdministrador(administrador);

        log.info("Administrador inicial '{}' creado correctamente", usuario);
    }
}
