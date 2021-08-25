
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 *
 * @author JulioL
 */
public class LabelComponent extends Label{
    private String title;
    private String fStyle;
    private int fSize;
    private Color color;
    private DropShadow ds;
    private Color shadowC;
    
    public LabelComponent(String title, String fStyle, int fSize, Color color){
        this.title = title;
        super.setText(title);
         this.fStyle = fStyle;
         this.fSize = fSize;
         this.color = color;
         super.setTextFill(color);
         super.setFont(new Font(fStyle, fSize));
         ds = new DropShadow();
         ds.setBlurType(BlurType.GAUSSIAN);  
         ds.setOffsetY(2.0f);
         shadowC = color;
         ds.setColor(shadowC);

         
         super.setEffect(ds);
         super.setCache(true);
    }
    
    public void setFontSize(int fSize){
        this.fSize = fSize;
        super.setFont(new Font(fStyle, this.fSize));
    }
    
}
