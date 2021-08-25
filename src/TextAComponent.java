
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 *
 * @author JulioL
 */
public class TextAComponent extends Panel {

    private TextArea notes;

    public TextAComponent() {
        super(Settings.TextACHeight, Settings.TextAWidth, 
                Settings.TextAMHeight, Settings.fStyle, Settings.TextAFSize );
        super.setTitle("Notes");
        notes = new TextArea();
        
        double xOffset = .80;
        double yOffset = .80;
        int width = (int) (Settings.TextAWidth * xOffset);
        int height = (int) (Settings.TextACHeight * yOffset);
        notes.setPrefSize(width, height);
        notes.setWrapText(true);

        double x = (Settings.TextAWidth - width) / 2.0;
        double y = ((Settings.TextACHeight - height) / 2.0);
        super.addToCenter(notes, x, y);
    }
    
    public void setText(String text){
        notes.setText(text);
    }

    public TextArea getNotes() {
        return notes;
    }
    
    

}
