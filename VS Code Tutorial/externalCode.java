1 import java.util.ArrayList; // import the ArrayList class
2 //메인함수 순서 설명
3 //1. 각자의 서브클래스 instance들을 Animal Array에 담아서 한번에 컨트롤(polymorphism)
4 //2. 그중에 Pet만 가능한 메소드는 Pet인지 확인하고 Pet으로 Casting해서 실행
5 //3. 서브클래스에서 슈퍼클래스의 private 변수와 메소드에 간접적으로 접근 가능한 것을 확인
6 //4. RoboDog 소개와 Pet list에 추가
7 //5. Animal Array에서 Pet만 따로 뽑아내고 싶을 때 사용하는 메소드 소개(extraction)
8 //6. Pet list에 담아서 Pet의 서브클래스들을 한번에 컨트롤(polymorphism again)
9 
10 
11 public class Launcher {
12     public static void main(String[] args) {
13         System.out.println("making Animal reference for contain sub-animal instances");
14         System.out.println("Controling all different classes at once is the power of Polymorphism!!");
15         Animal [] animals = new Animal[6];  //making Animal reference for contain sub-animal instances
16         animals[0] = new Wolf();            //Controling all different classes at once is power of Polymorphism!!
17         animals[1] = new Cat();             //각각 다른 인스턴스들을 superclass의 메소드와 변수를 사용해서 한번에 사용할 수 있다.
18         animals[2] = new Lion();
19         animals[3] = new Dog();
20         animals[4] = new Hippo();
21         animals[5] = new Tiger();
22         Vet treater = new Vet();             //(veterinarian) treats animals
23         PetOwner owner = new PetOwner();     //new pet owner instance
24 
25 
26         System.out.println("1. we can control all animal at once (can use superclass method and IV in instances)");
27         System.out.println("");
28         for(Animal animalElement : animals) {//we can control all animal at once (can use superclass method and IV in instances)
29             animalElement.eat();
30             animalElement.roam();
31             animalElement.makeNoise();
32             animalElement.sleep();
33             if(animalElement instanceof Pet) { // 1-st Type Casting
34                 owner.givemeal((Pet)animalElement);//Petowner only give meal to Pet class
35             }
36             treater.giveShot(animalElement);
37             System.out.println("---------------------------------");
38         }
39 
40 
41         System.out.println("2. This is Test for private-superclass-instance-variable-access");//superclass의 변수에 간접접근 가능
42         System.out.println("");
43         System.out.println("Food is private instance variable in Superclass Animal");
44         System.out.println("Test for Getting and Setting Food");//Test for private-superclass-instance-variable-access
45         System.out.println("Getting <instance variable>-Food of first animal");
46         System.out.println(animals[0].getFood());
47         System.out.println("Setting the Food as My new Food!");
48         animals[0].setFood("My new Food!(private value accessed)");
49         System.out.println("Getting the Food Again, you can check it changed!!");
50         System.out.println(animals[0].getFood());
51         System.out.println("-> You can access to superclass private value by get set method unless no direct access is allowed");
52         System.out.println("---------------------------------");
53 
54 
55         System.out.println("3. This is another test for private-method-access");//superclass의 메소드에 간접접근 가능
56         System.out.println("");
57         System.out.println("dance is private method in Superclass Animal");
58         System.out.println("Test for accessing dance");//Test for private-superclass-method-access
59         animals[0].danceTime();
60         System.out.println("access to dance and you can check it is possible!!");
61         System.out.println("-> You can access to superclass method by get set method unless no direct access is allowed");
62         System.out.println("---------------------------------");
63 
64 
65         System.out.println("4. This is Test for Robot method (it is not subclass of Animal but subclass of Pet)");//RoboDog Test   
66         RoboDog roboDog = new RoboDog();//Robodog First To add to PetList
67         System.out.println("I'm RoboDog, I can do the Robot-Things (TurnOn TurnOff)");
68         roboDog.turnOn();
69         roboDog.turnOff();
70         System.out.println("I can Also do the Pet-Things (play beFriendly)");
71         roboDog.play();
72         roboDog.befriendly();
73 
74 
75         System.out.println("5. This is Test for Pet method");
76         System.out.println("OH NO I Can't use animal as Pet.. :(");
77         System.out.println("");
78         System.out.println("But I have some Idea!!!");
79         System.out.println("This idea is reverse of polymorphism");
80         System.out.println("any-subclass -> superclass(polymorphism) <=> superclass -> each-subclass (My return method)");
81         System.out.println("");
82         System.out.println("I also added roboDog to Pet List! ");
83         System.out.println("");
84         ArrayList<Pet> petList = new ArrayList<Pet>();
85         for(Animal animalElement : animals) {
86             if(animalElement instanceof Pet) {
87                 petList.add( (Pet)animalElement  );
88             }
89         }
90 
91         petList.add(roboDog);
92 
93         //after this we have list consists only pet element!!! back from complex animals!!!
94         System.out.println("with this method we extracted to list consists only pet element!!! back from complex animals!!!");
95         System.out.println("You can check this type is in Pet (Dog, Cat, RoboDog)");
96         System.out.println("When you have to extract only specific subclass instances from the list of Superclass, Use This Method");
97         System.out.println("");
98         for(Pet petEle : petList) {
99             System.out.println("I am implementing the Pet Interface!, and my class is");
100             System.out.println(petEle.getClass());
101             System.out.println("is in Pet (Dog, Cat, RoboDog)");
102             System.out.println("And I surely able to use Pet method");
103             petEle.befriendly();
104             petEle.play();
105             owner.givemeal(petEle);
106             System.out.println("---------------------------------");
107         }
108     }
109 }
110 
111 
    