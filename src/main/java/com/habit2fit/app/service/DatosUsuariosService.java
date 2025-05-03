package com.habit2fit.app.service;

import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.habit2fit.app.model.DatosUsuarios;

@Service
public class DatosUsuariosService {
	private static final Logger logger = LogManager.getLogger(UsuariosService.class);
	private static final String COLLECTION_NAME = "datosusuario";
	@Autowired
    private Firestore firestore; 
	
	private CollectionReference getUsuariosCollection() {
        return firestore.collection(COLLECTION_NAME);
    }
	
	public String guardarOActualizarUsuario(DatosUsuarios datosUsuarios) throws ExecutionException, InterruptedException {
	    // Validar que el ID de usuario no sea nulo ni vacío
	    if (datosUsuarios.getIdUsuario() == null || datosUsuarios.getIdUsuario().trim().isEmpty()) {
	        throw new IllegalArgumentException("El campo 'idUsuario' no puede ser nulo ni vacío.");
	    }

	    // Obtener la referencia a la colección de usuarios
	    CollectionReference usuariosCollection = getUsuariosCollection();

	    // Intentar obtener un documento con el idUsuario proporcionado
	    Query query = usuariosCollection.whereEqualTo("idUsuario", datosUsuarios.getIdUsuario());
	    ApiFuture<QuerySnapshot> future = query.get();
	    QuerySnapshot querySnapshot = future.get();

	    ApiFuture<WriteResult> writeResult = null;
	    DocumentReference docRef = null;

	    if (!querySnapshot.isEmpty()) {
	        // El documento ya existe, lo actualizamos
	        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);  // Obtener el primer documento encontrado
	        docRef = documentSnapshot.getReference();  // Obtener la referencia del documento

	        // Actualizar el documento
	        writeResult = docRef.set(datosUsuarios);
	    } else {
	        // El documento no existe, lo creamos con un ID generado automáticamente
	        docRef = usuariosCollection.add(datosUsuarios).get();  // Esto genera un nuevo documento con un ID único

	        // El ID generado por Firebase se obtiene de la referencia
	        String generatedId = docRef.getId();
	        
	        // Loguear y devolver el ID generado
	        logger.info("Nuevo usuario guardado con ID generado automáticamente: " + generatedId);
	    }

	    // Esperar a que la operación termine
	    writeResult.get();

	    // Obtener el ID del documento utilizado
	    String documentId = docRef.getId();

	    // Loguear el ID del documento
	    logger.info("Usuario guardado/actualizado con ID: " + documentId);

	    // Devolvemos el ID del documento
	    return documentId;
	}
}
