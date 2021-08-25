
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author JulioL
 */
public class InfoManager {

    //Checking inputs for adding and updating anime/manga
    public static boolean checkInputs(InputModel model) {
        boolean valid = true;
        String title = model.getTextAt(0);
        String pos = model.getTextAt(1);
        String rating = model.getTextAt(2);
        if (title.length() <= 0) {
            valid = false;
            model.setError(0, "ERROR: Invalid Title");
        }
        int test = -1;
        try {
            test = Integer.parseInt(pos);
        } catch (NumberFormatException e) {
            model.setError(1, "ERROR: Invalid Number");
            valid = false;
        };

        try {
            test = Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            model.setError(2, "ERROR: Invalid Number");
            valid = false;
        };
        
        return valid;

    }

    public static void saveFile(MainMenu mainMenu) {
        try {
            mainMenu.setForSave();
            BufferedWriter bw = new BufferedWriter(new FileWriter("library.txt"));

            bw.write(mainMenu.saveFormat());

            List<ImageComponent> mainImgs = mainMenu.getImages();

            for (int i = 0; i < mainImgs.size(); i++) {
                bw.newLine();
                bw.write(mainImgs.get(i).saveFormat());
            }

            bw.newLine();

            ObservableList<Genre> items = mainMenu.getList().resetGenreList();

            for (int i = 0; i < items.size(); i++) {
                Genre g = items.get(i);
                bw.write(g.saveFormat());

                for (int r = 0; r < g.getNumImages(); r++) {
                    bw.newLine();
                    bw.write(g.getAt(r).saveFormat());
                }

                if (i + 1 != items.size()) {
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException ex) {

        }
    }

    public static MainMenu loadFile(String url) {
        MainMenu mainMenu = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(url));

            //first line (mainmenu)
            String[] main = br.readLine().split(",");
            //0 - cHeight
            //1 - widthT
            //2 - mHeight
            //3 - fStyle
            //4 - fSize
            //5 - locked
            //6 - numImages
            //7 - numItems

            double cHeight = Double.parseDouble(main[0]);
            double widthT = Double.parseDouble(main[1]);
            double mHeight = Double.parseDouble(main[2]);
            String fStyle = main[3];
            short fSize = Short.parseShort(main[4]);
            boolean isLocked = main[5].equals("1");
            int numImages = Integer.parseInt(main[6]);

            int numItems = Integer.parseInt(main[7]);

            mainMenu = new MainMenu((int) cHeight, (int) widthT, (int) mHeight, fStyle, fSize);
            mainMenu.setlock(isLocked);

            double width;
            double height;
            double x;
            double y;
            for (int i = 0; i < numImages; i++) {
                String[] img = br.readLine().split(",");
                //img
                //0 - url
                //1 - width
                //2 - height
                //3 - x
                //4 - y
                width = Double.parseDouble(img[1]);
                height = Double.parseDouble(img[2]);
                x = Double.parseDouble(img[3]);
                y = Double.parseDouble(img[4]);

                ImageComponent image = new ImageComponent(mainMenu, img[0], true);
                image.loadSettings(width, height, x, y, isLocked);
                mainMenu.addCenterImage(image);
            }

            //load genres
            //0 - title
            //1 - pos
            //2 - rating
            //3 - medium
            //4 - cHeight
            //5 - width
            //6 - mheight
            //7 - fStyle
            //8 - fSize
            //9 - 0 or 1 (isLocked)
            //10 - 0 or 1 (New)
            //11 - numimages
            //12 - count
            int pos;
            int rating;
            String desc;
            int count;
            Type medium;
            boolean isNew;
            Genre genre;
            CollectionManager lst = mainMenu.getList();

            for (int i = 0; i < numItems; i++) {

                //get Genre
                String[] g = br.readLine().split(",");
                String title = g[0];
                pos = Integer.parseInt(g[1]);
                rating = Integer.parseInt(g[2]);
//                desc = g[3];
                medium = g[3].equals("MANGA") ? Type.MANGA : Type.ANIME;

                cHeight = Integer.parseInt(g[4]);
                widthT = Integer.parseInt(g[5]);
                mHeight = Integer.parseInt(g[6]);
                fStyle = g[7];
                fSize = Short.parseShort(g[8]);

                isLocked = g[9].equals("1");
                isNew = g[10].equals("1");

                numImages = Integer.parseInt(g[11]);

                count = Integer.parseInt(g[12]);

                desc = "";
                for(int c=0; c < count; c++){
                    desc += br.readLine();
                    if(c+1!=count) desc += "\n";
                }

                genre = new Genre(title, pos, rating, desc, medium);
                lst.addGenre(genre);

                genre.loadSettings((int) cHeight, (int) widthT, (int) mHeight, fStyle, fSize, isLocked, isNew);

                for (int r = 0; r < numImages; r++) {
                    String[] img = br.readLine().split(",");
                    //img
                    //0 - url
                    //1 - width
                    //2 - height
                    //3 - x
                    //4 - y
                    width = Double.parseDouble(img[1]);
                    height = Double.parseDouble(img[2]);
                    x = Double.parseDouble(img[3]);
                    y = Double.parseDouble(img[4]);

                    ImageComponent image = new ImageComponent(mainMenu.getGenrePanel(), img[0], false);
                    image.loadSettings(width, height, x, y, isLocked);
                    genre.addImage(image);

                }

            }
            mainMenu.listToFront();
            mainMenu.toFront();
            return mainMenu;

        } catch (FileNotFoundException ex) {
            return new MainMenu(450, 1150, 60, "contrast", (short) 40);
        } catch (IOException ex) {
        } catch (NullPointerException e) {
            return new MainMenu(450, 1150, 60, "contrast", (short) 40);
        }

        return mainMenu;
    }

}
