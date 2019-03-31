import java.util.ArrayList; // import the ArrayList class

public class Launcher {
    public static void main(String[] args) {
        System.out.println("making Animal reference for contain sub-animal instances");
        System.out.println("Controling all different classes at once is power of Polymorphism!!");
        Animal [] animals = new Animal[6];  //making Animal reference for contain sub-animal instances
        animals[0] = new Wolf();            //Controling all different classes at once is power of Polymorphism!!
        animals[1] = new Cat();             //각각 다른 인스턴스들을 superclass의 메소드와 변수를 사용해서 한번에 사용할 수 있다.
        animals[2] = new Lion();
        animals[3] = new Dog();
        animals[4] = new Hippo();
        animals[5] = new Tiger();
        Vet treater = new Vet();             //(veterinarian) treats animals
        PetOwner owner = new PetOwner();     //new pet owner instance
        System.out.println("we can control all animal at once (can use superclass method and IV in instances)");
        System.out.println("");
        for(Animal animalElement : animals) {//we can control all animal at once (can use superclass method and IV in instances)
            animalElement.eat();
            animalElement.roam();
            animalElement.makeNoise(); 
            animalElement.sleep();
            owner.givemeal(animalElement);
            treater.giveShot(animalElement);
            System.out.println("---------------------------------");
        }
        System.out.println("This is Test for private-superclass-instance-variable-access");//superclass의 변수에 간접접근 가능
        System.out.println("");
        System.out.println("Food is private instance variable in Superclass Animal");
        System.out.println("Test for Getting and Setting Food");//Test for private-superclass-instance-variable-access
        System.out.println("Getting <instance variable>-Food of first animal");
        System.out.println(animals[0].getFood());
        System.out.println("Setting the Food as My new Food!");
        animals[0].setFood("My new Food!");
        System.out.println("Getting the Food Again, you can check it changed!!");
        System.out.println(animals[0].getFood());
        System.out.println("-> You can access to superclass private value by get set method unless no direct access is allowed");
        System.out.println("---------------------------------");
        
        System.out.println("This is another test for private-method-access");//superclass의 메소드에 간접접근 가능
        System.out.println("");
        System.out.println("dance is private method in Superclass Animal");
        System.out.println("Test for accessing dance");//Test for private-superclass-method-access
        animals[0].danceTime();
        System.out.println("access to dance and you can check it is possible!!");
        System.out.println("-> You can access to superclass method by get set method unless no direct access is allowed");
        System.out.println("---------------------------------");

        System.out.println("This is Test for Pet method");
        Pet[] petRef = new Pet[3];
        petRef[0] = new Cat();
        petRef[1] = new Dog();
        petRef[2] = new RoboDog();
        for(Pet petEle : petRef) {
            petEle.befriendly();
            petEle.play();
        }
        System.out.println("This is Test for Robot method");
        RoboDog roboDog = new RoboDog();
        roboDog.turnOn();
        roboDog.befriendly();
        roboDog.play();
        roboDog.turnOff();
    }
}
