import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Created by Dylan on 12/2/2017.
 */
public class MyMenuBar extends MenuBar {

    private Menu file;
    private MenuItem genNewMaze;

    public MyMenuBar(MyCanvas canvas){

        this.file = new Menu("File");
        this.genNewMaze = new MenuItem("Generate New Maze");
        this.file.getItems().addAll(this.genNewMaze);//,new MenuItem("Nuke Japan (again)"));

        this.genNewMaze.setOnAction(e->{
            canvas.genMaze(30,30);
        });

        this.getMenus().add(this.file);
    }
}
