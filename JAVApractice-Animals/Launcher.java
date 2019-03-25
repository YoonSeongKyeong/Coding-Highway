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
    }
}
