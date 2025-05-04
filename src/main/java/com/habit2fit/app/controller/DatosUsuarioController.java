package com.habit2fit.app.controller;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.habit2fit.app.model.DatosUsuarios;
import com.habit2fit.app.service.DatosUsuariosService;


@RestController
@RequestMapping("/api/datosUsuarios")
public class DatosUsuarioController {
	@Autowired
    private DatosUsuariosService datosUsuariosService;
	 public DatosUsuarioController(DatosUsuariosService datosUsuariosService) {
	        this.datosUsuariosService = datosUsuariosService;
	    }
	 
	 @PostMapping //"Upsert"
	    public ResponseEntity<String> crearOActualizarUsuario(@RequestBody DatosUsuarios datosUsuarios) { // Nombre del método actualizado opcionalmente
	        try {
	           
	            String datosUsuarioId = datosUsuariosService.guardarOActualizarUsuario(datosUsuarios);

	            return ResponseEntity.ok(datosUsuarioId); // Devolvemos 200 OK y el ID

	        } catch (IllegalArgumentException e) {
	            // Error si el idUsuario no se proporcionó en el JSON
	             throw new ResponseStatusException(HttpStatus.SC_BAD_REQUEST, e.getMessage(), e); // Usa HttpStatus de Spring
	        } catch (ExecutionException e) {
	            // Capturamos otros errores de ejecución generales de Firestore.
	            throw new ResponseStatusException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error al guardar o actualizar usuario", e); // Usa HttpStatus de Spring
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt(); // Restablece el flag de interrupción
	            throw new ResponseStatusException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Operación interrumpida al guardar o actualizar usuario", e); // Usa HttpStatus de Spring
	        }
	    }
	 
	 	
	 
}
