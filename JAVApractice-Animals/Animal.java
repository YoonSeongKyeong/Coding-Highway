import java.util.*;

public class Animal {
    private String picture;//save picture address
    private String food;//what it eats
    protected int hunger;//how hungry it is
    private int[] boundaries;//boundary for roaming {0~x, 0~y}
    private int[] location;//location now positioning {x, y}
    Animal(){
        picture = "";
        food = "all";
        hunger = 0;
        boundaries = new int[2];
        location = new int[2];
        boundaries[0] = 0;
        boundaries[1] = 0;
        location[0] = 0;
        location[0] = 0;
    }
    Animal(int boundaryX, int boundaryY, int locationX, int locationY) {
        picture = "";
        food = "all";
        hunger = 0;
        boundaries = new int[2];
        location = new int[2];
        boundaries[0] = boundaryX;
        boundaries[1] = boundaryY;
        location[0] = locationX;
        location[0] = locationY;
    }
    public void setPicture(String pictAdress) {
        picture=pictAdress;
    }
    public void makeNoise() {
        System.out.println("AAA~AAAA!");
    } 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("MMMMMM~~!");
    }
    public void sleep() {
        System.out.println("zzzZZZZZ");
    }
    public void roam() {
    //     Random generator = new Random(); Not now - inheritance issue
    //     int xMove;
    //     int yMove;
    //     int nextX;
    //     int nextY;
    //     boolean inBound=false;
    //     while (!inBound) {
    //         xMove=generator.nextInt(11)-5;//-5~5
    //         yMove=generator.nextInt(11)-5;//-5~5
    //         nextX=location[0]+xMove;
    //         nextY=location[1]+yMove;
    //         if (nextX>=0&&nextX<=boundaries[0]&&nextY>=0&&nextY<=boundaries[1]) {
    //             inBound=true;
    //             location[0] = nextX;
    //             location[1] = nextY;
    //         }
    //     }
    //     System.out.println("go to "+location[0]+", "+location[1]);
        System.out.println("default Moving");
    }
    
}