public class RoboDog extends Robot implements Pet {
    @Override
    public void turnOn() {
        System.out.println("(RoboDog) Turning On!");
    }
    @Override
    public void turnOff() {
        System.out.println("(RoboDog) Turning Off!");
    }
    @Override
    public void befriendly() {
        System.out.println("(RoboDog) Be friendly to owner");
    }
    @Override
    public void play() {
        System.out.println("(RoboDog) Play with owner");
    }
    @Override
    public void eat() {
        System.out.println("(RoboDog) Charging!!!! ----- POWER!!");
    }
}