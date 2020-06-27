package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.Collegamento;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getRuolo(){
		
		String sql = "SELECT DISTINCT a.role ruolo" + 
				" FROM authorship a" + 
				" ORDER BY a.role ASC";
		List<String> ruoli = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				String rtemp = res.getString("ruolo");
				ruoli.add(rtemp);
			}
			
			conn.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore nel prendere i ruoli dal db!", e);
		}
		
		return ruoli;
	}
	
	public List<Artists> getArtists( String ruolo){
		String sql = "SELECT DISTINCT s.name nome, s.artist_id cod" + 
				" FROM authorship a, artists s" + 
				" WHERE a.role = ? AND a.artist_id = s.artist_id";
		List<Artists> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				int codice = res.getInt("cod");
				String nome = res.getString("nome");
				
				lista.add(new Artists(codice, nome, ruolo));
			}
			
			con.close();
			
		}catch(SQLException e) {
			throw new RuntimeException("Erroe nell'estrazione degli artisti di quel ruolo dal db!", e);
		}
		
		return lista;
	}
	
	public List<Collegamento> getCollegamenti (String ruolo, Map<Integer, Artists> mappaArtisti){
		
		String sql = "SELECT a1.artist_id AS ar1, a2.artist_id AS ar2, COUNT(DISTINCT o1.exhibition_id) AS peso " + 
				" FROM exhibition_objects o1, exhibition_objects o2, authorship a1, authorship a2" + 
				" WHERE o1.exhibition_id = o2.exhibition_id" + 
				"		AND o1.object_id<> o2.object_id" + 
				"		AND o1.object_id = a1.object_id" + 
				"		AND o2.object_id = a2.object_id" + 
				"		AND a1.role = a2.role " + 
				"		AND a1.role = ? " + 
				"		AND a1.artist_id> a2.artist_id " + 
				" GROUP BY a1.artist_id, a2.artist_id";
		List<Collegamento> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				if( mappaArtisti.containsKey(res.getInt("ar1")) && mappaArtisti.containsKey(res.getInt("ar2"))) {
					
					Artists a1 = mappaArtisti.get(res.getInt("ar1"));
					Artists a2 = mappaArtisti.get(res.getInt("ar2"));
					int p = res.getInt("peso");
					
					Collegamento coll = new Collegamento(a1, a2, p);
					
					lista.add(coll);
					
				}
				
			}
			
			con.close();
			
			return lista;
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore nella selezione del peso fra artisti dal db!", e);
		}
		
	}
	
	public Artists getArtistById(int id){
		String sql = "SELECT  a.artist_id cod, a.name nome" + 
				" FROM artists a" + 
				" WHERE artist_id = ? ";
		Artists a = null;
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				a = new Artists(res.getInt("cod"), res.getString("nome"));
			}
			
			con.close();
			
			return a;
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore nel trovare l'artista mediante l'id passato!", e);
		}
		
	}
	
}
