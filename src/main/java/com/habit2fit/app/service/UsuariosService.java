package com.habit2fit.app.service;

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
import com.google.cloud.firestore.WriteResult;
import com.habit2fit.app.dto.UsuariosDTO;
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
	
	
	public String guardarOActualizarUsuario(UsuariosDTO usuario) throws ExecutionException, InterruptedException {
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
	
	
	
	public UsuariosDTO getUsuarioPorId(String documentId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getUsuariosCollection().document(documentId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get(); // Espera a que la operación termine

        if (document.exists()) {
            UsuariosDTO usuario = document.toObject(UsuariosDTO.class); // Mapea el documento al DTO
            usuario.setIdUsuario(documentId);
            logger.info("Usuario encontrado: " + usuario);
            return usuario;
        } else {
            logger.error("No se encontró ningún usuario con ID: " + documentId);
            return null;
        }
    }
	
	 public String eliminarUsuario(String documentId) throws ExecutionException, InterruptedException {
	        DocumentReference docRef = getUsuariosCollection().document(documentId);
	        ApiFuture<WriteResult> future = docRef.delete();
	        WriteResult result = future.get();
	        logger.info("Usuario eliminado en: " + result.getUpdateTime());
	        return "Usuario eliminado en: " + result.getUpdateTime();
	   }
}
