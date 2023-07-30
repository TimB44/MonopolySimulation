package monopoly;

public class JailBoardObject implements BoardObjects{

    //References used to keep track of get out of jail free cards
    CommunityChestBoardObject communityChestBoardObject;
    ChanceBoardObject chanceBoardObject;

    //Used to differentiate between people in jail and visitors.
    boolean inJail;

    //used for rolling doubles to get out of jail
    int turnsInJail;

    //false means instantly pay to get out, true means attempt to roll doubles to get out.
    boolean strategy;

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

                //Strategy "a" (false) will pay instantly to get out of jail
                else if(!strategy){
                    inJail = false;
                    turnsInJail = 0;
                    return position + die1 + die2;

                }
            }

            //Once you roll doubles, or wait 3 days you will get out of jail and move forward
            if(turnsInJail == 3 || die1 == die2){
                inJail = false;
                turnsInJail = 0;
                return position + die1 + die2;
            }
        }

        //If not in jail then you are just visiting meaning your position stays the same
        return 10;
    }

    public void sentToJail() {
        inJail = true;
    }

    public boolean isInJail() {
        return inJail;
    }
}
