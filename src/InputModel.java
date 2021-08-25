
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author JulioL
 */
public class InputModel extends Panel{
    
    private short rows;
    private Input[] inputs;
    
    private short inputSpacing = 20;
    private int labelWidth = 150;
    private VBox model;
    
    private Button modelSelect;
    
    public InputModel(String title, int cHeight, int width, int mHeight, short rows, String fStyle, short fSize) {
        super(cHeight, width, mHeight, fStyle, fSize);
        super.setTitle(title);
        super.nullStage();
        //
        this.rows = rows;
        inputs = new Input[rows];
        model = new VBox();
//        modelScene = new Scene(this);
        
        //setup main select button
        modelSelect = new Button(Settings.mainBText);
        modelSelect.prefHeight(Settings.selectHeight);
        modelSelect.setPrefWidth(Settings.selectWidth);
       
        
        Color color = super.getTextColor();
        for(int i =0; i < rows; i++){
            String defaultName = Integer.toString(i + 1);
            inputs[i] = new Input(defaultName, fStyle, fSize, color, this.inputSpacing);
            inputs[i].getText().setPrefWidth(this.labelWidth);
            inputs[i].setAlignment(Pos.CENTER_LEFT);
            model.getChildren().add(inputs[i]);
        }
        super.addToCenter(model, Settings.modelX, Settings.modelY);
        
        double selectX = (super.getWidthT()/2.0) - (Settings.selectWidth/2.0);
        double selectY = super.getcHeight() * Settings.selectYScale;
        super.addToCenter(modelSelect, selectX, selectY);
    }
    
    public String getTextAt(int row){
        if(row < 0 || row >= rows){
            return "";
        }
        return inputs[row].toString();
    }
    
    public void setLabelFontSize(int fSize){
         for(int i =0; i < rows; i++){
            inputs[i].setFontSize(fSize);
        }
    }
    
    public void setGenre(Genre genre){
        inputs[0].setLabelText(genre.getTitle());
        inputs[1].setLabelText(Integer.toString(genre.getPos()));
        inputs[2].setLabelText(Integer.toString(genre.getRating()));
    }
    
    public boolean renameRows(String[] names){
        if(names.length != rows ){
            return false;
        }
        for(int i =0; i < names.length; i++){
            inputs[i].setText(names[i]);
        }
        return true;
    }

    
    public String getLabelTexts(){
        String texts = "";
        for(int i =0; i < rows; i++){
            texts += inputs[i];
            if((i + 1) != rows){
                texts += ",";
            }
        }
        return texts;
    }
    
    public boolean editLabel(int index, String newLabel){
        
        if(index >= rows || index < 0){
            return false;
        }
        
        inputs[index].setText(newLabel);
        
        return true;
    }

    public Button getModelSelect() {
        return modelSelect;
    }
    
    public void shiftDown(double amount){
        model.setLayoutY(model.getLayoutY() + amount);
    }
    
    public void shiftRight(double amount){
        model.setLayoutX(model.getLayoutX() + amount);
    }
    
    public void setLabelWidth(int width){
        this.labelWidth = width;
         for(int i =0; i < rows; i++){
            inputs[i].getText().setPrefWidth(this.labelWidth);
        }
        
    }
    
    public void setInputSpacing(short inputSpacing){
        this.inputSpacing = inputSpacing;
        for(int i =0; i < rows; i++){
            inputs[i].setSpacing(inputSpacing);
        }
    }
    
    public void setError(int row, String msg){
        if(row >= rows)
            return;
        inputs[row].errorMsg(msg);
    }
    
    public double getInputWidth(){
        return inputs[0].getWidth();
    }
    
    public void setTextFieldWidth(int width){
        for(int i =0; i < inputs.length; i++){
            inputs[i].setTFWidth(width);
        }
    }
    
}
