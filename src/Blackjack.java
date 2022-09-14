//Blackjack class
//This is the definition of our Blackjack game.
/*import java.awt.Graphics2D;
import java.awt.event.ActionListener;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;*/
import java.awt.image.BufferStrategy;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.sound.sampled.*;

public class Blackjack implements Runnable, KeyListener, ActionListener {

    //1 - declare the array
    public Card[] deck;      //this is the array that will represent our deck of cards.
    public Player player1;
    public Player player2;
    public Player currentPlayer;
    public Image image;
    public Image backcard;
    public int cardheight = 315;
    public int cartdwidth = 225;
    public Image bkimage;
    public boolean gameover = false;

    //Hit and Stay Buttons
    public JButton hitButtonP1;
    public JButton stayButtonP1;
    public JButton betButtonP1;

    public JButton hitButtonP2;
    public JButton stayButtonP2;
    public JButton betButtonP2;

    public JButton newGame;

    public int currentcard = 0;
    public String whowon = "";

    final int WIDTH = 1000;
    final int HEIGHT = 800;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //constructor.
    public Blackjack() {

        setUpGraphics();

        image = Toolkit.getDefaultToolkit().getImage("cards.jpg");
        backcard = Toolkit.getDefaultToolkit().getImage("back.jpg");

        bkimage = Toolkit.getDefaultToolkit().getImage("backround.jpg");

        restart();


    }


    public void startGame() {
        new Thread(this).start();
    }

    // a method to go through the deck array and print out the information on each card by using the printCard() method
    public void printDeck() {
        for (int i = 0; i < deck.length; i++) {
            deck[i].printCard();
        }

    }

    public void Shuffle() {
        System.out.println("Shuffling...");
        Card tempCard;
        playSound("shuffle.wav");
        for (int i = 0; i < deck.length; i++) {
            int random = (int) (Math.random() * 51);
            tempCard = deck[i];
            deck[i] = deck[random];
            deck[random] = tempCard;
        }
    }

    public void Deal() {
        // say I am dealing
        System.out.println("Dealing...");

        // give two cards to each player
        for (int i = 0; i < 2; i++) {
            player1.Hand[i] = deck[currentcard];
            player1.CardCount++;
            currentcard = currentcard + 1;
            player2.Hand[i] = deck[currentcard++];
            player2.CardCount++;
        }

        //print out the words for players cards
        player1.PrintCards();
        player2.PrintCards();
        currentPlayer = player1;
    }

    public void DealOne() {
        // say I am dealing
        System.out.println("Dealing card...");
        playSound("cardflip.wav");

        // give two cards to each player
        //currentPlayer.DealCard(deck[currentcard]);
        currentPlayer.Hand[currentPlayer.CardCount] = deck[currentcard++];
        currentPlayer.CardCount++;
        //print out the words for players cards
        player1.PrintCards();
        player2.PrintCards();
        if (currentPlayer.bust == true)
            gameOver(true);
    }

    public void restart(){
        gameOver(false);
        currentcard = 0;
        //2 - construct the array
        deck = new Card[52];  //constructs the array that will hold 52 cards

        //fill the array of cards
        int slotNumber = 0;  //declares and initializes a counter variable.

        // the s (suit) loop will go through all the suits
        for (int s = 1; s <= 4; s++) {

            //the cn (card number inner loop) will go through all the cards (1 is the Ace, 2 is the 2.... 13 is the King)
            for (int cn = 1; cn <= 13; cn++) {

                deck[slotNumber] = new Card(cn, s);     //construct a new card and store it in the deck array
                slotNumber++;   // increment the counter so the we can go to the next card slot in the deck array
            }
        }

        player1 = new Player("Jimmy", 0);
        player2 = new Player("Dealer", 1);


        //printDeck();         //print out the deck to see if things worked.  the
        Shuffle();
        //printDeck();
        Deal();

    }

