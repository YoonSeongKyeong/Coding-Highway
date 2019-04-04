package javapractice_animals;

public class Wolf extends Canine {
    @Override
    public void makeNoise() {
        System.out.println("(Wolf noise)aWWWWWWWWWWWWWWW");
    }
    @Override 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Wolf eat)ooommmmmmMM");
    }
    @Override
    public void sleep() {
        System.out.println("(Wolf sleep) ZZZZZZzzzzzzz");
    }
}