package com.habit2fit.app.model;

public class DatosUsuarios {
	    private String idUsuario;
	    private Double peso;
	    private Integer edad;
	    private Double altura;
	    private Long idTipoCuerpo;

	    // --- Constructores ---
	    public DatosUsuarios() {}

	    public DatosUsuarios(String idUsuario, Double peso, Integer edad,Long idTipoCuerpo, Double altura) {
	        this.idUsuario = idUsuario;
	        this.peso = peso;
	        this.edad = edad;
	        this.idTipoCuerpo = idTipoCuerpo;
	        this.altura = altura;
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

		public Integer getEdad() {
			return edad;
		}

		public void setEdad(Integer edad) {
			this.edad = edad;
		}

		public Long getIdTipoCuerpo() {
			return idTipoCuerpo;
		}

		public void setIdTipoCuerpo(Long idTipoCuerpo) {
			this.idTipoCuerpo = idTipoCuerpo;
		}

		public Double getAltura() {
			return altura;
		}

		public void setAltura(Double altura) {
			this.altura = altura;
		}
		
	    
}
