# This is about Animal demo


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