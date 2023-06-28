/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.SimulatorResult;
import it.polito.tdp.yelp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();

     	String input = this.txtN.getText();
     	
     	Integer n = 0;
     	
     	try {
     		
     		n = Integer.parseInt(input);
     		
     	} catch(NumberFormatException e) {
     		
     		this.txtResult.setText("Inserisci un valore numerico a N !");
     		return;
     		
     	}
     	
     	Integer anno = this.cmbAnno.getValue();
     	
     	if(anno == null) {
     		
     		this.txtResult.appendText("Scegli un anno!");
     		return;
     		
     	}
     	
     	this.model.creaGrafo(n, anno);
    	

        this.txtResult.appendText(
        		String.format("Grafo creato con %d vertici e %d archi \n", this.model.getNNodes(), this.model.getNArchi()));
        
        
      this.cmbUtente.getItems().clear();
      this.cmbUtente.getItems().addAll(this.model.getVertici());
    	
    	

    	
    	
    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
        
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	User u = this.cmbUtente.getValue();
    	
    	if(u == null) {
    		
    		this.txtResult.appendText("Scegli un utente prima!");
    		return;
    		
    	}
    	
    	List<User> users = this.model.utentiPiuSimili(u);
    	
    	this.txtResult.appendText("Utenti più simili a " + u + "\n\n");
    	
    	for(User us : users) {
    		
    		this.txtResult.appendText(us + "\t\t GRADO: " + this.model.getMax()  +"\n");
    		
    	}
    	
    	

    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
        
    	
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
     	String input1 = this.txtX1.getText();
     	
     	Integer x1 = 0;
     	
     	try {
     		
     		x1 = Integer.parseInt(input1);
     		
     	} catch(NumberFormatException e) {
     		
     		this.txtResult.setText("Inserisci un valore numerico a X1!");
     		return;
     		
     	}
     	
     	String input2 = this.txtX2.getText();
     	
     	Integer x2 = 0;
     	
     	try {
     		
     		x2 = Integer.parseInt(input2);
     		
     	} catch(NumberFormatException e) {
     		
     		this.txtResult.setText("Inserisci un valore numerico a X2!");
     		return;
     		
     	}
    	
     	if(x2 > this.model.getNNodes()) {
     		
     		this.txtResult.appendText("Numero utenti maggiore di quelli disponibili!");
     		return;
     		
     	}
     	
     	if(x1 >= x2) {
     		
     		this.txtResult.appendText("Il numero di intervistatori deve essere minore del numero di utenti da intervistare! ");
     		return;
     		
     	}
     	
     	SimulatorResult sr = this.model.Simula(x1, x2);
     	
    	this.txtResult.appendText("Simulazione effettuata in " + sr.getNumGiorni() + " giorni \n");
    	
    	Map<Integer, Integer> mappaInterviste = sr.getGiornalistaNumIntervistati();
    	
    	for(Integer i : mappaInterviste.keySet()) {
    		
    		this.txtResult.appendText("Giornalista " + i + "  =  " + mappaInterviste.get(i) + "\n");
    		
    	}
     	

    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	for(int i=2005; i<=2013; i++) {
    		
    		this.cmbAnno.getItems().add(i);
    		
    	}
    }
}
