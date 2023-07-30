package monopoly;

/**
 * This Class represents the go-to jail space on the monopoly board. When landed on this space will send you to the jail
 * space and put you in jail.
 *
 * @author Timothy Blamiers
 * @version 8/29/23
 */
public class GoToJailBoardObject implements BoardObjects{
    JailBoardObject jailBoardObject;
    public GoToJailBoardObject(JailBoardObject jailBoardObject) {
        this.jailBoardObject = jailBoardObject;
    }

    @Override
    public int move(int die1, int die2, int position) {
        jailBoardObject.sentToJail();
        return 10;
    }
}
