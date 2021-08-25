

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *This class is responsible for book keeping a ListView of Genres
 * 
 * 
 * @author JulioL
 */
public class CollectionManager {
    
    private MainMenu display;
    
    private ListView<Genre> listNode;
    private double listHeight;
    private double listWidth;
    
    private Button select;
    private Button delete;
    private double buttonHeight = 20;
    private double buttonWidth;
    private double spacing = .05;
    
    private double toolOffset = 5;
    
    private TextField search;
    private double searchHeight = 20;
    private double searchWidth;
    private ObservableList<Genre> collection;
    private ObservableList<Genre> temp;
    
    
    public CollectionManager(MainMenu parent, double listHeight, double listWidth){
        this.display = parent;
        listNode = new ListView();
        listNode.setOnMouseClicked(e -> parent.closeOtherWindow());
        collection = listNode.getItems();
        temp = FXCollections.observableArrayList();
        select = new Button("Select");
        select.setOnMousePressed(e -> setSelectAction());
        delete = new Button("Delete");
        delete.setOnMousePressed(e -> removeSelectedGenre());
        search = new TextField();
        search.setOnKeyReleased(e -> searchList());
        setupList(listHeight, listWidth);
    }

    public ObservableList<Genre> resetGenreList(){
        if(!temp.isEmpty()){
            collection.addAll(temp);
        }
        return collection;
    }
    
    public void toFront(){
        listNode.toFront();
        select.toFront();
        delete.toFront();
        search.toFront();
    }
    
    public void setSelectAction(){
        int numSel = listNode.getSelectionModel().getSelectedItems().size();
        if(numSel <= 0){
            return;
        }
        display.getGenrePanel().
                setGenre(listNode.getSelectionModel().getSelectedItem());
        display.switchToOtherScence(display.getGenreScene());
    }
    
    public void searchList(){
        String input = search.getText();
        
        if(input.isEmpty()){
            temp.clear();
            listNode.setItems(collection);
            return;
        }
        
        temp.clear();
        for(int i =0; i < collection.size(); i++ ){
            String name = collection.get(i).toString().toLowerCase();
            
            if(name.contains(input)){
                temp.add(collection.get(i));
            }
        }
        
        listNode.setItems(temp);
    }
    
    public void addToPanel(Pane panel){
        panel.getChildren().addAll(listNode, select, delete, search);
    }
    
    public void setupList(double listHeight, double listWidth){
         this.listHeight = listHeight;
         this.listWidth = listWidth;
         this.listNode.setPrefHeight(listHeight);
         this.listNode.setPrefWidth(listWidth);
         this.buttonWidth = (listWidth/2.0) /2 * (1 - spacing);
         this.searchWidth = (listWidth/2.0);
         
         setToolsWidthHeight();
    }
    
    public void removeSelectedGenre(){
        Genre temp = listNode.getSelectionModel().getSelectedItem();
        if(temp == null){
            return;
        }
        collection.remove(temp);
    }
    
    public void setToolsWidthHeight(){
        delete.setPrefHeight(buttonHeight);
        delete.setPrefWidth(buttonWidth);
        select.setPrefHeight(buttonHeight);
        select.setPrefWidth(buttonWidth);
        search.setPrefWidth(searchWidth);
        search.setPrefHeight(searchHeight);
    }
    
    public void setListPosition(double x, double y){
        positionNode(listNode, x, y);
        
        double toolY = listHeight + y + toolOffset;
        
        double selectX = x;
        double deleteX = x + buttonWidth + (buttonWidth *spacing);
        double searchX = x + (listWidth/2.0);
        positionNode(select, selectX, toolY);
        positionNode(delete, deleteX, toolY);
        positionNode(search, searchX, toolY);
        
    }

    public void setCollection(){
        listNode.setItems(collection);
    }
    
    private void positionNode(Node node, double x, double y){
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    public double getToolOffset() {
        return toolOffset;
    }
    
    
    public void addGenre(Genre genre){
        collection.add(genre);
    }
    
    public boolean removeGenre(Genre genre){
        return collection.remove(genre);
    }
    
    //boolean check?
    public void changeListBackGround(){
        //TODO
    }

    public ListView getListNode() {
        return listNode;
    }

    public ObservableList<Genre> getCollection() {
        return collection;
    }
    
    public void setXY(double x, double y){
        listNode.setLayoutX(x);
        listNode.setLayoutY(y);
    }

    public double getListHeight() {
        return listHeight;
    }

    public double getListWidth() {
        return listWidth;
    }
    
    public double getWidthT(){
        return listHeight + toolOffset + buttonHeight;
    }

    public Button getSelect() {
        return select;
    }
    
    public int getNumItems(){
        return collection.size();
    }
    
    
}
