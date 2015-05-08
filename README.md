# Android-Chess
Replicated chess on Android

The project was initially created as a prototype with an input-based UI where the board was represented through the console in text. Used a MVC architectural structure to help implement this application. The piece logic is implemented in the pieces package which acts as the model. Each piece (e.g. pawn, rook, queen) inherits from a super class called Piece. From the Piece class, they inherit basic properties and methods that are common among every piece on the board. Polymorphism takes place as these common methods will then be modified through overriding and overloading in each respective class. Methods from The control package held the code to call the methods from the model while the chess package acts as the view. 

Porting the project to Android, another package was created to help implement the GUI for the mobile application. Four classes are created with their corresponding activities and onCreate() methods to help initialize the individual screens. A record class is created to help keep track of the saved games so that they can be replayed through the playback screen. The record class contains a title to represent the file name and an ArrayList of 2D arrays that help keep track of each move made through the course of a game. 

Each class from the piece package along with the Record class implements Serializable to help persist the state of objects into file as they were. This way, the user can view replays and access previous games even after closing the application.

For the functionality of the application itself, the user is greeted by a main menu on start. From there, the user may start a new game or watch replays of saved games through the Playback button. Upon starting a new game, the game starts on White's turn. Piece may be moved by clicking on a piece and selecting a tile to move to or a piece to capture. If the movement if valid, the piece will move. A player may use the undo button to retry his turn. The game ends when a player wins through the opposing player resigning, consuming the opposing king (reason being, if the player does not move his king into a reasonable tile away from check, they are to be punished for their play), or checkmating the other king.
A draw button is also implemented to propose a draw with the other player. If the opposing player accepts, the game ends.
At the end of each game, a prompt appears asking the player if they want to save the game or not. Upon entering a name for the replay, if the user confirms, the game is stored as an object, serialized, and saved to the internal storage space. 

The Playback button brings the user to a screen where the user can select a saved replay and confirm to watch the game. Playing the replay will bring the player to another screen where he/she can click on the previous and next buttons to sift through each move of the game. 

The main data structures used for this application are ArrayLists and 2D arrays.

