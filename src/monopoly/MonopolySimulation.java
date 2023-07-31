package monopoly;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;

/**
 * This class acts as a simulation of monopoly in order to find which places are the most probable for a player to land
 * on. This simulation does not keep track of money, and only has one player, however it does keep track of all aspects
 * of the game that affect your position on the board.
 *
 * @author Timothy Blamires
 * @version 8/30/23
 */
public class MonopolySimulation {

    //Defines which strategy you use to get out of jail
    private final boolean strategy;

    //used as the dice of the game
    private final Random rng;

    //used to keep track of which spots are landed on most frequently
    private final int[] freq;

    //Board objects used to keep track of the logic of some spaces
    private final CommunityChestBoardObject communityChestBoardObject;
    private final ChanceBoardObject chanceBoardObject;
    private final JailBoardObject jailBoardObject;
    private final GoToJailBoardObject goToJailBoardObject;

    //Hashmap to map board position to Board Objects
    private final HashMap<Integer, BoardObjects> boardSpaces;

    /**
     * This method builds a MonopolySimulation object. The boolean parameter will determine which strategy is used to
     * get out of jail. Note that both strategies will use a get out of free card instantly if the player has one.
     * False - This means the player will use strategy "a", meaning they will immediately pay the 50$ to get out of jail
     * True - This means the player will use strategy "b", meaning they will attempt to roll doubles to get out of jail
     * @param strategy - The strategy used to get out of jail
     */
    public MonopolySimulation(boolean strategy) {
        this.strategy = strategy;

        //used as dice
        rng = new Random();

        //used to keep track places the player landed
        freq = new int[40];
        
        //Creating BoardObjects
        communityChestBoardObject = new CommunityChestBoardObject();
        chanceBoardObject = new ChanceBoardObject();
        jailBoardObject = new JailBoardObject(strategy, chanceBoardObject, communityChestBoardObject);
        goToJailBoardObject = new GoToJailBoardObject(jailBoardObject);
        communityChestBoardObject.setJail(jailBoardObject);
        chanceBoardObject.setJail(jailBoardObject);
        
        //mapping board spaces to BoardObjects
        boardSpaces = new HashMap<>(8);
        boardSpaces.put(2, communityChestBoardObject);
        boardSpaces.put(7, chanceBoardObject);
        boardSpaces.put(10, jailBoardObject);
        boardSpaces.put(17, communityChestBoardObject);
        boardSpaces.put(22, chanceBoardObject);
        boardSpaces.put(30, goToJailBoardObject);
        boardSpaces.put(33, communityChestBoardObject);
        boardSpaces.put(36, chanceBoardObject);
    }

