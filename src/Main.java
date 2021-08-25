
import javafx.application.Application;

import javafx.stage.Stage;

/**
 *
 * @author JulioL
 */
public class Main extends Application {
    static MainMenu mainMenu;
    @Override
    public void start(Stage stage) throws Exception{
        mainMenu = InfoManager.loadFile("library.txt");
        mainMenu.switchToMainScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void stop(){
        InfoManager.saveFile(mainMenu);
    }

}
