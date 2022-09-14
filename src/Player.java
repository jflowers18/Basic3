//Player Class


//import javax.smartcardio.Card;

public class Player {
    public String Name;
    public int Type;
    public int Money = 100;
    public int Bet = 0;
    public Card[] Hand;
    public int CardCount;
    public int total = 0;
    public boolean bust = false;

    //methods
    //constructor
    // cardNumber and suitValue
    public Player(String playerName, int playerType) {

        Name = playerName;
        Type = playerType;
        Hand = new Card[7];
        CardCount = 0;

    }

    public void PrintCards(){
        System.out.println(Name + " has...");
        total = 0;
        for (int i = 0; i < CardCount; i++){
            System.out.println("      " + Hand[i].cardName + " of " + Hand[i].suit);
            total = total + Hand[i].value;
        }

        System.out.println(Name + " total " + total);
        if(total > 21){
            for (int i = 0; i < CardCount; i++){
                if(Hand[i].value == 11)
                    total = total - 10;

            }
            if(total > 21){
                bust = true;
                System.out.println(Name + " YOU HAVE BUSTED!!! " + total);

            }


        }

    }

    public void gameOver(boolean won)
    {
        //when each game ends add or remove money
        if(won == true)
            Money = Money + Bet;
        else
            Money = Money - Bet;
    }
    //public void DealCard(Card newCard){
    //  Card[CardCount] = newCard;
    //CardCount++;

    //}

}