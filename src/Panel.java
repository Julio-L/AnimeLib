
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author JulioL
 */
public class Panel extends BorderPane {
    //main window
    private Stage main;
    //popup window
    private Stage otherWindow;

    //this current scene
    private Scene mainScene;

    //center height
    private int cHeight;
    //menu height
    private int mHeight;
    //total width of window
    private int widthT;

    ///////////////////////////////////////
    private Pane menuBar;
    private Pane centerPane;
    //////////////////////////////////////

    //Separates menu and center pane//
    private Line divider;
    private double lineX1;
    private double lineY1;
    private double lineX2;
    private double lineY2;

    //Title text node
    private TextComponent titleT;
    private String title = "Title";
    private String fStyle = "contrast";
    private int fSize = 55;
    private Color textColor;

    //customize
    //openC (open customization)
    private Button openC;
    private short openCHeight = 30;
    private short openCWidth = 100;

    private CustomizeWindow customize;
    boolean locked = false;
    //button x position
    private double customizeX;
    private int customizeXOffset = 8;

    //image container
    private ArrayList<ImageComponent> images;

    //Defualt
    private double defaultTitleX = 10;
    private double defaultYScale = .70;

    public Panel(int cHeight, int width, int mHeight) {
        this(cHeight, width, mHeight, Settings.fStyle, (short) 55);
    }

    /**
     * @param cHeight Height of the center pane
     * @param widthT total window width
     * @param mHeight Height of menu pane
     *
     */
    public Panel(int cHeight, int widthT, int mHeight, String fStyle, int fSize) {
        mainScene = new Scene(this, widthT, mHeight + cHeight);
        this.cHeight = cHeight;
        this.widthT = widthT;
        this.mHeight = mHeight;
        this.setPrefHeight(mHeight + cHeight);
        this.setPrefWidth(widthT);

        this.fStyle = fStyle;
        this.fSize = fSize;

        //create a text node
        this.textColor = Settings.textColor;
        this.titleT = new TextComponent(title, fStyle, fSize, textColor);

        //position title with default x and y scaled by the hgiht of the text
        this.setTextPos(titleT, defaultTitleX, mHeight * defaultYScale);

        //setup center pane
        centerPane = new Pane();
        centerPane.setPrefHeight(this.cHeight);
        centerPane.setPrefWidth(this.widthT);
        centerPane.setStyle(Settings.bColor);
        this.setCenter(centerPane);

        //setup menu pane
        menuBar = new Pane();
        menuBar.setPrefHeight(this.mHeight);
        menuBar.setPrefWidth(this.widthT);
        menuBar.setStyle(Settings.bColor);
        super.setTop(menuBar);

        //setup line sep menu and center pane
        lineX1 = 0;
        lineY1 = this.mHeight;
        lineX2 = widthT+8;
        lineY2 = this.mHeight;

        divider = new Line(lineX1, lineY1, lineX2, lineY2);
        divider.setStrokeWidth(Settings.dividerStroke);
        divider.setStroke(Settings.dividerC);
        menuBar.getChildren().addAll(divider, titleT);

        this.otherWindow = new Stage();
        this.otherWindow.setAlwaysOnTop(true);
        this.otherWindow.setResizable(false);
    }

    public String saveFormat() {
        String settings = String.format("%d,%d,%d,%s,%d,%d,%d",
                cHeight, widthT, mHeight, fStyle, fSize, locked ? 1 : 0, images.size());
        return settings;
    }

    public int getNumImages() {
        return images.size();
    }

    public void clearImages() {
        centerPane.getChildren().removeAll(images);
        images.clear();
    }

    public void setlock(boolean locked) {
        this.locked = locked;
        if (locked) {
            customize.lockWindow();
        } else {
            customize.unlockWindow();
        }
    }

    public void updateLock(boolean lock) {
        this.locked = lock;
    }

    public void setMain() {
        main = new Stage();
        main.setResizable(false);
    }
    

    public void switchToOtherScence(Scene scene) {
        otherWindow.close();
        main.setScene(scene);
    }

    public void switchToMainScene() {
        if (main != null) {
            main.setScene(mainScene);
            this.main.setWidth(widthT + 16);
            this.main.setHeight(mHeight + cHeight + 39);
            main.show();
        }
    }

    public void removeCenterImages() {
        centerPane.getChildren().clear();
    }

    public void addCenterImage(ImageComponent image) {
        centerPane.getChildren().add(image);
        images.add(image);
    }

