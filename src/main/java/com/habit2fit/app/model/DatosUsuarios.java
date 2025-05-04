package com.habit2fit.app.model;

public class DatosUsuarios {
	    private String idUsuario;
	    private Double peso;
	    private Boolean ejercicio;

	    // --- Constructores ---
	    public DatosUsuarios() {}

	    public DatosUsuarios(String idUsuario, Double peso,Boolean ejercicio) {
	        this.idUsuario = idUsuario;
	        this.peso = peso;
	        this.ejercicio = ejercicio;
	    }
	    

		public String getIdUsuario() {
			return idUsuario;
		}

		public void setIdUsuario(String idUsuario) {
			this.idUsuario = idUsuario;
		}

		public Double getPeso() {
			return peso;
		}

		public void setPeso(Double peso) {
			this.peso = peso;
		}

		public Boolean getEjercicio() {
			return ejercicio;
		}

		public void setEjercicio(Boolean ejercicio) {
			this.ejercicio = ejercicio;
		}
		
	    
}
