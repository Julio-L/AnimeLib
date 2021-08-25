
import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

/**
 *
 * @author JulioL
 */
public class CustomizeWindow extends InputModel {
//    private Scene customizeScene;

    //This fields comes from the panel to customized
    private Panel customizableWindow;
    private ArrayList<ImageComponent> otherWindowImgs;

    //lock components
    private Button lockUnlock;
    private boolean Locked;

    //                   COMPONENTS                 //       
    //                  Add Image                         //
    //previews image to be inserted
    private ImageView previewImgV;
    private Image previewImg;
    //buttons used to open and confirm selection of image
    private Button addImg;
    private Button confirmImg;
    //offset used to place buttons by scaling to center pane width//height
    private double imgXOffset = 0.5;
    private double imgYOffset = 0.45;

    //universal offset to add padding between the two buttons
    private double offset = 10;

    //scales used to get actual x and y
    private double addImgY = super.getcHeight() * imgYOffset;
    private double addBWidth = 100;

    //temp border for the image (dimensions will be the same as the rec border)
    private Color borderC = Color.WHITE;
    private double borderW = addBWidth * 2 + offset;
    private double borderH = super.getcHeight() * imgYOffset * .80;
    private Rectangle imgBorder = new Rectangle(borderW, borderH);

    private double addImgX = super.getWidthT() * imgXOffset - (borderW / 2.0);

    //manager is used to obtain the actual image from the user
    private FileChooser manager;
    private FileChooser.ExtensionFilter filter;
    private String url;

    private double addDefaultWidth = 0.40;
    private double addDefaultHeight = 0.40;
    //                  Add Image                         // 
    //                   COMPONENTS                 //       

    public CustomizeWindow(Panel customizeWindow, int cHeight, int widthT, short mHeight, String fStyle, short fSize) {
        super("Customization", cHeight, widthT, mHeight,
                Settings.customNumRows, fStyle, (short) Settings.customizeFSize);
        super.nullStage();
        super.renameRows(Settings.custTools);
        super.shiftDown(Settings.shiftDown);
        super.setLabelWidth(Settings.customLabelW);
        super.setInputSpacing(Settings.customSpacing);
        super.setTextFieldWidth(135);
        super.setLabelFontSize(Settings.labelFSize);
        super.getModelSelect().setOnMousePressed(e -> resize());

        //set window used to customize
        this.customizableWindow = customizeWindow;
        //where the images will be placed
        this.otherWindowImgs = customizableWindow.getImages();

        //full set up of image component
        setUpAddImg();
        setUpLockUnlock();
        super.shiftRight(addImgX);
    }

    public void resize() {
        String height = super.getTextAt(0);
        String width = super.getTextAt(1);
        if (height.isEmpty()) {
            super.setError(0, "ERROR: No Number Found");
        }

        if (width.isEmpty()) {
            super.setError(1, "ERROR: No Number Found");
        }

        int newWidth = 550;
        int newHeight = 400;

        try {
            newWidth = Integer.parseInt(width);
            if(newWidth < 550){
                newWidth = 550;
            }
        } catch (NumberFormatException e) {
            super.setError(1, "ERROR: Invalid Number");
        }

        try {
            newHeight = Integer.parseInt(height);
            if(newHeight < 400){
                newHeight = 400;
            }
        } catch (NumberFormatException e) {
            super.setError(0, "ERROR: Invalid Number");
        }

        customizableWindow.repositionLayout(newHeight, newWidth);

    }

    private void setUpLockUnlock() {
        lockUnlock = new Button("Lock");
        lockUnlock.setPrefWidth(Settings.lockWidth);
        lockUnlock.setPrefHeight(Settings.lockHeight);
        Locked = false;
        super.addToMenuBar(lockUnlock, super.getWidthT() - Settings.lockWidth - offset,
                (super.getmHeight() / 2.0) - Settings.lockHeight / 2.0);
        lockUnlock.setOnMousePressed(e -> lockAction());

    }

    public void lockAction() {
        if (Locked) {
            unlockWindow();
        } else {
            lockWindow();
        }
        customizableWindow.updateLock(Locked);
    }

    public void lockWindow() {
        Locked = true;
        lockUnlock.setText("Unlock");
        disableComponents();
    }

    public void unlockWindow() {
        Locked = false;
        lockUnlock.setText("Lock");
        enableComponents();
    }

    public void disableComponents() {
        //components - images
        for (int i = 0; i < otherWindowImgs.size(); i++) {
            otherWindowImgs.get(i).setDisable(true);
        }
    }

    public void enableComponents() {
        //components - images
        for (int i = 0; i < otherWindowImgs.size(); i++) {
            otherWindowImgs.get(i).setDisable(false);
        }
    }

    public void openImgSelection() {
        File selectedFile = manager.showOpenDialog(
                customizableWindow.getOtherWindow());
        if (selectedFile != null) {
            imgBorder.setOpacity(0);
            url = selectedFile.toURI().toString();
            previewImg = new Image(url, 100, 100, true, false, false);
            previewImgV.setImage(previewImg);
            previewImgV.setFitHeight(borderH);
            previewImgV.setFitWidth(borderW);
        }
    }

    private void confirmImg() {
        ImageComponent confirmed = new ImageComponent(customizableWindow, url, true);
        confirmed.setFitHeight(customizableWindow.getcHeight() * addDefaultHeight);
        confirmed.setFitWidth(customizableWindow.getWidthT() * addDefaultWidth);
        confirmed.setCache(true);
        otherWindowImgs.add(confirmed);
        customizableWindow.addToCenter(confirmed, Settings.addInsertX, Settings.addInsertY);
        if(customizableWindow instanceof MainMenu){
            MainMenu m = (MainMenu)customizableWindow;
            m.listToFront();
        }
    }

    private void setUpAddImg() {
        previewImgV = new ImageView();

        addImg = new Button("Add Image");
        addImg.setPrefWidth(addBWidth);
        addImg.setOnMousePressed(e -> openImgSelection());

        confirmImg = new Button("Confirm");
        confirmImg.setPrefWidth(addBWidth);
        confirmImg.setOnMousePressed(e -> confirmImg());

        imgBorder.setFill(borderC);
        super.addToCenter(previewImgV, addImgX, offset);
        super.addToCenter(addImg, addImgX, addImgY);
        super.addToCenter(confirmImg, addImgX + addBWidth + offset, addImgY);
        super.addToCenter(imgBorder, addImgX, offset);

        manager = new FileChooser();
        filter = new FileChooser.ExtensionFilter("Img Files (*.hng)", "*.jpg", "*.png", "*.gif");

    }

    public boolean IsLocked() {
        return Locked;
    }

    public void setLocked(boolean Locked) {
        this.Locked = !Locked;
        lockAction();
    }

    public Button getConfirmImg() {
        return confirmImg;
    }

}
