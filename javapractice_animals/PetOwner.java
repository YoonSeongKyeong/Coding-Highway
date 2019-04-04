package javapractice_animals;

public class PetOwner {
	public void givemeal(Pet pet) {
        System.out.println("(PetOwner Gave meal)");
        pet.eat();
	}
}