package com.habit2fit.app.model;

import java.util.Date;

import com.google.cloud.firestore.annotation.Exclude;

public class Usuarios {
    private String correo;
    private String idUsuario;
    private String nombre;
    private Date fechaRegistro;
    private Date fechaUpdate;

    // --- Constructores ---
    public Usuarios() {}

    public Usuarios(String correo, String idUsuario, String nombre, Date fechaRegistro, Date fechaUpdate) {
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.fechaUpdate = fechaUpdate;
    }
    
    

    public Date getFechaUpdate() {
		return fechaUpdate;
	}

	public void setFechaUpdate(Date fechaUpdate) {
		this.fechaUpdate = fechaUpdate;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
