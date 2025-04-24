package com.habit2fit.app.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

@Service
public class FirestoreService {
	private final Firestore firestore;

    public FirestoreService(Firestore firestore) {
        this.firestore = firestore;
    }
    public String saveData(String collection, String documentId, Map<String, Object> data) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(collection).document(documentId).set(data);
        return future.get().getUpdateTime().toString();
    }
    
    public Map<String, Object> getData(String collection, String documentId) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection(collection).document(documentId).get().get();
        return snapshot.exists() ? snapshot.getData() : null;
    }
}
