public abstract class Animal {
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
        System.out.println("(Animal noise)AAA~AAAA!");
    } 
    public abstract void eat();
    public abstract void sleep();
    public abstract void roam();
    
}