import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebHistory;
import javafx.util.Duration;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dylan on 12/2/2017.
 */
public class MyCanvas extends Canvas {

    private boolean[][] maze;
    private boolean[][] visit2;
    private HashMap<Point,Point> path;
    private Point current;

    private GraphicsContext pen;

    private Timeline timer;

    public MyCanvas(){

        this.pen = this.getGraphicsContext2D();
        this.pen.setLineWidth(10);

        this.timer = new Timeline(new KeyFrame(Duration.millis(10),e ->{this.step();this.draw();}));
        this.timer.setCycleCount(Animation.INDEFINITE);
    }

    public void genMaze(int width,int height){

        this.maze = new boolean[width][height];
        this.visit2 = new boolean[width][height];
        this.path = new HashMap<>();
        for(int i=0;i<this.maze.length;i++){
            for(int a=0;a<this.maze[0].length;a++){
                this.maze[i][a] = false;
                this.visit2[i][a] = false;
            }
        }
        this.current = new Point(0,0);
        timer.play();
    }

    private void step(){

        //  mark cell as visited
        this.maze[(int)this.current.getX()][(int)this.current.getY()] = true;

        //  add all non visited neighbors to list
        ArrayList<Point> list = new ArrayList<>();
        if(this.current.getX()>0){
            if(!this.maze[(int)this.current.getX()-1][(int)this.current.getY()]){
                list.add(new Point((int)this.current.getX()-1,(int)this.current.getY()));
            }
        }
        if(this.current.getX()<this.maze.length-1){
            if(!this.maze[(int)this.current.getX()+1][(int)this.current.getY()]){
                list.add(new Point((int)this.current.getX()+1,(int)this.current.getY()));
            }
        }
        if(this.current.getY()>0){
            if(!this.maze[(int)this.current.getX()][(int)this.current.getY()-1]){
                list.add(new Point((int)this.current.getX(),(int)this.current.getY()-1));
            }
        }
        if(this.current.getY()<this.maze[0].length-1){
            if(!this.maze[(int)this.current.getX()][(int)this.current.getY()+1]){
                list.add(new Point((int)this.current.getX(),(int)this.current.getY()+1));
            }
        }

        //  if all neighbor cells have been visited back track
        if(list.size()==0){

            this.visit2[(int)this.current.getX()][(int)this.current.getY()] = true;

            this.current = this.path.get(this.current);

            //  if this is the starting cell end the algorithm
            if(this.current.getX()==0&&this.current.getY()==0){
                System.out.println("Maze Complete!");
                timer.stop();
                this.draw();
            }
            return;
        }

        //  randomly select one neighbor that hasn't been visited
        int rand = (int)(Math.random()*list.size());

        //  add the connection between the current cell and the selected neighbor to the path
        this.path.put(list.get(rand),this.current);

        //  set the selected cell as the new neighbor
        this.current = list.get(rand);
    }

    private void draw(){

        //  clear canvas
        this.pen.setFill(Color.BLACK);
        this.pen.fillRect(0,0,this.getWidth(),this.getHeight());

        //  draw maze
        double widthScale = this.getWidth()/this.maze.length;
        double heightScale = this.getHeight()/this.maze[0].length;
        double widthOffSet = widthScale/2;
        double heightOffSet= heightScale/2;
        this.pen.setLineWidth(Math.min(widthScale,heightScale)/2);
        for(Map.Entry<Point,Point> i:this.path.entrySet()){

            if(this.visit2[(int)i.getKey().getX()][(int)i.getKey().getY()]){
                this.pen.setStroke(Color.BLUE);
            }else this.pen.setStroke(Color.RED);

            this.pen.strokeLine( i.getKey().getX()*widthScale+widthOffSet
                                ,i.getKey().getY()*heightScale+heightOffSet,
                                 i.getValue().getX()*widthScale+widthOffSet,
                                 i.getValue().getY()*heightScale+heightOffSet);
        }

        //  color start and end points orange
        this.pen.setFill(Color.ORANGE);
        this.pen.fillRect(widthOffSet-pen.getLineWidth()/2,
                        heightOffSet-pen.getLineWidth()/2,
                        pen.getLineWidth(),
                        pen.getLineWidth());
        this.pen.fillRect(this.getWidth()-widthOffSet-pen.getLineWidth()/2,
                this.getHeight()-heightOffSet-pen.getLineWidth()/2,
                pen.getLineWidth(),
                pen.getLineWidth());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
