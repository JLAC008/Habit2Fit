package com.habit2fit.app.model;

import java.util.Date;

import com.google.cloud.firestore.annotation.Exclude;

public class Usuarios {
    private String correo;
    private String idUsuario;
    private String nombre;
    private Date fechaRegistro;
    private Date fechaUpdate;
    private String genero;
    private Double altura;
    private Integer edad;
    private Date fechaNacimiento;

    // --- Constructores ---
    public Usuarios() {}

    public Usuarios(String correo, String idUsuario, String nombre,
    		Date fechaRegistro, Date fechaUpdate, String genero, Double altura,
    		Integer edad, Date fechaNacimiento) {
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.fechaUpdate = fechaUpdate;
        this.genero = genero;
        this.altura = altura;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    

    public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
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
