package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button browseBtn;
    @FXML
    private Button countLinesBtn;
    @FXML
    private TextField addressField;

    public void browseBtnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("C Programmes", "*.c"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
            addressField.setText(selectedFile.getAbsolutePath());
        } else {
            System.out.println("file is not valid");
        }
    }
}
