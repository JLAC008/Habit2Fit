package com.habit2fit.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.habit2fit.app.model.Usuarios;

import org.springframework.util.StringUtils;


@Service
public class UsuariosService {
	
	private static final Logger logger = LogManager.getLogger(UsuariosService.class);
	private static final String COLLECTION_NAME = "usuarios";
	@Autowired
    private Firestore firestore; 
	
	private CollectionReference getUsuariosCollection() {
        return firestore.collection(COLLECTION_NAME);
    }
	
	
	public String guardarOActualizarUsuario(Usuarios usuario) throws ExecutionException, InterruptedException {
        // Validar que el ID personalizado (idUsuario) se haya proporcionado
        if (!StringUtils.hasText(usuario.getIdUsuario())) {
             throw new IllegalArgumentException("El campo 'idUsuario' no puede ser nulo ni vacío para usarlo como ID de documento.");
        }

        String customDocumentId = usuario.getIdUsuario();
        DocumentReference docRef = getUsuariosCollection().document(customDocumentId);

        // Usar set() para crear o sobrescribir.
        ApiFuture<WriteResult> future = docRef.set(usuario);

        // Esperar a que la operación termine
        future.get();

        logger.info("Usuario guardado/actualizado con ID: " + customDocumentId);
        // Devolvemos el mismo ID que usamos
        return customDocumentId;
    }
	
	
	
	public Usuarios getUsuarioPorId(String documentId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getUsuariosCollection().document(documentId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get(); // Espera a que la operación termine

        if (document.exists()) {
            Usuarios usuario = document.toObject(Usuarios.class); // Mapea el documento al DTO
            usuario.setIdUsuario(documentId);
            logger.info("Usuario encontrado: " + usuario+ " por id");
            return usuario;
        } else {
            logger.error("No se encontró ningún usuario con ID: " + documentId);
            return null;
        }
    }
	
	
	public List<Usuarios> getUsuariosPorCorreo(String correo) throws ExecutionException, InterruptedException {
	    ApiFuture<QuerySnapshot> future = getUsuariosCollection()
	        .whereEqualTo("correo", correo)
	        .get();

	    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	    List<Usuarios> usuarios = new ArrayList<>();

	    for (DocumentSnapshot document : documents) {
	        Usuarios usuario = document.toObject(Usuarios.class);
	        usuario.setIdUsuario(document.getId()); // Opcional: setea el ID del documento
	        usuarios.add(usuario);
	    }

	    logger.info("Usuarios encontrados con correo " + correo + ": " + usuarios.size());
	    return usuarios;
	}
	
	public List<Usuarios> getUsuariosPorNombre(String nombre) throws ExecutionException, InterruptedException {
	    ApiFuture<QuerySnapshot> future = getUsuariosCollection()
	        .whereEqualTo("nombre", nombre)
	        .get();

	    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	    List<Usuarios> usuarios = new ArrayList<>();

	    for (DocumentSnapshot document : documents) {
	        Usuarios usuario = document.toObject(Usuarios.class);
	        usuario.setIdUsuario(document.getId()); // Opcional: setea el ID del documento
	        usuarios.add(usuario);
	    }

	    logger.info("Usuarios encontrados con nombre " + nombre + ": " + usuarios.size());
	    return usuarios;
	}
	
	 public String eliminarUsuario(String documentId) throws ExecutionException, InterruptedException {
	        DocumentReference docRef = getUsuariosCollection().document(documentId);
	        ApiFuture<WriteResult> future = docRef.delete();
	        WriteResult result = future.get();
	        logger.info("Usuario eliminado en: " + result.getUpdateTime());
	        return "Usuario eliminado en: " + result.getUpdateTime();
	   }
}
