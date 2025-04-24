package com.habit2fit.app.controller;

import java.util.concurrent.ExecutionException;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.habit2fit.app.dto.UsuariosDTO;
import com.habit2fit.app.service.UsuariosService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	@Autowired
    private UsuariosService usuarioService;

    public UsuarioController(UsuariosService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping // Sigue siendo POST, pero ahora hace "upsert"
    public ResponseEntity<String> crearOActualizarUsuario(@RequestBody UsuariosDTO usuario) { // Nombre del método actualizado opcionalmente
        try {
           
            String userId = usuarioService.guardarOActualizarUsuario(usuario);

            // Consideración del código de estado:
            // - 201 Created es ideal si SIEMPRE se crea.
            // - 200 OK es común para actualizaciones exitosas o upserts exitosos.
            // Dado que no sabemos fácilmente si fue creación o actualización sin una lectura previa,
            // devolver 200 OK es una opción razonable y común para operaciones tipo "upsert".
            // Devolveremos el ID como confirmación.
            return ResponseEntity.ok(userId); // Devolvemos 200 OK y el ID

        } catch (IllegalArgumentException e) {
            // Error si el idUsuario no se proporcionó en el JSON
             throw new ResponseStatusException(HttpStatus.SC_BAD_REQUEST, e.getMessage(), e); // Usa HttpStatus de Spring
        } catch (ExecutionException e) {
            // Ya no necesitamos verificar AlreadyExistsException específicamente para .set()
            // Capturamos otros errores de ejecución generales de Firestore.
            throw new ResponseStatusException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error al guardar o actualizar usuario", e); // Usa HttpStatus de Spring
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablece el flag de interrupción
            throw new ResponseStatusException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Operación interrumpida al guardar o actualizar usuario", e); // Usa HttpStatus de Spring
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosDTO> getUsuarioPorId(@PathVariable String id) {
        try {
            UsuariosDTO usuario = usuarioService.getUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario); // Devuelve el usuario y status 200 OK
            } else {
                // Devuelve status 404 Not Found si no se encuentra
                return ResponseEntity.notFound().build();
            }
        } catch (ExecutionException | InterruptedException e) {
             Thread.currentThread().interrupt();
             // Devuelve un error interno del servidor
             throw new ResponseStatusException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error al obtener usuario", e);
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String id) {
        try {
            String deleteTime = usuarioService.eliminarUsuario(id);
             return ResponseEntity.ok("Eliminado: " + deleteTime);
        } catch (ExecutionException | InterruptedException e) {
             Thread.currentThread().interrupt();
             // Podrías verificar si el error fue porque no existía y devolver 404
             throw new ResponseStatusException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error al eliminar usuario", e);
        }
    }
    
}
