//This is the CASINO class.  It will "host" our Blackjack games.


//import section - list any external libraries needed


//beginning of the class definition
public class Casino {

    //The main( ) method.  This runs first and automatically.
    //Any Java Projet needs a main( ) method.
    public static void main(String[] args) {
        System.out.println("Hello Casino");  //Print out "Hello Casino" in the terminal box below
        Blackjack game1;                     //declare a variable of type Blackjack called game1
        game1 = new Blackjack();             //Construct the new Blackjack game (object).
        game1.startGame();
    }
}
