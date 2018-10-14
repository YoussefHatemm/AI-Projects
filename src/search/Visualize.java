package search;

import processing.core.PApplet;

public class Visualize  extends PApplet {


    public Visualize() {

    }

    public static void main(String[] args) {
        PApplet.main("search.Visualize", args);
    }

    public void settings() {
        size(600,480);
    }

    public void setup() {
        background(255);
    }

    public void draw() {
       stroke(0);
       strokeWeight(2);

       int x = 50;

       while (x < width) {
           line(x,0,x,height);
           x += 50;
       }

       int y = 50;

       while (y < height) {
           line(0,y, width, y);
           y+=50;
       }
    }
}
