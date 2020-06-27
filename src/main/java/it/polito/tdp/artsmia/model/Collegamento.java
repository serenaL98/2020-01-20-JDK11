package it.polito.tdp.artsmia.model;

public class Collegamento implements Comparable<Collegamento>{
	
	private Artists a1;
	private Artists a2;
	private Integer peso;
	
	
	public Collegamento(Artists a1, Artists a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	
	
	public Artists getA1() {
		return a1;
	}
	public void setA1(Artists a1) {
		this.a1 = a1;
	}
	public Artists getA2() {
		return a2;
	}
	public void setA2(Artists a2) {
		this.a2 = a2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		result = prime * result + peso;
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
		Collegamento other = (Collegamento) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		if (peso != other.peso)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return a1.getNome() + ", " + a2.getNome() + ", " + peso + "\n";
	}


	@Override
	public int compareTo(Collegamento o) {
		return -(this.peso.compareTo(o.peso));
	}

	
}
