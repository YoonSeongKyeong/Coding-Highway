package javapractice_animals;

public class Hippo extends Animal {
    @Override
    public void makeNoise() {
        System.out.println("(Hippo noise)HIhiPipi!");
    }
    @Override 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Hippo eat)MBBMBFRRRM");
    }
    @Override
    public void sleep() {
        System.out.println("(Hippo sleep) ZZZZzzzzzzz");
    }
    @Override
    public void roam() {
        System.out.println("(Hippo roam)  aaoaoaooaaa");
    }
}