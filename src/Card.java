//imports

public class Card {
    //variables
    public String suit;   //name of suit
    public int suitValue;  //number of the suit. We'll be using this to make using graphics easier. 1 - Hearts, 2 - Spades, 3 - Diamonds, 4 - Clubs
    public int value;   //what the card is worth in the game
    public int cardNumber;  //Ace is 1, 2 is 2....  10 is 10, Jack is 11,... King is 13
    public String cardName;  // "Ace", "2",  "3".....", "King":
    public String color; //"Red" or "Black"  We won't use that right now.

    //methods
    //constructor
    // cardNumber and suitValue
    public Card(int cardNumberParameter, int suitNumberParameter) {

        suitValue = suitNumberParameter;
        cardNumber = cardNumberParameter;


        //instead of using a bunch of if( ) statements we combined everything in to a switch statement
        //this checks the cardNumber and assigns the correct value and cardName to the card.
        switch (cardNumber) {
            case 1:
                cardName = "Ace";
                value = 11;
                break;
            case 11:
                cardName = "Jack";
                value = 10;
                break;
            case 12:
                cardName = "Queen";
                value = 10;
                break;
            case 13:
                cardName = "King";
                value = 10;
                break;
            default:
                cardName = "" + cardNumber;
                value = cardNumber;

        }

        switch (suitValue) {
            case 1:
                suit = "Hearts";
                break;

            case 2:
                suit = "Spades";
                break;

            case 3:
                suit = "Diamonds";
                break;

            case 4:
                suit = "Clubs";
                break;

            default:
                System.out.println("Bad suit");


        }//switch
    }

    public void printCard(){

        System.out.println(cardName + " of " + suit);


    }
}

