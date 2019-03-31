public class Lion extends Feline {
    @Override
    public void makeNoise() {
        System.out.println("(Lion noise)GRRRRRGGGGG!");
    }
    @Override 
    public void eat() {// we can check if meal is what this animal would eat
        hunger=0;
        System.out.println("(Lion eat)GGRRGGGUMMMMMMM");
    }
    @Override
    public void sleep() {
        System.out.println("(Lion sleep) zsszZZZzzzzzz");
    }
}