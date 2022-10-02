package controller.util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {



    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-prompt-text-fill: #ff0000");
                    textFieldKey.setStyle("-fx-text-fill: #ff0000");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-prompt-text-fill: #008000");
            textFieldKey.setStyle("-fx-text-fill: #008000");

        }
        btn.setDisable(false);
        return true;
    }

}