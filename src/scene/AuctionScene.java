package scene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class AuctionScene extends Scene {
    private static Pane root;

    static {
        root = new Pane();
    }

    public AuctionScene() {
        super(root);
        root.getChildren().add(new Button("okay"));
    }
}
