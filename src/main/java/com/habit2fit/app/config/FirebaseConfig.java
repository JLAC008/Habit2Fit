package com.habit2fit.app.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {
	 @Bean
	    Firestore firestore() throws IOException {
	        InputStream serviceAccount = getClass().getClassLoader()
	            .getResourceAsStream("firebase-service-account.json");

	        FirebaseOptions options = FirebaseOptions.builder()
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();

	        if (FirebaseApp.getApps().isEmpty()) {
	            FirebaseApp.initializeApp(options);
	        }

	        return FirestoreClient.getFirestore();
	    }
}
