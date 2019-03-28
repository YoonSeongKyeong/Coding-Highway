public class testLauncher {
    public static void main(String[] args) {
        child kid1 = new child();
        kid1.jump();// child method success test
        child kid2 = new child();
        child kid3 = new child();
        child kid4 = new child();
        Parent parent1 = new Parent();
        Parent parent2 = new Parent();
        // Private int of parent
        System.out.println("Private int of parent1");
        System.out.println("set parent1 val to 3");
        parent1.setMoney(3);
        int out1 = parent1.getMoney();
        System.out.println(out1);

        System.out.println("Private int of parent2");
        System.out.println("set parent1 val to 2");
        parent2.setMoney(2);
        int out2 = parent2.getMoney();
        System.out.println(out2);

        System.out.println("Private int of parent1");
        out1 = parent1.getMoney();
        System.out.println(out1);

        System.out.println("Private int of child1");
        System.out.println("set child1 val to 5");
        kid1.setMoney(5);
        int out3 = kid1.getMoney();
        System.out.println(out3);

        System.out.println("Private int of child2");
        System.out.println("set child2 val to 7");
        kid2.setMoney(7);
        out3 = kid2.getMoney();
        System.out.println(out3);
        out3 = kid1.getMoney();
        System.out.println("Private int of child1");
        System.out.println(out3);

        System.out.println("Private int of child3");
        System.out.println("set child3 val to 9");
        kid3.setMoney(9);
        out3 = kid3.getMoney();
        System.out.println(out3);

        System.out.println("Private int of parent1");
        out1 = parent1.getMoney();
        System.out.println(out1);

        out3 = kid1.getMoney();
        System.out.println("Private int of child1");
        System.out.println(out3);

        System.out.println("Private int of child4");
        System.out.println("set child4 val to 6");
        kid4.setMoney(6);
        out3 = kid4.getMoney();
        System.out.println(out3);

        System.out.println("Private int of child3");
        out3 = kid3.getMoney();
        System.out.println(out3);

        System.out.println("Private int of parent1");
        out1 = parent1.getMoney();
        System.out.println(out1);

// check If money, honey is accessible -> no accessible field error
        // out1 = kid1.money;
        // System.out.println(out1);
        // kid1.money=1;
        // out1 = kid1.money;
        // System.out.println(out1);

        System.out.println("Here is Test About private method on Parent --- Accessing");
        kid1.makeNoise();
    }
}