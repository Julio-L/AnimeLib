
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author JulioL
 */
public class TextComponent extends Text {
    private String title;
    private String fStyle;
    private int fSize;
    private Color color;
    private DropShadow ds;
    private Color shadowC;

    public TextComponent(String title, String fStyle, int fSize, Color color) {
        this.title = title;
        super.setText(title);
        this.fStyle = fStyle;
        this.fSize = fSize;
        this.color = color;
        super.setFill(color);
        super.setFont(new Font(fStyle, fSize));
        ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setOffsetY(2.0f);
        shadowC = color;
        ds.setColor(shadowC);

        super.setEffect(ds);
        super.setCache(true);

    }

    public void setTitle(String title) {
        this.title = title;
        this.setText(title);
    }

    public double getHeight() {
        return this.getBoundsInLocal().getHeight();
    }

    public double getWidth() {
        return this.getBoundsInLocal().getWidth();
    }

    public void setfStyle(String fStyle) {
        this.fStyle = fStyle;
    }

    public void setfSize(int fSize) {
        this.fSize = fSize;
        super.setFont(new Font(fStyle, fSize));
    }

    public int getfSize() {
        return fSize;
    }

    
    
    
}
