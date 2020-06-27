package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private List<String> ruoli;
	private List<Artists> artisti;
	private Map<Integer, Artists> mappaArt;
	
	//grafo semplice, pesato non orientato
	private Graph<Artists, DefaultWeightedEdge> grafo;
	
	List<Collegamento> lista = new ArrayList<>(); 
	
	public Model() {
		
		this.dao = new ArtsmiaDAO();
		this.ruoli = dao.getRuolo();
		
	}

	public List<String> getRuoli(){
		return this.ruoli;
	}
	
	/*public List<Artists> getArtistsByRole(String ruolo){
		this.artisti = dao.getArtists(ruolo);
		return artisti;
	}*/
	
	public void creaGrafo(String ruolo) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.artisti = dao.getArtists(ruolo);
		this.mappaArt = new HashMap<>();
		
		for(Artists a : artisti) {
			mappaArt.put(a.getCode(), a);
		}
		
		//VERTICI
		Graphs.addAllVertices(this.grafo, this.artisti);
		
		//ARCHI
		lista.addAll(this.dao.getCollegamenti(ruolo, mappaArt));
		
		for(Collegamento c: lista) {
			if(this.grafo.containsVertex(c.getA1()) && this.grafo.containsVertex(c.getA2())) {
			//if(this.grafo.getEdge(c.getA1(), c.getA2()) == null) {
				Graphs.addEdgeWithVertices(this.grafo, c.getA1(), c.getA2(), c.getPeso());
			}
		}
		
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int numeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Collegamento> getArtistiAccoppiati(){
		
		Collections.sort(this.lista);
		
		return lista;
	}
	
	public Artists getArtistById(int id) {
		return dao.getArtistById(id);
	}
	
	public Artists cercaArtista(int id) {
		for(Artists a: this.artisti) {
			if(a.getCode() == id) {
				return a;
			}
		}
		return null;
	}
	//----------VIDEOLEZIONE----------
	List<Artists> best;
	
	public boolean prensenteNelGrafo(Artists a) {
		if(this.grafo.containsVertex(a)) {
			return true;
		}
		return false;
	}
	
	//lista di vertici:
	public List<Artists> trovaPercorso(Artists sorgente){
		
		//inizializzo
		this.best = new ArrayList<>();
		List<Artists> parziale = new ArrayList<>();
		
		//aggiungo il primo artista
		parziale.add(sorgente);
		
		//avvio ricorsione: -1 so che quel peso non c'è
		ricorsione(parziale, -1);
		
		//restituisco la soluzione migliore
		return best;
	}
	
	private void ricorsione(List<Artists> parziale, double peso) {
		
		//il peso deve essere uguale per lo stesso percorso, se cambio percorso ho altro peso
		Artists ultimo = parziale.get(parziale.size()-1);
		
		//prendo i vicini
		List<Artists> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		
		for(Artists a: vicini) {
			//primo caso
			if(!parziale.contains(a) && peso == -1) {
				//aggiungo
				parziale.add(a);
				//ricorsione
				ricorsione(parziale, this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, a)));
				//backtracking
				parziale.remove(a);
			}else {
				//NON SONO NEL PRIMO CASO: aggiungo solo i vicini collegati con quel peso
				if(this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, a)) == peso && !parziale.contains(a)) {
					parziale.add(a);
					ricorsione(parziale, peso);
					parziale.remove(a);
				}
			}
		}
		
		//percorso piu lungo
		if(parziale.size()>best.size()) {
			this.best = new ArrayList<>(parziale);
		}
		
	}
	
}
