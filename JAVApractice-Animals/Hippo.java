public class Hippo extends Animal {
    public void makeNoise() {
        System.out.println("(Hippo noise)HIhiPipi!");
    } 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Hippo eat)MBBMBFRRRM");
    }
    public void sleep() {
        System.out.println("(Hippo sleep) ZZZZzzzzzzz");
    }
    public void roam() {
        System.out.println("(Hippo roam)  aaoaoaooaaa");
    }
}