public class Cat extends Feline implements Pet {
    @Override
    public void makeNoise() {
        System.out.println("(Cat noise)MEOW!");
    }
    @Override 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Cat eat)MMmm");
    }
    @Override
    public void sleep() {
        System.out.println("(Cat sleep) zzzzzzz");
    }
    @Override
    public void befriendly() {
        System.out.println("(Cat) Be friendly to owner");
    }
    @Override
    public void play() {
        System.out.println("(Cat) Play with owner");
    }
}