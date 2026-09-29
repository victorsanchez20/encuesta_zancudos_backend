package backend.dto;

import backend.model.Administrador;

/**
 * Respuesta de la API. Nunca incluye el password: la entidad solo se usa
 * como entrada/salida interna y el hash BCrypt no debe viajar al cliente.
 */
public record AdministradorResponse(
    Long id,
    String usuario,
    boolean estado
) {

    public static AdministradorResponse from(Administrador administrador) {
        return new AdministradorResponse(
            administrador.getId(),
            administrador.getUsuario(),
            administrador.isEstado()
        );
    }
}
