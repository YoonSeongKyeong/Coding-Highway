package javapractice_animals;

public class Tiger extends Feline {
    @Override
    public void makeNoise() {
        System.out.println("(Tiger noise)WOOOOOOWWOOO");
    }
    @Override 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Tiger eat)ttmttttt");
    }
    @Override
    public void sleep() {
        System.out.println("(Tiger sleep) ZZZZZZZZzzzzzzzzz");
    }
}