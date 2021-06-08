/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
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

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {

    	this.txtResult.clear();
    	if(this.cmbSquadra.getValue().equals(null)) {
    		this.txtResult.setText("Scegli un team");
    		return ;
    	}
    	if(this.model.getVertici()==null) {
    		this.txtResult.setText("Creare prima il grafo");
    		return ;
    	}
    	this.model.doClassifica(this.cmbSquadra.getValue());
    	
    	this.txtResult.appendText("\nSQUADRE MIGLIORI: \n");
    	for(Adiacenza a : this.model.getMigliori()) {
    		this.txtResult.appendText(a.getT1().toString()+"["+a.getDifferenza()+"]\n");
    	}
    	
    	this.txtResult.appendText("\nSQUADRE PEGGIORI: \n");
    	for(Adiacenza a : this.model.getPeggiori()) {
    		this.txtResult.appendText(a.getT2().toString()+"["+a.getDifferenza()+"]\n");
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.model.creaGrafo();
    	this.txtResult.setText("Grafo creato\n #vertici: "+this.model.nVertici()+"\n# Archi: "+this.model.nArchi());
    	
    	this.cmbSquadra.getItems().addAll(this.model.getVertici());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.model.getVertici()==null) {
    		this.txtResult.setText("Creare prima il grafo");
    		return ;
    	}
    	int N;
    	try {
    		N = Integer.parseInt(this.txtN.getText());
    	}catch(NumberFormatException ne) {
    		ne.printStackTrace();
    		return ;
    	}
    	
    	int X;
    	try {
    		X = Integer.parseInt(this.txtX.getText());
    	}catch(NumberFormatException ne) {
    		ne.printStackTrace();
    		return ;
    	}
    	
    	this.model.simula(N, X);
    	this.txtResult.appendText("Media Reporter: "+this.model.getMedia());
    	this.txtResult.appendText("\nPartite inferiori a X: "+this.model.getInf());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
