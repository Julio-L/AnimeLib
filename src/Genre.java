
import java.util.ArrayList;

/**
 *
 * @author JulioL
 */
public class Genre {

    private String title;
    private int rating;
    private String desc;
    private Type medium;
    private int pos;

    //properties
    private ArrayList<ImageComponent> images;
    private int cHeight;
    private int width;
    private int mHeight;
    private String fStyle;
    private short fSize;
    private boolean isLocked;
    private boolean New = true;

    public Genre(String title, int pos, int rating, String desc, Type medium) {
        this.title = title;
        this.rating = rating;
        this.desc = desc;
        this.medium = medium;
        this.pos = pos;

        images = new ArrayList();
    }

    public Genre(String title, int rating, String desc, int pos) {
        this.title = title;
        this.rating = rating;
        this.desc = desc;
        this.medium = null;
        this.pos = pos;
    }
    
    public void update(String title, int pos, int rating){
        this.title = title;
        this.pos = pos;
        this.rating = rating;
    }

    public String saveFormat() {

        int count = 0;
        for(int i =0; i < desc.length(); i++){
            if(desc.charAt(i) == '\n') count +=1;
        }

        if(count >0) count+=1;

//        String settings = String.format("%s,%d,%s,%s,%s,%d,%d,%d,%s,%d,%d,%d,%d",
//                title, pos, rating, desc, medium.toString(), cHeight, width, mHeight,
//                fStyle, fSize, isLocked ? 1 : 0, New ? 1 : 0, images.size());

        String settings = String.format("%s,%d,%s,%s,%d,%d,%d,%s,%d,%d,%d,%d,%d%s%s",
                title, pos, rating, medium.toString(), cHeight, width, mHeight,
                fStyle, fSize, isLocked ? 1 : 0, New ? 1 : 0, images.size(), count,(desc.length()==0)?"":"\n",desc);

        return settings;
    }

    public void loadSettings(int cHeight, int width, int mHeight,
            String fStyle, short fSize, boolean isLocked, boolean isNew) {
        this.cHeight = cHeight;
        this.width = width;
        this.mHeight = mHeight;
        this.fStyle = fStyle;
        this.fSize = fSize;
        this.isLocked = isLocked;
        this.New = isNew;
    }
    
    public void clearImages(){
        this.images.clear();
    }
    
    public void deleteImage(ImageComponent img){
        images.remove(img);
    }

    public ImageComponent getAt(int index) {
        return images.get(index);
    }

    public int getNumImages() {
        return images.size();
    }

    public void addImage(ImageComponent image) {
        images.add(image);
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }

    public Type getMedium() {
        return medium;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setMedium(Type medium) {
        this.medium = medium;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String toString() {
        return this.title.toUpperCase()
                + (medium == Type.ANIME ? "(ANIME)" : "(MANGA)");
    }

    public ArrayList<ImageComponent> getImages() {
        return images;
    }

    public int getcHeight() {
        return cHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getmHeight() {
        return mHeight;
    }

    public String getfStyle() {
        return fStyle;
    }

    public short getfSize() {
        return fSize;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public boolean IsNew() {
        return New;
    }

    public void setcHeight(int cHeight) {
        this.cHeight = cHeight;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public void setfStyle(String fStyle) {
        this.fStyle = fStyle;
    }

    public void setfSize(short fSize) {
        this.fSize = fSize;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setNew(boolean isNew) {
        this.New = isNew;
    }

}