    public void run() {

        //for the moment we will loop things forever.
        while (true) {
            render();  // paint the graphics
            pause(100);

        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(150, 0, WIDTH-150, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addKeyListener(this);

        //Add buttons
        hitButtonP1 = new JButton("HIT");
        stayButtonP1 = new JButton("STAY");
        betButtonP1 = new JButton("BET");
        hitButtonP1.setBounds(30, 60, 100, 30);
        stayButtonP1.setBounds(30, 100, 100, 30);
        betButtonP1.setBounds(30, 140, 100, 30);

        hitButtonP2 = new JButton("HIT");
        stayButtonP2 = new JButton("STAY");
        betButtonP2 = new JButton("BET");
        hitButtonP2.setBounds(30, cardheight+60, 100, 30);
        stayButtonP2.setBounds(30, cardheight+100, 100, 30);
        betButtonP2.setBounds(30, cardheight+140, 100, 30);

        newGame = new JButton("New Game");
        newGame.setBounds(30, cardheight*2 +20, 100, 35);

        //set action commands
        hitButtonP1.addActionListener(this);
        hitButtonP1.setActionCommand("HitP1");
        stayButtonP1.addActionListener(this);
        stayButtonP1.setActionCommand("StayP1");
        betButtonP1.addActionListener(this);
        betButtonP1.setActionCommand("BetP1");

        hitButtonP2.addActionListener(this);
        hitButtonP2.setActionCommand("HitP2");
        stayButtonP2.addActionListener(this);
        stayButtonP2.setActionCommand("StayP2");
        betButtonP2.addActionListener(this);
        betButtonP2.setActionCommand("BetP2");
        newGame.addActionListener(this);
        newGame.setActionCommand("newGame");

        frame.add(hitButtonP1);
        frame.add(stayButtonP1);
        frame.add(betButtonP1);
        frame.add(hitButtonP2);
        frame.add(stayButtonP2);
        frame.add(betButtonP2);
        frame.add(newGame);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        panel.setBackground(Color.GREEN);
        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");


    }


    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        Font font = new Font("Dialog", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.white);

        g.clearRect(0, 0, WIDTH, HEIGHT);

        //add background
        g.drawImage(bkimage, 0, 0, WIDTH, HEIGHT, null);
        g.drawString(player1.Name, 20, 100);
        g.drawString(player2.Name, 20, 100 + cardheight);
        g.drawString("->" + player1.total, 20, 130);
        g.drawString("Bet:$"+player1.Bet, 20, 220);
        g.drawString("$"+player1.Money, 20, 170);
        g.drawString("->" + player2.total, 20, 130 + cardheight);
        g.drawString("Bet:$"+player2.Bet, 20, 220+cardheight);
        g.drawString("$"+player2.Money, 20, 170+cardheight);


        if (gameover == true) {
            Font overFont = new Font("Dialog", Font.BOLD, 60);
            g.setFont(overFont);
            g.drawString(whowon, 190, 400);
        }
        else {
            int dx1, dy1, dx2, dy2;
            int sx1, sy1, sx2, sy2;
            dx1 = 50;
            dy1 = 25;
            dx2 = dx1 + cartdwidth;
            dy2 = dy1 + cardheight;

            for (int i = 0; i < player1.CardCount; i++) {
                sx1 = (player1.Hand[i].cardNumber - 1) * cartdwidth;
                sy1 = (player1.Hand[i].suitValue - 1) * cardheight;

                sx2 = sx1 + cartdwidth;
                sy2 = sy1 + cardheight;

                dx1 = dx1 + cartdwidth / 3;
                dx2 = dx2 + cardheight / 3;

                g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

            }

            dx1 = 50;
            dy1 = cardheight + 45;
            dx2 = dx1 + cartdwidth;
            dy2 = dy1 + cardheight;

            for (int i = 0; i < player2.CardCount; i++) {
                sx1 = (player2.Hand[i].cardNumber - 1) * cartdwidth;
                sy1 = (player2.Hand[i].suitValue - 1) * cardheight;

                sx2 = sx1 + cartdwidth;
                sy2 = sy1 + cardheight;

                dx1 = dx1 + cartdwidth / 3;
                dx2 = dx2 + cardheight / 3;

                if (i == 0)
                    g.drawImage(backcard, dx1, dy1, cartdwidth, cardheight, null);
                else
                    g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
            }
        }
        //.updateUI();

        g.dispose();

        bufferStrategy.show();
    }


    //Add handling for key press event//
    public void keyTyped (KeyEvent e) {

    }

    public void keyPressed (KeyEvent e){
        char key = e.getKeyChar();
        if (key == 'r')
            restart();
        if(gameover == true)
            return;

        if (key == 'h') {
            DealOne();
        }
        if (key == 's') {
            stayFunction();


        }

        if (key == 'r')
            restart();
    }

    public void stayFunction() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
            hitButtonP1.setEnabled(false);
            stayButtonP1.setEnabled(false);
            betButtonP1.setEnabled(false);
            //enable the player two buttons
            hitButtonP2.setEnabled(true);
            stayButtonP2.setEnabled(true);
            betButtonP2.setEnabled(true);
        } else {
            currentPlayer = player1;
            gameOver(true);

        }
    }

    public void gameOver(boolean over) {
        gameover = over;
        if(gameover == true)
        {
            //disable all the buttons
            hitButtonP1.setEnabled(false);
            stayButtonP1.setEnabled(false);
            betButtonP1.setEnabled(false);
            //enable the player two buttons
            hitButtonP2.setEnabled(false);
            stayButtonP2.setEnabled(false);
            betButtonP2.setEnabled(false);

            //tell the players the game is over
            if (player1.bust == true) {
                player1.gameOver(false);
                player2.gameOver(true);
                whowon = player2.Name + " Wins!!";
            } else if (player2.bust == true) {
                player1.gameOver(true);
                player2.gameOver(false);
                whowon = player1.Name + " Wins!!";
                playSound("cheer3.wav");
            } else if (player2.total > player1.total) {
                player1.gameOver(false);
                player2.gameOver(true);
                whowon = player2.Name + " Wins!!";
            } else if (player2.total == player1.total) {
                player1.gameOver(true);
                player2.gameOver(true);
                whowon = "It's a Tie!!!";
            } else {
                player1.gameOver(true);
                player2.gameOver(false);
                whowon = player1.Name + " Wins!!";
                playSound("cheer3.wav");
            }

        }
        else {
            //enable the buttons
            hitButtonP1.setEnabled(true);
            stayButtonP1.setEnabled(true);
            betButtonP1.setEnabled(true);
            //enable the player two buttons
            hitButtonP2.setEnabled(false);
            stayButtonP2.setEnabled(false);
            betButtonP2.setEnabled(false);

        }

    }

    public void keyReleased (KeyEvent e){
        char key = e.getKeyChar();



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("HitP1".equals(e.getActionCommand()) || "HitP2".equals(e.getActionCommand()))
            DealOne();
        else if("StayP1".equals(e.getActionCommand()) || "StayP2".equals(e.getActionCommand()))
        {
            stayFunction();
        }
        else if("BetP1".equals(e.getActionCommand()))
        {
            player1.Bet = player1.Bet + 10;
        }
        else if("BetP2".equals(e.getActionCommand()))
        {
            player2.Bet = player1.Bet;
        }
        else if("newGame".equals(e.getActionCommand())) {
            restart();
        }
    }

    public void playSound(String name) {

        try {

// Open an audio input stream.
            File soundFile = new File(name); //you could also get the sound file with an URL
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
// Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
// Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();   //start playing the sound
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}