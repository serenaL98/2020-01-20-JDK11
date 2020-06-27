package it.polito.tdp.artsmia.model;

public class Artists {

	private int code;
	private String nome;
	private String ruolo;
	
	
	public Artists(int code, String nome, String ruolo) {
		super();
		this.code = code;
		this.nome = nome;
		this.ruolo = ruolo;
	}
	

	public Artists(int int1, String string) {
		this.code = int1;
		this.nome = string;
	}


	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((ruolo == null) ? 0 : ruolo.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artists other = (Artists) obj;
		if (code != other.code)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (ruolo == null) {
			if (other.ruolo != null)
				return false;
		} else if (!ruolo.equals(other.ruolo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return this.getNome() + " " +this.getRuolo();
	}
	
}
