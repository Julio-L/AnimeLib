
import javafx.scene.paint.Color;

/**
 *
 * @author JulioL
 */
public class Settings {

    static String colorPre = "-fx-background-color: ";
    static String fStyle = "contrast";
    static String bColor = colorPre + "black";
    static short fSize = 40;

    static Color dividerC = Color.WHITE;
    static int dividerStroke = 5;
    static Color textColor = Color.WHITE;
    
    //panel settings
    static double defaultTitleX = 10;
    static double defaultYScale = .60;
    static short openCHeight = 30;
    static short openCWidth = 100;

    //customization
    static short customizeMHeight = 55;
    static int customizeCHeight = 320;
    static int customizeWidth = 300;
    static short customizeFSize = 30;
    static short customNumRows = 2;
    static int shiftDown = 170;
    static short customSpacing = 10;
    static short customLabelW = 60;
    static int labelFSize = 20;
    static String[] custTools = {"Height", "Width"};
    static int lockWidth = 70;
    static int lockHeight = 25;
    static double lockXScale = .88;
    static double addInsertX = 20;
    static double addInsertY = 20;

    //Main menu settings//
    static String TITLE = "Manga/Anime";
    //scale used to create the height of the listview (center pane height * scale)
    static double listHeightScale = .90;

    //scale used to create the width of the listview (center pane width * scale)
    static double listWidthScale = .2;
    static short listWidthOffset = -5;

    //main menus add manga/anime button
    static short addGenreHeight = 30;
    static short addGenreWidth = 70;
    static short offset = 5;
    //main menus add manga/anime popup window
    static String addTitle = "Add Anime/Manga";
    static int addWidth = 350;
    static int addCHeight = 220;
    static short addMHeight = 50;
    static short addFSize = 35;
    static short numRows = 3;
    static String[] addTitles = {"Name: ", "Episode: ", "Rating: "};

    //Genre panel settings
    static int buttonHeight = 30;
    static int buttonWidth = 55;
    static int buttonOffset = 8;
    static short genrePFSize = 35;
    
    //updateModel Settings
    static int updateCHeight = 220;
    static int updateWidth = 350;
    static int updateMHeight = 50;
    static short updateRows = 3;
    static short updateFSize = 25;

    //InputModel settings
    static short modelX = 5;
    static short modelY = 10;
    static String mainBText = "Select";
    static int selectWidth = 100;
    static int selectHeight = 100;
    static double selectYScale = 0.8;
    
    //image component
    static String ImgCTitle = "Image Tools";
    static short toolRows = 2;
    static int ImgCHeight = 200;
    static short ImgCMHeight = 40;
    static int ImgCWidth = 270;
    static short ImgCFSize = 25;
    static String[] toolNames = {"Height: ", "Width: "};
    static int ImgCLabelW = 100;
    static short ImgCSpacing = 5;
    static int ImgCBW = 60;
    static int ImgCBH = 18;
    
    //TextAComponent 
    static int TextACHeight = 300;
    static int TextAWidth = 400;
    static int TextAMHeight = 50;
    static int TextAFSize = 25;
   

}
