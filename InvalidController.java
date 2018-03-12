package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class InvalidController {

    @FXML private Button invButton;

    @FXML
    private void exitPopUp() {
        Stage stage = (Stage) invButton.getScene().getWindow();
        stage.close();
    }

}
