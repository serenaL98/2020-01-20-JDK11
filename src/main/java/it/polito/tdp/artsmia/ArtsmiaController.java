package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.Collegamento;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi");
    	
    	String elenco = "";
    	for(Collegamento c: this.model.getArtistiAccoppiati()) {
    		elenco += c.toString();
    	}
    	this.txtResult.appendText("\n\n"+elenco);
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    	
    	String id = this.txtArtista.getText();
    	int ident;
    	
    	try {
    		
    		ident = Integer.parseInt(id);
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("\n\nInserire un valore numerico!");
    		return;
    	}
    	
    	//Artists a = this.model.getArtistById(ident);
    	Artists a = this.model.cercaArtista(ident);
    	txtResult.appendText("\n\nL'artista e': "+a.toString());
    	
		if(a == null) {
			txtResult.appendText("Il codice inserito non corrisponde a nessun artista.\n");
			return;
		}
		
		if(this.model.prensenteNelGrafo(a) == false) {
			txtResult.appendText("\nL'artista non è presente nel grafo.\n");
			return;
		}
		
		List<Artists> percorso = this.model.trovaPercorso(a);
		
		String stampa = "";
		for(Artists s: percorso) {
			stampa += s.getNome()+"\n";
		}
		
		txtResult.appendText("\n\nIl codice identificativo e' corretto.\nIl percorso più lungo ("+percorso.size()+"):\n"+stampa);
		
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	txtResult.appendText("Crea grafo...\n");
    	
    	String ruolo = this.boxRuolo.getValue();
    	if (ruolo == null) {
    		txtResult.setText("\n\nSelezionare un ruolo!");
    		return;
    	}
    	
    	this.model.creaGrafo(ruolo);
    	txtResult.appendText("Grafo con "+this.model.numeroVertici()+" vertici e "+this.model.numeroArchi()+" archi.\n");
    	
    	this.btnCalcolaPercorso.setDisable(false);
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().addAll(this.model.getRuoli());
    	this.btnCalcolaPercorso.setDisable(true);
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
