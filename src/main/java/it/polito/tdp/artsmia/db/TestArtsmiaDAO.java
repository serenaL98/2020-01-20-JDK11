package it.polito.tdp.artsmia.db;

import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.Exhibition;

public class TestArtsmiaDAO {

	public static void main(String[] args) {

		ArtsmiaDAO dao = new ArtsmiaDAO();

		System.out.println("Test objects:");
		List<ArtObject> objects = dao.listObjects();
		System.out.println(objects.get(0));
		System.out.println(objects.size());
		
		System.out.println("Test exhibitions:");
		List<Exhibition> exhibitions = dao.listExhibitions();
		System.out.println(exhibitions.get(0));
		System.out.println(exhibitions.size());

		System.out.println("Stampo qualcosa:\n");
		for( Artists a: dao.getArtists("Photographer")) {
			System.out.println(a.toString()+"\n");
		}
		
		//mappaArtisti
		//dao.getCollegamenti("Photographer", mappaArtisti);
	}

}