    public void centerWidth(double newWidth) {
        lineX2 = newWidth;
        centerPane.setPrefWidth(newWidth);
        divider.setEndX(lineX2);
    }

    public void nullStage() {
        otherWindow = null;
    }

    public void setUpCustomization() {
        //setup button
        openC = new Button("Customize");
        openC.setPrefHeight(openCHeight);
        openC.setPrefWidth(openCWidth);
        openC.setOnMousePressed((e) -> openCustomizationWindow());
        double openY = (mHeight / 2.0) - (openCHeight / 2.0);
        this.customizeX = (widthT - openCWidth) - customizeXOffset;
        addToMenuBar(openC, customizeX, openY);
        images = new ArrayList();
        customize = new CustomizeWindow(this, Settings.customizeCHeight,
                Settings.customizeWidth, Settings.customizeMHeight, fStyle, Settings.customizeFSize);
    }

    public void repositionLayout(int cHeight, int width) {
        this.cHeight = cHeight;
        this.widthT = width;
        double openY = (mHeight / 2.0) - (openCHeight / 2.0);
        this.customizeX = (widthT - openCWidth) - customizeXOffset;
        openC.setLayoutX(customizeX);
        openC.setLayoutY(openY);

        lineX2 = widthT+8;
        divider.setEndX(lineX2);

        centerPane.setPrefWidth(widthT);
        menuBar.setPrefWidth(widthT);
        centerPane.setPrefHeight(cHeight);

    }

    public void setCustomizationOffset(int offset) {
        this.customizeXOffset = offset;
        this.customizeX = (widthT - openCWidth) - customizeXOffset;
        openC.setLayoutX(customizeX);
    }

    public void deleteImage(ImageComponent img) {
        images.remove(img);
        centerPane.getChildren().remove(img);
    }

    public void closeOtherWindow() {
        otherWindow.close();
    }

    public void setCustomizeWidth(short width) {
        this.openCWidth = width;
        this.customizeX = (widthT - openCWidth) - customizeXOffset;
        openC.setLayoutX(customizeX);
    }

    public void setTextAttributes(short fSize, String fStyle) {
        this.fSize = fSize;
        this.fStyle = fStyle;

        titleT.setfSize(fSize);
        titleT.setfStyle(fStyle);
        titleT.setFont(new Font(fStyle, fSize));
    }

    public void openNewWindow(Scene scene) {
        otherWindow.setScene(scene);
        otherWindow.show();
    }

    public void openCustomizationWindow() {
        otherWindow.setScene(customize.getMainScene());
        otherWindow.show();
    }

    public void hideCustomization() {
        openC.setDisable(true);
        openC.setOpacity(0);
    }

    public void addImage(ImageComponent img) {
        images.add(img);
    }

    public void setTextPos(Text t, double x, double y) {
        t.setLayoutX(x);
        t.setLayoutY(y);
    }

    public Pane getMenuBar() {
        return menuBar;
    }

    public void addToMenuBar(Node node, double x, double y) {
        //TODO: add boundary check
        node.setLayoutX(x);
        node.setLayoutY(y);
        menuBar.getChildren().add(node);
    }

    public void addToCenter(Node node, double x, double y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
        centerPane.getChildren().add(node);
    }

    public boolean setTitle(String title) {
        titleT.setText(title);
        this.title = title;
        return true;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public TextComponent getTitleT() {
        return titleT;
    }

    public String getfStyle() {
        return fStyle;
    }

    public short getOpenCWidth() {
        return openCWidth;
    }

    public int getfSize() {
        return fSize;
    }

    public double getDefaultTitleX() {
        return defaultTitleX;
    }

    public double getDefaultYScale() {
        return defaultYScale;
    }

    public double getTotalHeight() {
        return cHeight + mHeight;
    }

    public int getcHeight() {
        return cHeight;
    }

    public int getmHeight() {
        return mHeight;
    }

    public int getWidthT() {
        return widthT;
    }

    public int getHeightT() {
        return mHeight + cHeight;
    }

    public Color getTextColor() {
        return textColor;
    }

    public CustomizeWindow getCustomize() {
        return customize;
    }

    public double getCustomizeX() {
        return (widthT - openCWidth) - customizeXOffset;
    }

    public Stage getOtherWindow() {
        return otherWindow;
    }

    public ArrayList<ImageComponent> getImages() {
        return images;
    }

    public Pane getCenterPane() {
        return centerPane;
    }

    public Stage getMain() {
        return main;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public int getCustomizeXOffset() {
        return customizeXOffset;
    }

}
