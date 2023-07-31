package monopoly;

/**
 * This method acts as the jail space on the Monopoly board. This object will handle all the logic associated with
 * being in jail, and the ways to get out. The ways to get out of jail include, paying a 50$ fine, using a get out of
 * free card, or rolling doubles. This simulation has 2 strategies. In strategy "a" (false) you player will always pay
 * the fine on the first turn if they do not have a get out of jail free card at their disposal. With strategy B (true),
 * the player will attempt to roll doubles 3 times. If the player fails they will pay the fine on the third round and
 * move forward as usual. Note that because this simulation does not track a players' money, no fine is actually paid to
 * get out of jail
 *
 * @author Timothy Blamires
 * @version 8/30/23
 */

public class JailBoardObject implements BoardObjects{

    //References used to keep track of get out of jail free cards
    private CommunityChestBoardObject communityChestBoardObject;
    private ChanceBoardObject chanceBoardObject;

    //Used to differentiate between people in jail and visitors.
    private boolean inJail;

    //used for rolling doubles to get out of jail
    private int turnsInJail;

    //false means instantly pay to get out, true means attempt to roll doubles to get out.
    private boolean strategy;

    /**
     * This constructor builds a JailBoardObject which has a reference to the community chest and chance objects.
     * @param chanceBoardObject - The object representing all chance card spaces in the game
     * @param communityChestBoardObject - The object representing all community chest card spaces in the game
     */
    public JailBoardObject(boolean strategy, ChanceBoardObject chanceBoardObject, CommunityChestBoardObject communityChestBoardObject) {
        this.chanceBoardObject = chanceBoardObject;
        this.communityChestBoardObject = communityChestBoardObject;
        this.strategy = strategy;
        inJail = false;
        turnsInJail = 0;
    }

    /**
     * This method is called when a player lands on or gets sent to jail. This method handles the logic for getting out
     * of jail, and also makes sure that visitors are not placed in jail
     * @param die1     - the value of the first dice roll
     * @param die2     - the value of the second dice roll
     * @param position - the current board position of the player
     * @return The position of the player after the turn
     */
    @Override
    public int move(int die1, int die2, int position) {
        //If you are a visitor no work needs to be done
        if(inJail){
            turnsInJail++;
            //On the first turn in jail you will use the get out of jail free cards if you have them
            if(turnsInJail == 1){

                //If you get out of jail use your turn as normal
                if(chanceBoardObject.hasGetOutOfJailCard()){
                    chanceBoardObject.useGetOutOfJailCard();
                    inJail = false;
                    turnsInJail = 0;
                    return position + die1 + die2;
                }
                else if(communityChestBoardObject.hasGetOutOfJailCard()){
                    communityChestBoardObject.useGetOutOfJailCard();
                    inJail = false;
                    turnsInJail = 0;
                    return position + die1 + die2;
                }

                //Strategy "a" (false) will pay instantly to get out of jail if they do not have cards
                else if(!strategy){
                    inJail = false;
                    turnsInJail = 0;
                    return position + die1 + die2;

                }
            }

            //Once you roll doubles, or wait 3 days strategy b will get out of jail and move forward
            if(turnsInJail == 3 || die1 == die2){
                inJail = false;
                turnsInJail = 0;
                return position + die1 + die2;
            }
        }

        //If not in jail then you are just visiting meaning your position stays the same
        return 10;
    }

    /**
     * This method is called by other objects when you are sent to jail. This ensures that the player is treated as
     * a prisoner and not as a visitor by the JailBoardObject
     */
    public void sentToJail() {
        inJail = true;
    }

    /**
     * This method returns true if the player is in jail and false if they are not. Note that being a visitor does not
     * mean you are in jail
     * @return true if in jail, false if not
     */
    public boolean isInJail() {
        return inJail;
    }
}
