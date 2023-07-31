# Monopoly Simulation

This repository contains a simple Java implementation of a Monopoly simulation. The simulation helps determine the most probable places for a player to land on during the game. It does not involve tracking money but only focuses on the player's position on the board.

## How to Run

1. Download the executable JAR file from the "artifacts" folder.

2. Open a command-line interface (e.g., Terminal on macOS/Linux or Command Prompt on Windows).

3. Navigate to the directory containing the downloaded JAR file.

4. Execute the JAR file using the following command:
`java -jar monopolySimulation.jar <strategy> <number_of_simulations>`

Replace strategy with one of the following options:

* "a": Player pays $50 to get out of jail.
* "b": Player attempts to roll doubles to get out of jail.
* "both": Simulate both strategies.

Replace number_of_simulations with an integer value specifying how many times you want to simulate the game for the selected strategy.

The results will be printed to the console and automatically copied to clipboard.

Note
This is a basic implementation for educational purposes and may not cover all aspects of the Monopoly game. Feel free to modify and expand the code according to your needs.

Enjoy the simulation!
