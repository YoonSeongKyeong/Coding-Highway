public class Dog extends Canine implements Pet {
    @Override
    public void makeNoise() {
        System.out.println("(Dog noise)WRafWraff");
    }
    @Override 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Dog eat)mmmMMMmm");
    }
    @Override
    public void sleep() {
        System.out.println("(Dog sleep) zzzZZzzzzzz");
    }
    @Override
    public void befriendly() {
        System.out.println("(Dog) Be friendly to owner");
    }
    @Override
    public void play() {
        System.out.println("(Dog) Play with owner");
    }
}