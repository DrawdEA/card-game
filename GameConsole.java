/**
 * The GameConsole class handles the input of the player, including the toggling of addons,
 * and deciding whether to attack or swap. This is also where the report is printed on the console.
 * 
 * @author Edward Joshua M. Diesta (241571)
 * @version December 3, 2024
 * 
 * I have not discussed the Java language code in my program 
 * with anyone other than my instructor or the teaching assistants 
 * assigned to this course.
 * 
 * I have not used Java language code obtained from another student, 
 * or any other unauthorized source, either modified or unmodified.
 * 
 * If any Java language code or documentation used in my program 
 * was obtained from another source, such as a textbook or website, 
 * that has been clearly noted with a proper citation in the comments 
 * of my program.
 */
import java.util.Scanner;

public class GameConsole {
    public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		GameMaster gameMaster;

		// Handles the toggling of the addons depending on what the player put in the console.
		if (args.length == 2) {
			gameMaster = new GameMaster(new Player(args[0]), new Player(args[1]));
		} else {
			gameMaster = new GameMaster(new Player(args[0]), new Player(args[1]), args[2].equals("y"), args[3].equals("y"), args[4].equals("y"));
		}

		System.out.printf("Welcome, %s and %s!\nThe game begins.\n\n", args[0], args[1]);

		// Deals 10 cards, 5 for each player.
		for (int i = 0; i < 10; i++) {
			System.out.print(gameMaster.dealCard());
		}

		// Start the game loop. Only break out of there is already a winner.
		while (true) {
			String action = "";
			while (!(action.equalsIgnoreCase("attack") || action.equalsIgnoreCase("swap"))) {
				System.out.print("Attack or Swap? ");
				action = input.nextLine();
			}
			System.out.print(gameMaster.play(action));

			if (gameMaster.hasWinner()) {
				System.out.print(gameMaster.gameReport());
				break;
			}
		}

		// Closes the scanner.
		input.close();
    }
}
