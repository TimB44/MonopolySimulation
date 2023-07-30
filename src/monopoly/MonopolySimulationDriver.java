package monopoly;

/**
 * This Class is the driver class that contain the main method for the program. This class will start a simulation of
 * monopoly. This driver will take two argument in order to run correctly. The first argument will determine the
 * strategy used to get out of jail. If strategy "a" is specified then the player will immediately pay the 50 dollars to
 * get out of jail. If strategy b is specified then the player will attempt to roll doubles for 3 turns before paying 50
 * dollars to get out of jail. Note that both strategies will immediately use a get out of jail free card if they
 * have it. Using the argument "both" will run the simulation first using strategy "a", then using strategy "b". \
 *
 * The second parameter will specify how many times you would like to simulate the game. Note that if you are
 * using the both argument then the number of simulation will be twice the number provided as it will run once for
 * strategy a and once for strategy b.
 *
 */
public class MonopolySimulationDriver {
    public static void main(String[] args) {
        //Checking for illegal arguments
        if(args.length != 2)
            throw new IllegalArgumentException("Must provide valid arguments");
        if(!(args[0].equals("a") || args[0].equals("b") || args[0].equals("both")))
            throw new IllegalArgumentException("First argument not valid");
        if(Integer.parseInt(args[1]) < 1)
            throw new IllegalArgumentException("Second argument not valid");

        //The number of simulation to be ran
        int loops = Integer.parseInt(args[1]);

        //Running simulations
        if(args[0].equals("a")){
            for(int i = 0; i < loops; i++){
                MonopolySimulation m = new MonopolySimulation(false);
                m.run();
            }

        }
        if(args[0].equals("b")){
            for(int i = 0; i < loops; i++){
                MonopolySimulation m = new MonopolySimulation(true);
                m.run();
            }

        }
        if(args[0].equals("both")){
            for(int i = 0; i < loops; i++){
                MonopolySimulation m = new MonopolySimulation(false);
                m.run();
                m = new MonopolySimulation(false);
                m.run();
            }

        }

    }
}