public class Wolf extends Canine {
    public void makeNoise() {
        System.out.println("(Wolf noise)aWWWWWWWWWWWWWWW");
    } 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Wolf eat)ooommmmmmMM");
    }
    public void sleep() {
        System.out.println("(Wolf sleep) ZZZZZZzzzzzzz");
    }
}