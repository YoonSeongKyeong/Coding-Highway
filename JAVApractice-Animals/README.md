# This is about Animal demo

## 메인함수 순서 설명
 - 각자의 서브클래스 instance들을 Animal Array에 담아서 한번에 컨트롤 (polymorphism)
 - 그중에 Pet만 가능한 메소드는 Pet인지 확인하고 Pet으로 Casting해서 실행
 - 서브클래스에서 슈퍼클래스의 private 변수와 메소드에 간접적으로 접근 가능한 것을 확인
 - RoboDog 소개와 Pet list에 추가
 - Animal Array에서 Pet만 따로 뽑아내고 싶을 때 사용하는 메소드 소개 (extraction)
 - Pet list에 담아서 Pet의 서브클래스들을 한번에 컨트롤 (polymorphism again)


# Animal #Abstract
    instance variables
        - picture
        - food
        - hunger 
        - boundaries 
        - location
    methods
        - makeNoise()
        - eat()
        - sleep() 
        - roam()
    subclass
        - Feline
        - Hippo
        - Canine

## Feline #Abstract
    methods
       - roam()  
    subclass
        - Lion
        - Tiger
        - Cat
### Lion
    methods
        - makeNoise()
        - eat()
        - sleep() 
### Tiger
    methods
        - makeNoise()
        - eat()
        - sleep() 
### Cat
    methods
        - makeNoise()
        - eat()
        - sleep() 
        - befriendly()
        - play()
## Hippo
    methods
        - makeNoise() 
        - eat()
        - sleep() 
        - roam()
## Canine #Abstract
    methods
        - roam()  
    subclass
        - Wolf
        - Dog
### Wolf
    methods
        - makeNoise()
        - eat()
        - sleep() 
### Dog
    methods
        - makeNoise()
        - eat()
        - sleep() 
        - befriendly()
        - play()
                
# Vet
    methods
        - giveShot()
# PetOwner
    methods
        - give meal()
# ROBOT #Abstract
    methods
        - turnOn()
        - turnOff()
    subclass
        - RoboDog
### RoboDog
    methods
        - turnOn()
        - turnOff()
        - befriendly()
        - play()
# Pet #Interface
    methods
        - befriendly()
        - play()
    subclass
        - Cat
        - Dog
        - RoboDog