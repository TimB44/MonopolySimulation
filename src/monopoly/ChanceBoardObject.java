package monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This Class represents the Chance cards in the game of monopoly. Cards will be used then placed in a discard pile
 * just like the real game. Once the cards run out, the discard pile is shuffled and the cards are placed back into the
 * deck array. Note that because this simulation is only concerned with the position of the player, any cards that do
 * not affect a players position will simply do nothing
 *
 * @author Timothy Blamires
 * @version 8/30/23
 */

public class ChanceBoardObject implements BoardObjects {

    //This list keeps track chance cards in the deck
    private List<Integer> deck;

    //This is the list cards get added to once they have been used
    private List<Integer> discard;

    //boolean to keep track if the player has the get out of jail free card
    private boolean hasGetOutOfJailCard;

    //Reference used to send the player to jail if the go-to jail card is drawn
    private JailBoardObject jailBoardObject;

    /**
     * This method Builds a ChanceBoardObject for the monopoly simulation. This method will build the deck array and
     * populate it with the correct  integers. These integers represent cards in the deck. It will also shuffle the deck.
     *  a number >= 0 has the effect of moving the player to that position on the board.
     * -1 means the card  does nothing
     * -2 is the get out of jail free card
     * -3 is the go-to jail card
     * -4 is the move 3 spaces backwards card
     * -5 is the advance to nearest railroad card (There are 2 of these in the deck)
     * -6 is the advance to the nearest utility card
     */
    public ChanceBoardObject() {
        deck = new ArrayList<>(16);
        discard = new ArrayList<>(16);
        hasGetOutOfJailCard = false;


        //add 5 cards that do not affect position
        for (int i = 0; i < 5; i++) {
            deck.add(-1);
        }

        //These cards move the spots indicated by the number
        deck.add(0);
        deck.add(5);
        deck.add(11);
        deck.add(24);
        deck.add(39);

        //These are special cards that require more logic to determine their effect on player position
        deck.add(-2);
        deck.add(-3);
        deck.add(-4);
        deck.add(-5);   //There are 2 of these cards
        deck.add(-5);
        deck.add(-6);

        //Shuffle the deck
        Collections.shuffle(deck);

    }
    /**
     * This method is called when the player lands on any of the Chance spaces on the board. The player will
     * draw a card, and may move spaces if the card drawn moves their position
     * @param die1     - the value of the first dice roll
     * @param die2     - the value of the second dice roll
     * @param position - the current position of the player on the board
     * @return - The position of the player after drawing a card
     */
    @Override
    public int move(int die1, int die2, int position) {

        //if deck is empty, fill it up and shuffle cards
        if(deck.size() == 0){
            deck.addAll(discard);
            Collections.shuffle(deck);
            discard.clear();
        }

        //Remove the last item in the deck
        int card = deck.remove(deck.size() -1);

        //All cards go to the discard pile immediately except the get out of jail free card
        if(card != -2)
            discard.add(card);

        // -1 cards does not affect the position of the player, so the player does not move
        if(card == -1) return position;

        //A card number >= 0 indicates means that we move are player to that position on the board
        if(card >= 0) return card;

        // -2 Is the get out of jail free card, does not affect position
        if(card == -2){
            hasGetOutOfJailCard = true;
            return position;
        }

        // -3 is the go-to jail card, change position to jail
        if(card == -3){
            jailBoardObject.sentToJail();
            return 10;
        }

        // -4 is the go back 3 spaces card (same as moving forward 37 spaces)
        if(card == -4) return (position + 37) % 40;

        // -5 is the move to next railroad card
        if(card == -5){
            if(position >= 35 || position < 5)
                return 5;
            if(position >=25)
                return 35;
            if(position >= 15)
                return 25;
            return 15;
        }

        //-6 is the move to nearest Utility card
        if(card == -6){
            if(position >= 28 || position < 12)
                return 12;
            return 28;
        }

        //Should never reach this line, If it does, it indicates an error
        throw new IllegalStateException("Invalid Card number in the chance deck");
    }

    /**
     * This method will show whether a player has the get out of jail free card from the chance card deck.
     * @return - true if the player has the card, false if they do not
     */
    public boolean hasGetOutOfJailCard() {
        return hasGetOutOfJailCard;
    }

    /**
     * This method is called when a player uses their get out of jail free card that they got from a chance card.
     * An error will be thrown if the player does not have the card
     */
    public void useGetOutOfJailCard() {
        if(!hasGetOutOfJailCard)
            throw new IllegalStateException("Can not use card as you do not have it");
        hasGetOutOfJailCard = false;
        discard.add(-2);
    }

    /**
     * This method is used to give the ChanceBoardObject a reference to the Jail Object so that it can property
     * send the player to jail when the go-to jail card is drawn
     * @param jailBoardObject - The Object representing the jail the in the game
     */
    public void setJail(JailBoardObject jailBoardObject) {
        this.jailBoardObject = jailBoardObject;
    }
}
