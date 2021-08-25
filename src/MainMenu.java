
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 *
 * @author JulioL
 */
public class MainMenu extends Panel {

    //listview that handles add/remove
    private CollectionManager list;

    //panel used to display the genre selected
    //buttons
    private Button[] addGenre;
    private Type currentGenre;

    //add window
    private InputModel addWindow;
    private boolean isAddOpen = false;

    private GenrePanel genrePanel;
    private Scene genreScene;

    public MainMenu(int cHeight, int width, int mHeight, String fStyle, short fSize) {
        super(cHeight, width, mHeight, fStyle, fSize);
        super.setTitle(Settings.TITLE);
        super.setUpCustomization();

        super.setMain();
        this.setOnClose();
        //setup genre panel
        genrePanel = new GenrePanel(this, cHeight, width, mHeight, fStyle, Settings.genrePFSize);
        genreScene = genrePanel.getMainScene();

        //setup list
        double[] listS = computeListSettings();
        list = new CollectionManager(this, listS[0], listS[1]);
        list.addToPanel(super.getCenterPane());

        //create add window which is shared with Manga and Anime button
        addWindow = new InputModel("Add", Settings.addCHeight, Settings.addWidth,
                Settings.addMHeight, Settings.numRows, super.getfStyle(), Settings.addFSize);
        addWindow.renameRows(Settings.addTitles);
        addWindow.getModelSelect().setOnMousePressed(e -> insertNewGenre());

        //setup buttons//
        //adds a new anime/manga to open addWindow 
        addGenre = new Button[2];

        addGenre[0] = new Button("Manga");

        addGenre[0].setOnMousePressed(e -> addManga());
        addGenre[1] = new Button("Anime");
        addGenre[1].setOnMousePressed(e -> addAnime());

        for (int i = 0; i < addGenre.length; i++) {
            addGenre[i].setPrefHeight(Settings.addGenreHeight);
            addGenre[i].setPrefWidth(Settings.addGenreWidth);
            super.addToMenuBar(addGenre[i], 0, 0);
        }

        //position Manga and Anime buttons in the menu bar
        posMangaAnimeButtons();
        positionList();

    }
    
    public void setOnClose(){
        super.getMain().setOnCloseRequest(e ->{
            super.getOtherWindow().close();
            genrePanel.getOtherWindow().close();
        });
    }

    public String saveFormat() {
        return super.saveFormat() + "," + list.getNumItems();
    }

    public void repositionLayout(int cHeight, int width) {
        super.repositionLayout(cHeight, width);
        super.clearImages();

        this.posMangaAnimeButtons();
        double[] listS = computeListSettings();
        list.setupList(listS[0], listS[1]);
        this.positionList();
        super.getMain().setWidth(width + 16);
        super.getMain().setHeight(super.getmHeight() + cHeight + 39);
        genrePanel.repositionHelper(cHeight, width);
        

    }

    public void posMangaAnimeButtons() {
        //[ totalButtonsW ] = [Manga] + [Anime] + offset*2
        double cX = super.getCustomizeX();
        double totalButtonsW = (Settings.addGenreWidth * 2) + Settings.offset * 2;
        double y = super.getmHeight() / 2 - (Settings.addGenreHeight / 2);
        addGenre[0].setLayoutX(cX - totalButtonsW);
        addGenre[0].setLayoutY(y);

        addGenre[1].setLayoutX(cX - totalButtonsW + Settings.offset + Settings.addGenreWidth);
        addGenre[1].setLayoutY(y);
    }

    public void listToFront(){
        list.toFront();
    }
    
    public double[] computeListSettings() {
        double cX = super.getCustomizeX();
        double totalButtonsW = (Settings.addGenreWidth * 2) + Settings.offset * 2;

//        double listH = super.getHeightT() * Settings.listHeightScale;
        double listH = super.getcHeight() * Settings.listHeightScale;
        double listW = super.getWidthT() - (cX - totalButtonsW) - Settings.listWidthOffset;
        if (list != null) {
            list.setupList(listH, listW);
        }

        return new double[]{listH, listW};
    }

    public void positionList() {
        // window width - list width - list width * scale
        double x = addGenre[0].getLayoutX();
        // [(center height / 2 ) - (list height/2)] [CENTERS THE LISTVIEW]
        double y = (super.getcHeight() / 2.0) - (list.getWidthT() / 2.0);

        list.setListPosition(x, y);
    }

    public void addManga() {
        addWindow.setTitle("Add Manga");
        addWindow.editLabel(1, "Chapter: ");
        currentGenre = Type.MANGA;
        openNewWindow(addWindow.getMainScene());
    }

    public void addAnime() {
        addWindow.setTitle("Add Anime");
        addWindow.editLabel(1, "Episode: ");
        currentGenre = Type.ANIME;
        openNewWindow(addWindow.getMainScene());
    }

    public void insertNewGenre() {
        boolean valid = InfoManager.checkInputs(addWindow);
        if (!valid) {
            return;
        }
        //0 - Name
        //1 - Ep/chapter
        //2 - Rating
        String texts[] = addWindow.getLabelTexts().split(",");
        Genre newGenre = null;

        String name = texts[0];

        int pos = Integer.parseInt(texts[1]);

        int rating = Integer.parseInt(texts[2]);

        if (currentGenre == Type.MANGA) {
            newGenre = new Genre(name, pos, rating, "", Type.MANGA);
        } else {
            newGenre = new Genre(name, pos, rating, "", Type.ANIME);
        }

        newGenre.setWidth((int) 800);
        newGenre.setcHeight((int) 400);
        newGenre.setmHeight((int) 60);
        newGenre.setfSize((short) this.getfSize());
        newGenre.setfStyle(this.getfStyle());

        list.addGenre(newGenre);
    }

    public void setForSave(){
        list.setCollection();
    }

    public GenrePanel getGenrePanel() {
        return genrePanel;
    }

    public Scene getGenreScene() {
        return genreScene;
    }

    public CollectionManager getList() {
        return list;
    }

}
