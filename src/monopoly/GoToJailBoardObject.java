package monopoly;

/**
 * This Class represents the go-to jail space on the monopoly board. When landed on this space will send you to the jail
 * space and put you in jail.
 *
 * @author Timothy Blamiers
 * @version 8/29/23
 */
public class GoToJailBoardObject implements BoardObjects{

    //used to send the player to jail
    private JailBoardObject jailBoardObject;

    /**
     * This method build the GoToJailBoardObject
     * @param jailBoardObject - The Jail object for the monopoly simulation
     */
    public GoToJailBoardObject(JailBoardObject jailBoardObject) {
        this.jailBoardObject = jailBoardObject;
    }

    /**
     * This method is called with the player land on the go-to jail space. This method will place the player in jail.
     * @param die1     - the value of the first dice roll
     * @param die2     - the value of the second dice roll
     * @param position - the current board position of the player
     * @return - 10, as the player will always be put in jail after landing on this space
     */
    @Override
    public int move(int die1, int die2, int position) {
        jailBoardObject.sentToJail();
        return 10;
    }
}
