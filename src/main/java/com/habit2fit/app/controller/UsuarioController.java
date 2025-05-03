package com.habit2fit.app.controller;

import java.util.concurrent.ExecutionException;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import com.habit2fit.app.model.Usuarios;
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
    public ResponseEntity<String> crearOActualizarUsuario(@RequestBody Usuarios usuario) { // Nombre del método actualizado opcionalmente
        try {
           
            String userId = usuarioService.guardarOActualizarUsuario(usuario);

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
    
    @GetMapping("id/{id}")
    public ResponseEntity<Usuarios> getUsuarioPorId(@PathVariable String id) {
        try {
            Usuarios usuario = usuarioService.getUsuarioPorId(id);
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
    
    
    @GetMapping("correo/{correo}")
    public ResponseEntity<List<Usuarios>> getUsuariosPorCorreo(@PathVariable String correo) {
        try {
            List<Usuarios> usuarios = usuarioService.getUsuariosPorCorreo(correo);
            if (!usuarios.isEmpty()) {
                return ResponseEntity.ok(usuarios);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(
                HttpStatus.SC_INTERNAL_SERVER_ERROR,
                "Error al obtener usuarios por correo",
                e
            );
        }
    }
    
    @GetMapping("nombre/{nombre}")
    public ResponseEntity<List<Usuarios>> getUsuariosPorNombre(@PathVariable String nombre) {
        try {
            List<Usuarios> usuarios = usuarioService.getUsuariosPorNombre(nombre);
            if (!usuarios.isEmpty()) {
                return ResponseEntity.ok(usuarios);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(
                HttpStatus.SC_INTERNAL_SERVER_ERROR,
                "Error al obtener usuarios por nombre",
                e
            );
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
