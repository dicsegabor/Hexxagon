package Graphics;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;

public class MenuToggleButton extends Button {

    private String activeGraphic, inactiveGraphic;
    private Boolean active;
    private GUIMaker maker = new GUIMaker();

    public MenuToggleButton(String activeGraphic, String inactiveGraphic){

        super();
        this.activeGraphic = activeGraphic;
        this.inactiveGraphic = inactiveGraphic;
    }

    public void changeState(){

        active = !active;

        if(active)
            setBackground(new Background(maker.imageToBackgroundImage(maker.loadImage("Active Button"))));

        else
            setBackground(new Background(maker.imageToBackgroundImage(maker.loadImage("Inactive Button"))));
    }
}
