package testForPrivateValueInheritance;

public class Parent {
    private int money;
    private int honey;
    private void noise() {
        System.out.println("Hey, I'm private method");
    }
    public void makeNoise() {
        noise();
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int num) {
        money = num;
    }
}
