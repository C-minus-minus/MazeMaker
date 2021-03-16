import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by Dylan on 12/2/2017.
 */
public class MazeMaker extends Application {

    private MenuBar myMenuBar;
    private MyCanvas myCanvas;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //  create layout items
        this.myCanvas = new MyCanvas();
        this.myMenuBar = new MyMenuBar(this.myCanvas);

        //  add menu bar to layout
        BorderPane layout = new BorderPane();
        layout.setTop(this.myMenuBar);

        //  add canvas to layout and make canvas resize to window
        Pane pane = new Pane(this.myCanvas);
        this.myCanvas.widthProperty().bind(pane.widthProperty());
        this.myCanvas.heightProperty().bind(pane.heightProperty());
        layout.setCenter(pane);

        //  set window to fill quarter of users screen when initialised
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(layout,screenSize.getWidth()/2,screenSize.getHeight()/2);

        //  add theme
        this.setUserAgentStylesheet(STYLESHEET_CASPIAN);

        //  set up and show window to user
        primaryStage.setTitle("Maze Maker 1.0");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e->System.exit(0));
        primaryStage.show();
    }
}
