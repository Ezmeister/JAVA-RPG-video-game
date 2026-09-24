# My Personal Project

## Proposal for personal project

### What will the application do? / Who will use it?
This application will create a **game** based on medieval themes. This application will be **easy to operate**; there will be a **protagonist** in the game, who
is a **knight in Europe**. In this game, players will make a series of choices and enhance the protagonist's strength. People interested in the *Middle Ages 
or gaming* will use the application to entertain themselves. If someone is interested in *history, role-playing, and video games* simultaneously. This 
application would be a great choice.

### Why is this project of interest to me?
I am interested in designing this application because I am a **history game** enthusiast and also a **role-playing game** enthusiast. This project enables me
to transform my passion into a practical application. I have played many games, and I am also very interested in creating my own games.




## User stories
- As a user, I hope to build a item and add it to the protagonist's backpack.
- As a user, I hope to be able to view list of all the nobles that the protagonist knows.
- As a user, I hope to be able to remove equipment from the protagonist's backpack.
- As a user, I hope to see the protagonist's statistical data, including the amount of money he spent and the number of people he saved.

- As a user, I want to be able to save my game to file (if I so choose).
- As a user, I want to be able to be able to load game from file (if I so choose).


## Instructions for End User
- You can view the panel that displays the items that have already been added to the Backpack by Click backpack to view the panel that displays the items that have already been added to the backpack.
- You can generate the first required action related to the user story "adding an item to a Backpack" by Click the button "Buy selected Item" to add an item into backpack.
- You can generate the second required action related to the user story "displays a subset of the items that satisfy some criterion" by Click backpack, then click weapon to see all items which is weapon in the backpack.
- You can locate my visual component by Click stat to find the visual component that was added to your project.
- You can save the state of my application by Click save to save the state of the application to file.
- You can reload the state of my application by Click load to load the state of the application from file.
- Because this is a game, you can use save and load to battle with enemy more than once in order to get more gold， then buy items more than once, to help you win.

## Phase 4: Task 2
-I logged the following three key actions in the model:
- Viewing all items in the backpack
- Adding an item to the backpack
- Viewing a filtered subset of items


Representative Event Log Sample

Fri Nov 28 01:31:50 PST 2025
Viewed all items in backpack (0 items).

Fri Nov 28 01:31:52 PST 2025
Item added to backpack: Iron Sword (type = weapon, cost = 50, damage = 5)

Fri Nov 28 01:31:52 PST 2025
Viewed all items in backpack (1 items).

Fri Nov 28 01:31:53 PST 2025
Item added to backpack: Steel Sword (type = weapon, cost = 80, damage = 8)

Fri Nov 28 01:31:53 PST 2025
Viewed all items in backpack (2 items).

Fri Nov 28 01:31:56 PST 2025
Item added to backpack: Knight Armor (type = armor, cost = 90, damage = 9)

Fri Nov 28 01:31:56 PST 2025
Viewed all items in backpack (3 items).

Fri Nov 28 01:31:57 PST 2025
Viewed all items in backpack (3 items).

Fri Nov 28 01:31:58 PST 2025
Viewed items in backpack filtered by type = weapon (2 items).

Fri Nov 28 01:31:59 PST 2025
Viewed items in backpack filtered by type = armor (1 items).

## Phase 4: Task 3

If I had more time, I would simplify some parts of the design. Currently, GameGUI manages all panels as well as loading and saving data, which gives it too many responsibilities. I would move the loading and saving logic into another class so that GameGUI only handles the user interface. This would reduce coupling and make the overall structure clearer and easier to maintain.

Right now, whenever I want to change something in the GameGUI class, I need to make sure that all four panels are not affected. After making changes, I must check every responsibility, which takes a lot of time. With refactoring, I would be able to test only the UI behavior in GameGUI, while testing the other responsibilities in a separate class.