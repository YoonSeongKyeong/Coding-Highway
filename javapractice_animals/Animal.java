package javapractice_animals;

public abstract class Animal {
    private String picture;//save picture address && this is private instance value
    private String food;//food for animal && this is private instance value
    int hunger;//how hungry it is
    private int[] location;//location now positioning {x, y}
    Animal() {//when make instance with no argument
        picture = "";
        food = "Default Food";
        hunger = 0;
        location = new int[2];
        location[0] = 0;
        location[0] = 0;
    }
    Animal(int locationX, int locationY) {//when describe location of instance while building
        picture = "";
        food = "Default Food";
        hunger = 0;
        location = new int[2];
        location[0] = locationX;
        location[0] = locationY;
    }
    private void dance() {//this is private method
        System.out.println("It's Dance time!!!(and It is private method)");
    }
    public void setFood(String foodIn) {//access to private instance value
        food = foodIn;
    }
    public String getFood() {//access to private instance value
        return food;
    }
    public void setPicture(String pictAdress) {//access to private instance value
        picture=pictAdress;
    }
    public void danceTime() {//access to private method
        dance();
    }
    public abstract void makeNoise();
    public abstract void eat();//abstract == no implementing in this level (exist only method head for control)
    public abstract void sleep();
    public abstract void roam();
    
}