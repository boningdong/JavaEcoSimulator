# JavaEcoSimulator

  - This project is a Java based ecosystem simulator aiming for creating a balanced sheep-wolves ecosystem. 
  - I build this for fun and for getting familiar with Java programming language, design pattern, and practice with several techniques like multithreading.
  - The idea originates from my friend [Tian Gao](https://github.com/gaogaotiantian).

## The simulator has following features.
  - Each animal is an individual. It determines it's own behavior based on its surrounding area and its own status.
  - Both of the wolves and sheep will getting hungry. If their food value reaches zero, they will die. 
  - Both of the wolves and sheep will getting old, when they reach their maximux age, they will die.
  - Both of the wolves and sheep have desire value, if their desire is strong, they will find a mate target.
  - Mate behavior may cause the wolves or sheep pregnant, and wolves or sheep babies may generate on the map. 
  - Mate behavior will loss food value.
  - The new born baby will inherit its parents property (speed and sight range), and it will vary because of the mutation.
  - When wolf is hungry, it will find a sheep target, and hunt the target.
  - During chasing a mate target and a hunt target, animals will run faster, but faster speed leads a faster food drop.
  - The food gain rate for sheep is based on how many sheep nearby. If there are too many sheep, the area cannot support all the sheep to survive.
  - A Statistician class is used for recording the data of the game.
  - A Plotter will plot the change of entities' number with time went by.
  - All the parameters and features are adjusted and designed to make sure the ecosystem can be balanced.



