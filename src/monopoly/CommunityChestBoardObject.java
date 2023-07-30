package monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CommunityChestBoardObject implements BoardObjects {

    //Array used to keep track of the cards in the deck
    //-1 indicates the card has no effect on the position soo it is not relevant for this simulation
    List<Integer> deck;

    //This is the list cards get added to once they have been used
    List<Integer> discard;

    //boolean to keep track if the player has the get out of jail free card
    boolean hasGetOutOfJailCard;

    //Reference used to send the player to jail
    private JailBoardObject jailBoardObject;

    public CommunityChestBoardObject() {
        deck = new ArrayList<>(16);
        discard = new ArrayList<>(16);
        hasGetOutOfJailCard = false;


        //add 13 cards that do not affect position
        for (int i = 0; i < 13; i++) {
            deck.add(-1);
        }

        //These cards move the spots indicated by the number
        deck.add(0);

        //These are special cards that require more logic to determine their effect on player position
        deck.add(-2);
        deck.add(-3);


        //Shuffle the deck
        Collections.shuffle(deck);

    }

    /**
     * This method is called when the player lands on any of the community chest spaces on the board. The player will
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

        //The Get out of free card goes into the discard pile once used
        if(card != -2)
            discard.add(card);

        //A -1 card does not affect the position of the player, so the player does not move
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


        //Should never reach this line, If it does, it indicates an error
        throw new IllegalStateException("Invalid Card number in the community chest deck");
    }

    /**
     * This method will show whether a player has the get out of jail free card from the Community chest card deck.
     * @return - true if the player has the card, false if they do not
     */
    public boolean hasGetOutOfJailCard() {
        return hasGetOutOfJailCard;
    }

    /**
     * This method is called when a player uses their get out of jail free card that they got from a chance card
     */
    public void useGetOutOfJailCard() {
        if(!hasGetOutOfJailCard)
            throw new IllegalStateException("Can not use card as you do not have it");
        hasGetOutOfJailCard = false;
        discard.add(-2);
    }

    /**
     * This method is used to give the CommunityChestBoardObject a reference to the Jail Object so that it can properly
     * send the player to jail when the go-to jail card is drawn
     * @param jailBoardObject - The Object representing the jail the in the game
     */
    public void setJail(JailBoardObject jailBoardObject) {
        this.jailBoardObject = jailBoardObject;
    }
}
