package monopoly;

/**
 * This class acts as an interface for all the relevant spaces on the board for our simulation. These places include
 * the jail space, the go-to jail space, community chest spaces, and chance spaces
 */
public interface BoardObjects {

    /**
     * This method will be called on a boardObject if the player lands on the object by rolling a the dice and moving
     * their player. Note that this method will be called after the dice are rolled, meaning that the player has already
     * moved for their turn and are now interacting will the space they are landed on. The dice are provided as some
     * spaced use their values in order to interact with the space
     *
     * @param die1     - the value of the first dice roll
     * @param die2     - the value of the second dice roll
     * @param position
     * @return
     */
    int move(int die1, int die2, int position);
}