    /**
     * This method starts the simulation. The results will be recorded at 4 intervals during the simulation.
     * 1,000 , 10,000 , 100,000 and 1,000,000. These results will be printed to the console and also copied to you
     * clipboard. This method will keep track of some game logic, such as the 3 doubles rule.
     */
    public void run() {

        //Variables used to track the player
        int doublesInARow = 0;
        int turns = 0;
        int position = 0;

        //variable for the dice
        int d1;
        int d2;

        //Running first 1,000 turns
        while(turns < 1_000){
            d1 = rollDie();
            d2 = rollDie();

            //if in jail do not move forward
            if(jailBoardObject.isInJail()){
                //Doubles do not count in jail
                doublesInARow = 0;
                position = jailBoardObject.move(d1, d2, position);
                freq[position]++;
                turns++;
                continue;
            }

            //Seeing if doubles where rolled
            if(d1 == d2)
                doublesInARow++;
            else
                doublesInARow = 0;

            //3 doubles in a row sends you to jail
            if(doublesInARow == 3){
                position = 10;
                jailBoardObject.sentToJail();
                doublesInARow = 0;
                freq[position]++;
                turns++;
                continue;
            }

            //move the player forward on the board (wrap around after 40)
            position = (position + d1 + d2) % 40;

            //If the player lands on an important place, call the move method on said BoardObject
            if(boardSpaces.containsKey(position)){
                position = boardSpaces.get(position).move(d1, d2, position);
            }


            //Increase the frequency of the current position. Note that it is th position at the end of the turn, so
            //if you where to land somewhere then be moved by a GameObject, your final spot will be the only spot counted
            freq[position]++;

            //Increase teh counting variable
            turns++;
        }
        printResults(turns);

        //Running until 10,000 turns
        while(turns < 10_000){
            d1 = rollDie();
            d2 = rollDie();

            //if in jail do not move forward
            if(jailBoardObject.isInJail()){

                //Doubles do not count in jail
                doublesInARow = 0;
                position = jailBoardObject.move(d1, d2, position);
                freq[position]++;
                turns++;
                continue;
            }

            //Seeing if doubles where rolled
            if(d1 == d2)
                doublesInARow++;
            else
                doublesInARow = 0;

            //3 doubles in a row sends you to jail
            if(doublesInARow == 3){
                position = 10;
                jailBoardObject.sentToJail();
                doublesInARow = 0;
                freq[position]++;
                turns++;
                continue;
            }

            //move the player forward on the board (wrap around after 40)
            position = (position + d1 + d2) % 40;

            //If the player lands on an important place, call the move method on said BoardObject
            if(boardSpaces.containsKey(position)){
                position = boardSpaces.get(position).move(d1, d2, position);
            }


            //Increase the frequency of the current position. Note that it is th position at the end of the turn, so
            //if you where to land somewhere then be moved by a GameObject, your final spot will be the only spot counted
            freq[position]++;

            //Increase teh counting variable
            turns++;
        }
        printResults(turns);

        //Running until 100,000 turns
        while(turns < 100_000){
            d1 = rollDie();
            d2 = rollDie();

            //if in jail do not move forward
            if(jailBoardObject.isInJail()){

                //Doubles do not count in jail
                doublesInARow = 0;
                position = jailBoardObject.move(d1, d2, position);
                freq[position]++;
                turns++;
                continue;
            }

            //Seeing if doubles where rolled
            if(d1 == d2)
                doublesInARow++;
            else
                doublesInARow = 0;

            //3 doubles in a row sends you to jail
            if(doublesInARow == 3){
                position = 10;
                jailBoardObject.sentToJail();
                doublesInARow = 0;
                freq[position]++;
                turns++;
                continue;
            }

            //move the player forward on the board (wrap around after 40)
            position = (position + d1 + d2) % 40;

            //If the player lands on an important place, call the move method on said BoardObject
            if(boardSpaces.containsKey(position)){
                position = boardSpaces.get(position).move(d1, d2, position);
            }


            //Increase the frequency of the current position. Note that it is th position at the end of the turn, so
            //if you where to land somewhere then be moved by a GameObject, your final spot will be the only spot counted
            freq[position]++;

            //Increase teh counting variable
            turns++;
        }
        printResults(turns);


        //Running until 1_000,000 turns
        while(turns < 1_000_000){

            d1 = rollDie();
            d2 = rollDie();

            //if in jail do not move forward
            if(jailBoardObject.isInJail()){

                //Doubles do not count in jail
                doublesInARow = 0;
                position = jailBoardObject.move(d1, d2, position);
                freq[position]++;
                turns++;
                continue;
            }

            //Seeing if doubles where rolled
            if(d1 == d2)
                doublesInARow++;
            else
                doublesInARow = 0;

            //3 doubles in a row sends you to jail
            if(doublesInARow == 3){
                position = 10;
                jailBoardObject.sentToJail();
                doublesInARow = 0;
                freq[position]++;
                turns++;
                continue;
            }

            //move the player forward on the board (wrap around after 40)
            position = (position + d1 + d2) % 40;

            //If the player lands on an important place, call the move method on said BoardObject
            if(boardSpaces.containsKey(position)){
                position = boardSpaces.get(position).move(d1, d2, position);
            }


            //Increase the frequency of the current position. Note that it is th position at the end of the turn, so
            //if you where to land somewhere then be moved by a GameObject, your final spot will be the only spot counted
            freq[position]++;

            //Increase teh counting variable
            turns++;
        }
        printResults(turns);
    }

    /**
     * This method prints the results of the simulation to the console so that they can be recorded. This is called
     * multiple times during the simulation at the 1,000, 10,000, 100,000 and finally the 1,000,000 turns point.
     * Note that the results are printed in the order of the spaces on the board, meaning GO is first, then
     * Mediterranean etc.
     */
    private void printResults(int turns) {
        //Adding all the frequencies together in one string seperated by lines breaks
        StringBuilder sb = new StringBuilder();
        for (int i :freq) {
            sb.append(i).append("\n");
        }
        System.out.println("Strategy " + (strategy? "b": "a") +"\nData for turns = " + NumberFormat.getIntegerInstance().format(turns) + ":\n");

        //Copies data to clipboard automatically and print to console(I did this so that it was easier to make the spreadsheets)
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(sb.toString());
        clipboard.setContents(transferable, null);

        //Printing the data to the console
        System.out.println(sb);


        //Sleeping for 5 seconds so that I can process the data before the simulation continues.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Returns a random number 1 - 6 inclusive. Acts as the dice for the game
     * @return integer 1 - 6
     */
    private int rollDie(){
        return rng.nextInt(1,7);
    }
}
