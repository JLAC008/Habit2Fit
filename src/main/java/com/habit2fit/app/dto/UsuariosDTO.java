package com.habit2fit.app.dto;

import com.google.cloud.firestore.annotation.Exclude;

public class UsuariosDTO {
    private String correo;
    private String idUsuario; // Este parece ser un ID propio de tu lógica
    private String nombre;

    // --- Constructores ---
    public UsuariosDTO() {}

    public UsuariosDTO(String correo, String idUsuario, String nombre) {
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   

	
	
	
}
