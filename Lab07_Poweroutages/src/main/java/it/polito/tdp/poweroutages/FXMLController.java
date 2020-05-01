package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ImageView img;

    @FXML
    private ChoiceBox<Nerc> choiceNERC;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button btnWorstCase;

    @FXML
    void doWorstCase(ActionEvent event) {
    	Nerc nerc = choiceNERC.getValue();
    	int anni = Integer.parseInt(txtYears.getText());
    	int ore = Integer.parseInt(txtHours.getText());
    	String s = this.model.worstCase(nerc, anni, ore);
    	txtResult.clear();
    	txtResult.appendText(s);
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert img != null : "fx:id=\"img\" was not injected: check your FXML file 'Scene.fxml'.";
        assert choiceNERC != null : "fx:id=\"choiceNERC\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnWorstCase != null : "fx:id=\"btnWorstCase\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
		this.model = model;
		List<Nerc> nercList = model.getNercList();
		choiceNERC.getItems().addAll(nercList);
	}
}

