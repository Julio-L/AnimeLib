
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author JulioL
 */
public class Input extends HBox {

    private LabelComponent text;
    private TextField tField;
    private double spacing;
    private boolean error = false;

    public Input(String title, String fStyle, int fSize, Color color, double spacing) {
        text = new LabelComponent(title, fStyle, fSize, color);
        tField = new TextField();
        tField.setOnMousePressed(e -> {
            if (error) {
                tField.setText("");
                error = false;
            }
            tField.setStyle("-fx-text-inner-color: black");
        });
        this.spacing = spacing;
        super.setSpacing(this.spacing);
        super.getChildren().addAll(text, tField);
    }

    public void errorMsg(String msg) {
        tField.setStyle("-fx-text-inner-color: red");
        tField.setText(msg);
        error = true;
    }
    
    public void setFontSize(int fSize){
        text.setFontSize(fSize);
    }

    public void newSpacing(double spacing) {
        this.spacing = spacing;
        super.setSpacing(spacing);
    }

    public Label getText() {
        return text;
    }

    public TextField gettField() {
        return tField;
    }

    public void setText(String newText) {
        this.text.setText(newText);
    }

    public String toString() {
        return tField.getText();
    }

    public void setLabelText(String msg) {
        tField.setText(msg);
    }
    
   public void setTFWidth(int width){
       tField.setPrefWidth(width);
   }

}
