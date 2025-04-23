package com.habit2fit.app.controller;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.habit2fit.app.service.FirestoreService;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {
	 private final FirestoreService firestoreService;

	    public FirebaseController(FirestoreService firestoreService) {
	        this.firestoreService = firestoreService;
	    }

	    @PostMapping("/{collection}/{id}")
	    public String save(@PathVariable String collection, @PathVariable String id, @RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
	        return firestoreService.saveData(collection, id, data);
	    }

	    @GetMapping("/{collection}/{id}")
	    public Map<String, Object> get(@PathVariable String collection, @PathVariable String id) throws ExecutionException, InterruptedException {
	        return firestoreService.getData(collection, id);
	    }
}
