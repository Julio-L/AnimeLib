
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author JulioL
 */
public class ImageComponent extends ImageView {

    private Panel mainPanel;

    private Image img;
    private String url;
    private InputModel imgTools;
    private Button delete;

    private double dragDeltaX;
    private double dragDeltaY;

    public ImageComponent(Panel mainPanel, String url, boolean load) {
        this.mainPanel = mainPanel;
        this.url = url;

        if (load) {
            this.img = new Image(url);
            this.setImage(img);
        }
        

        imgTools = new InputModel(Settings.ImgCTitle, Settings.ImgCHeight,
                Settings.ImgCWidth, Settings.ImgCMHeight,
                Settings.toolRows, Settings.fStyle,
                Settings.ImgCFSize);
        imgTools.renameRows(Settings.toolNames);
        imgTools.setInputSpacing(Settings.ImgCSpacing);
        imgTools.setLabelWidth(Settings.ImgCLabelW);

        delete = new Button("Delete");
        delete.setPrefWidth(Settings.ImgCBW);
        delete.setPrefHeight(Settings.ImgCBH);
        delete.setOnMousePressed(e -> {
            mainPanel.deleteImage(this);
            mainPanel.closeOtherWindow();
        });
        imgTools.addToMenuBar(delete, 0, 0);
        this.positionDelete();

        imgTools.getModelSelect().setOnMousePressed(e -> onUpdate());

        this.setOnMousePressed(e -> {
            mainPanel.openNewWindow(imgTools.getMainScene());
            dragDeltaX = this.getLayoutX() + e.getX();
            dragDeltaY = this.getLayoutY() + e.getY();
        });

        this.setOnMouseDragged(e -> {
            
            double newX = e.getSceneX() - dragDeltaX;
            double newY = e.getSceneY() - dragDeltaY;
            
            
            double imageWidth = (this.getFitWidth());
            double imageHeight = (this.getFitHeight());
            int mainWidth = mainPanel.getWidthT();
            int mainHeight = mainPanel.getcHeight();
            
            
            if(newX >=-40 && newX + imageWidth <=  mainWidth+40){
                this.setTranslateX(newX);
            }
            
            if(newY >=-40 && newY+imageHeight <=  mainHeight+40){
                this.setTranslateY(newY);
            }
           
        });
    }

    public void positionDelete() {
        double x = Settings.ImgCWidth - Settings.ImgCBW - 10;
        double y = ((Settings.ImgCMHeight - (Settings.dividerStroke)) / 2.0)
                - (Settings.ImgCBH / 2.0);
        delete.setLayoutX(x);
        delete.setLayoutY(y);
    }

    public void loadImage() {
        if (img == null) {
            img = new Image(url);
        }
        this.setImage(img);
    }

    public void removeImage() {
        this.setImage(null);
    }

    public void loadSettings(double width, double height,
            double x, double y, boolean isLocked) {
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setLayoutX(Settings.addInsertX);
        this.setLayoutY(Settings.addInsertY);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setDisable(isLocked);
    }

    public String saveFormat() {
        return String.format("%s,%f,%f,%f,%f", url,
                this.getFitWidth(), this.getFitHeight(),
                this.getTranslateX(), this.getTranslateY());
    }

    public void onUpdate() {
        //current fields
        //[0] - height
        //[1] - width
        String height = imgTools.getTextAt(0);
        String width = imgTools.getTextAt(1);
        
        int maxWidth  = mainPanel.getWidthT();
        int maxHeight = mainPanel.getcHeight();
        
        int newWidth = 100;
        int newHeight = 100;

        try {
            newWidth = Integer.parseInt(width);
            if(newWidth < 100){
                newWidth = 100;
            }else if(newWidth >= maxWidth){
                newWidth = maxWidth - 2;
            }
            this.setFitWidth(newWidth);
        } catch (NumberFormatException e) {
            imgTools.setError(1, "ERROR: Invalid Number");
        }
        
         try {
            newHeight = Integer.parseInt(height);
            if(newHeight < 100){
                newHeight = 100;
            }else if(newHeight >= maxHeight){
                newHeight = maxHeight - 2;
            }
            this.setFitHeight(newHeight);
        } catch (NumberFormatException e) {
            imgTools.setError(0, "ERROR: Invalid Number");
        }
        
    }

    public String getUrl() {
        return url;
    }

    public String toString() {
        return url;
    }

}
