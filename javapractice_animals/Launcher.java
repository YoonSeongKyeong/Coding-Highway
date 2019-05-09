package javapractice_animals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList; // import the ArrayList class
//메인함수 순서 설명
//1. 각자의 서브클래스 instance들을 Animal Array에 담아서 한번에 컨트롤(polymorphism)
//2. 그중에 Pet만 가능한 메소드는 Pet인지 확인하고 Pet으로 Casting해서 실행
//3. 서브클래스에서 슈퍼클래스의 private 변수와 메소드에 간접적으로 접근 가능한 것을 확인
//4. RoboDog 소개와 Pet list에 추가
//5. Animal Array에서 Pet만 따로 뽑아내고 싶을 때 사용하는 메소드 소개(extraction)
//6. Pet list에 담아서 Pet의 서브클래스들을 한번에 컨트롤(polymorphism again)

public class Launcher implements Serializable{
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws IOException {
        System.out.println("making Animal reference for contain sub-animal instances");
        System.out.println("Controling all different classes at once is the power of Polymorphism!!");
        Animal [] animals = new Animal[6];  //making Animal reference for contain sub-animal instances
        animals[0] = new Wolf();            //Controling all different classes at once is power of Polymorphism!!
        animals[1] = new Cat();             //각각 다른 인스턴스들을 superclass의 메소드와 변수를 사용해서 한번에 사용할 수 있다.
        animals[2] = new Lion();
        animals[3] = new Dog();
        animals[4] = new Hippo();
        animals[5] = new Tiger();
        Vet treater = new Vet();             //(veterinarian) treats animals
        PetOwner owner = new PetOwner();     //new pet owner instance


        System.out.println("1. we can control all animal at once (can use superclass method and IV in instances)");
        System.out.println("");
        for(Animal animalElement : animals) {//we can control all animal at once (can use superclass method and IV in instances)
            animalElement.eat();
            animalElement.roam();
            animalElement.makeNoise(); 
            animalElement.sleep();
            if(animalElement instanceof Pet) { // 1-st Type Casting
                owner.givemeal((Pet)animalElement);//Petowner only give meal to Pet class
            }
            treater.giveShot(animalElement);
            System.out.println("---------------------------------");
        }


        System.out.println("2. This is Test for private-superclass-instance-variable-access");//superclass의 변수에 간접접근 가능
        System.out.println("");
        System.out.println("Food is private instance variable in Superclass Animal");
        System.out.println("Test for Getting and Setting Food");//Test for private-superclass-instance-variable-access
        System.out.println("Getting <instance variable>-Food of first animal");
        System.out.println(animals[0].getFood());
        System.out.println("Setting the Food as My new Food!");
        animals[0].setFood("My new Food!(private value accessed)");
        System.out.println("Getting the Food Again, you can check it changed!!");
        System.out.println(animals[0].getFood());
        System.out.println("-> You can access to superclass private value by get set method unless no direct access is allowed");
        System.out.println("---------------------------------");
        

        System.out.println("3. This is another test for private-method-access");//superclass의 메소드에 간접접근 가능
        System.out.println("");
        System.out.println("dance is private method in Superclass Animal");
        System.out.println("Test for accessing dance");//Test for private-superclass-method-access
        animals[0].danceTime();
        System.out.println("access to dance and you can check it is possible!!");
        System.out.println("-> You can access to superclass method by get set method unless no direct access is allowed");
        System.out.println("---------------------------------");


        System.out.println("4. This is Test for Robot method (it is not subclass of Animal but subclass of Pet)");//RoboDog Test   
        RoboDog roboDog = new RoboDog();//Robodog First To add to PetList
        System.out.println("I'm RoboDog, I can do the Robot-Things (TurnOn TurnOff)");
        roboDog.turnOn();
        roboDog.turnOff();
        System.out.println("I can Also do the Pet-Things (play beFriendly)");
        roboDog.play();
        roboDog.befriendly();


        System.out.println("5. This is Test for Pet method");
        System.out.println("OH NO I Can't use animal as Pet.. :(");
        System.out.println("");
        System.out.println("But I have some Idea!!!");
        System.out.println("This idea is reverse of polymorphism");
        System.out.println("any-subclass -> superclass(polymorphism) <=> superclass -> each-subclass (My return method)");
        System.out.println("");
        System.out.println("I also added roboDog to Pet List! ");
        System.out.println("");
        ArrayList<Pet> petList = new ArrayList<Pet>();
        for(Animal animalElement : animals) {
            if(animalElement instanceof Pet) {
                petList.add( (Pet)animalElement  );
            }
        }

        petList.add(roboDog);

        //after this we have list consists only pet element!!! back from complex animals!!!
        System.out.println("with this method we extracted to list consists only pet element!!! back from complex animals!!!");
        System.out.println("You can check this type is in Pet (Dog, Cat, RoboDog)");
        System.out.println("When you have to extract only specific subclass instances from the list of Superclass, Use This Method");
        System.out.println("");
        for(Pet petEle : petList) {
            System.out.println("I am implementing the Pet Interface!, and my class is");
            System.out.println(petEle.getClass());
            System.out.println("is in Pet (Dog, Cat, RoboDog)");
            System.out.println("And I surely able to use Pet method");
            petEle.befriendly();
            petEle.play();
            owner.givemeal(petEle);
            System.out.println("---------------------------------");
        }     


        // This is Test for File I/O and Serialization
        Launcher mine = new Launcher();
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("./pet.ser"));
        os.writeObject(mine);
        os.close();
    }
    transient Cat transientCat = new Cat();
    Dog serDog = new Dog();
    // Cat nonSerCat = new Cat();//Exception뜸
}

