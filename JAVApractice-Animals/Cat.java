public class Cat extends Feline {
    public void makeNoise() {
        System.out.println("MEOW!");
    } 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("MMmm");
    }
}