public class Launcher {
    public static void main(String[] args) {
        Animal [] animals = new Animal[7];
        animals[0] = new Cat();
        animals[1] = new Cat();
        animals[2] = new Lion();
        animals[3] = new Dog();
        animals[4] = new Hippo();
        animals[5] = new Tiger();
        animals[6] = new Wolf();
        Vet hunter = new Vet();
        PetOwner owner = new PetOwner();
        for(Animal animalElement : animals) {
            animalElement.eat();
            animalElement.roam();
            animalElement.makeNoise(); 
            animalElement.sleep();
            owner.givemeal(animalElement);
            hunter.giveShot(animalElement);
            System.out.println("---------------------------------");
        }
        System.out.println("New Test for Getting and Setting Food");
        System.out.println(animals[0].getFood());
        System.out.println("Set Food as My new Food!");
        animals[0].setFood("My new Food!");
        System.out.println(animals[0].getFood());
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
