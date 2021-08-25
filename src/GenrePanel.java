
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author JulioL
 */
public class GenrePanel extends Panel {

    private MainMenu mainM;

    private Genre genre;
    private TextComponent posT;

    private Button back;
    private Button desc;

    private Button update;
    private InputModel updateModel;

    private TextAComponent textA;

    public GenrePanel(MainMenu mainMenu, int cHeight, int width, int mHeight, String fStyle, short fSize) {
        super(cHeight, width, mHeight, fStyle, fSize);
        this.mainM = mainMenu;
        posT = new TextComponent("", super.getfStyle(), Settings.genrePFSize, Color.WHITE);

        back = new Button("Back");
        back.setOnMousePressed(e -> saveGenre());

        desc = new Button("Notes");
        update = new Button("Update");
        update.setOnMousePressed(e -> openUpdate());

        updateModel = new InputModel("Update", Settings.updateCHeight, Settings.updateWidth,
                Settings.updateMHeight, Settings.updateRows, super.getfStyle(), Settings.updateFSize);
        updateModel.renameRows(Settings.addTitles);
        updateModel.getModelSelect().setOnMousePressed(e -> updateGenre());

        super.setUpCustomization();
        super.setCustomizationOffset(Settings.buttonWidth
                + (2 * Settings.buttonOffset));
        super.addToMenuBar(posT, 0, 0);

        back.setPrefHeight(Settings.buttonHeight);
        back.setPrefWidth(Settings.buttonWidth);

        textA = new TextAComponent();
        desc.setPrefHeight(Settings.buttonHeight);
        desc.setPrefWidth(Settings.buttonWidth);
        desc.setOnMousePressed(e
                -> super.openNewWindow(textA.getMainScene()));

        update.setPrefHeight(Settings.buttonHeight);
        update.setPrefWidth(Settings.buttonWidth);

        super.addToMenuBar(back, 0, 0);
        super.addToMenuBar(desc, 0, 0);
        super.addToMenuBar(update, 0, 0);
        postionButtons();
    }


    public void deleteImage(ImageComponent img) {
        super.deleteImage(img);
        genre.deleteImage(img);
    }

    public void openUpdate() {
        updateModel.editLabel(
                1,
                genre.getMedium() == Type.MANGA ? "Chapter: " : "Episode: ");
        updateModel.setGenre(genre);
        this.openNewWindow(updateModel.getMainScene());
    }

    public void updateGenre() {
        boolean valid = InfoManager.checkInputs(updateModel);
        if (!valid) {
            return;
        }
        //0 - name
        //1 - pos
        //2 - rating
        String[] info = updateModel.getLabelTexts().split(",");
        String name = info[0];
        int pos = Integer.parseInt(info[1]);
        int rating = Integer.parseInt(info[2]);
        genre.update(name, pos, rating);
        posT.setfSize(Settings.genrePFSize);
        super.getTitleT().setfSize(Settings.genrePFSize);
        updateTitle(name, genre.getMedium(), pos);
    }

    public void updateTitle(String title, Type type, int pos) {
        String posName = "Episode";
        if (type == Type.MANGA) {
            posName = "Chapter ";
        }
        TextComponent t = super.getTitleT();
        super.setTitle(title);
        t.setfSize(Settings.fSize);
        posT.setfSize(Settings.fSize);

        double titleLength = t.getWidth();
        posT.setText(String.format("[%s - %d] [Rating - %d]",
                posName, pos, genre.getRating()));
        while (titleLength >= update.getLayoutX() - posT.getWidth()) {
            int newFSize = (int) (t.getfSize() * 0.98);
            t.setfSize(newFSize);
            posT.setfSize(newFSize);
            titleLength = super.getTitleT().getWidth();
            genre.setfSize((short)newFSize);
        }

        posT.setLayoutX(titleLength);
        posT.setLayoutY(super.getmHeight() * super.getDefaultYScale());
    }

    public void repositionHelper(int cHeight, int width) {
        super.repositionLayout(cHeight, width);
        super.setCustomizationOffset(Settings.buttonWidth
                + (2 * Settings.buttonOffset));
        mainM.getMain().setWidth(width + 16); //+16
        mainM.getMain().setHeight(super.getmHeight() + cHeight + 39);//+39
        postionButtons();
    }

    public void repositionLayout(int cHeight, int width) {
        repositionHelper(cHeight, width);
        super.clearImages();
        genre.clearImages();
        posT.setfSize(Settings.genrePFSize);
        super.getTitleT().setfSize(Settings.genrePFSize);
        updateTitle(genre.getTitle(), genre.getMedium(), genre.getPos());
    }

    public void postionButtons() {
        double x = super.getCustomizeX() + super.getOpenCWidth() + Settings.buttonOffset;
        double y = (super.getmHeight() / 2.0) - Settings.buttonHeight / 2.0;

        back.setLayoutX(x);
        back.setLayoutY(y);

        x = super.getCustomizeX() - (2 * (Settings.buttonWidth + Settings.buttonOffset));
        update.setLayoutX(x);
        update.setLayoutY(y);
        x = x + Settings.buttonWidth + Settings.buttonOffset;
        desc.setLayoutX(x);
        desc.setLayoutY(y);
    }

    public void setTextAttributes(short fSize, String fStyle) {
        super.setTextAttributes(fSize, fStyle);
        posT.setfSize(fSize);
        posT.setfStyle(fStyle);
        posT.setFont(new Font(fStyle, fSize));
    }

    public void saveGenre() {
        if (genre == null) {
            return;
        }
        genre.setDesc(textA.getNotes().getText());
        genre.setNew(false);
        genre.setIsLocked(super.getCustomize().IsLocked());
        genre.setWidth((int) this.getWidthT());
        genre.setcHeight((int) this.getcHeight());
        genre.setmHeight((int) this.getmHeight());
        genre.setfSize((short) this.getfSize());
        genre.setfStyle(mainM.getfStyle());

        ArrayList<ImageComponent> temp = super.getImages();

        genre.getImages().clear();
        for (int i = 0; i < temp.size(); i++) {

            genre.addImage(temp.get(i));
        }
        this.getOtherWindow().close();
        mainM.switchToMainScene();
    }

    public void setGenre(Genre genre) {
        this.removeCenterImages();
        this.getImages().clear();
        textA.getNotes().setText("");
        this.genre = genre;

        if(genre.IsNew()){
            int width =  mainM.getWidthT();
            int cHeight = mainM.getcHeight();
            genre.setWidth(800); //width
            genre.setcHeight( 400);//cheight
            genre.setmHeight( mainM.getmHeight());
            this.repositionLayout(400, 800);//cheight width
        }

        //if not new 
        if (!genre.IsNew()) {
            //TODO - set images
            super.getImages().clear();
            this.removeCenterImages();

            ArrayList<ImageComponent> temp = genre.getImages();
            for (int i = 0; i < temp.size(); i++) {
                temp.get(i).loadImage();
                this.addCenterImage(temp.get(i));
            }
            //set width/cHeight/mHeight
            this.repositionHelper(genre.getcHeight(), genre.getWidth());
            //isLocked
            boolean lock = genre.isIsLocked();

            super.getCustomize().setLocked(lock);
            textA.getNotes().setText(genre.getDesc());
        }

        this.updateTitle(genre.getTitle(), genre.getMedium(), genre.getPos());

    }

}
