/**
 * The GameMaster class handles all of the card game logic, utilizing the Card and Player class.
 * It is responsible for assembling and manipulating the deck, taking count of the turns, 
 * dealing damage to a card given their resistances and weaknesses, checking for a winner, and reporting the game log.
 * This class also manages the randomize, human cards, and the newDeck add-on.
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
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class GameMaster {
    public static final int TOKENS_REQUIRED = 3;

    ArrayList<Card> deck;
    private int turn;
    private boolean hasWinner;
    private Player playerA;
    private Player playerB;
    private boolean randomized;
    Random rand;
    ArrayList<StatusEffect> activeEffects;
    private boolean statusEffectsOn;

    /**
	 * The assembleDeck() method is a private method.
	 * It is given entirely to the student.
	 * It must NOT be modified.
	 */
    private void assembleDeck()
    {
        deck.add( new Card( "Dragon", "Aquira", 174, 26 ) );
        deck.add( new Card( "Ghost", "Brawn", 130, 48 ) );
        deck.add( new Card( "Fairy", "Cerulea", 162, 29 ) );
        deck.add( new Card( "Dragon", "Demi", 147, 28 ) );
        deck.add( new Card( "Ghost", "Elba", 155, 37 ) );
        deck.add( new Card( "Fairy", "Fye", 159, 42 ) );
        deck.add( new Card( "Dragon", "Glyede", 129, 26 ) );
        deck.add( new Card( "Ghost", "Hydran", 163, 35 ) );
        deck.add( new Card( "Fairy", "Ivy", 146, 45 ) );
        deck.add( new Card( "Dragon", "Jet", 170, 24 ) );
        deck.add( new Card( "Ghost", "Kineti", 139, 21 ) );
        deck.add( new Card( "Fairy", "Levi", 160, 43 ) );
        deck.add( new Card( "Dragon", "Meadow", 134, 29 ) );
        deck.add( new Card( "Ghost", "Naidem", 165, 26 ) );
        deck.add( new Card( "Fairy", "Omi", 145, 21 ) );
        deck.add( new Card( "Dragon", "Puddles", 170, 34 ) );
        deck.add( new Card( "Ghost", "Quarrel", 151, 29 ) );
        deck.add( new Card( "Fairy", "Raven", 168, 32 ) );
        deck.add( new Card( "Dragon", "Surge", 128, 27 ) );
        deck.add( new Card( "Ghost", "Takiru", 140, 26 ) );
        deck.add( new Card( "Fairy", "Ustelia", 163, 47 ) );
        deck.add( new Card( "Dragon", "Verwyn", 145, 25 ) );
        deck.add( new Card( "Ghost", "Wyverin", 158, 32 ) );
        deck.add( new Card( "Fairy", "Xios", 155, 27 ) );
        deck.add( new Card( "Dragon", "Yora", 159, 44 ) );
        deck.add( new Card( "Ghost", "Zulu", 125, 46 ) );
    }

    /**
     * Initializes the GameMaster values. Sets the turn to 1, initializes the player's hand, assembles the deck normally,
     * and sets the add-on booleans to false.
     * 
     * @param a the first player
     * @param b the second player
     */
    public GameMaster(Player a, Player b) {
        turn = 1;
        hasWinner = false;
        deck = new ArrayList<Card>();
        assembleDeck();
        playerA = a;
        playerB = b;
        randomized = false;
        statusEffectsOn = false;
    }

    /**
     * Initializes the GameMaster values, with the add-ons considered.
     * 
     * @param a the first player
     * @param b the second player
     * @param r option to randomize the deck
     * @param n option to upload a new deck from newCard.txt
     * @param se option to include status effects in the game
     */
    public GameMaster(Player a, Player b, boolean r, boolean n, boolean se) {
        turn = 1;
        hasWinner = false;
        deck = new ArrayList<Card>();
        playerA = a;
        playerB = b;

        // If there is a newDeck addon, then check the newCards.txt file for the deck.
        if (n) { 
            try {
                FileReader reader = new FileReader("newCards.txt");
                Scanner scanner = new Scanner(reader);
                while (scanner.hasNextLine()) {
                    deck.add(new Card(scanner.next(), scanner.next(), Integer.parseInt(scanner.next()), scanner.nextInt()));
                }
                scanner.close();
            } catch(FileNotFoundException s) { // If the file was not found, assemble the deck normally.
                System.out.println("File not found! Assembling deck normally..");
                assembleDeck();
            }

        } else {
            assembleDeck();
        }

        rand = new Random();
        randomized = r;
        statusEffectsOn = se;
        activeEffects = new ArrayList<StatusEffect>();
    }

    /**
     * Plays a round of the game.
     * 
     * @param action attack or swap
     * @return the report of the play
     */
    public String play(String action) {
        String report = "";
        Player player;
        Player opponentPlayer;

        // The player and the opposing player will be decided by the current turn.
        if (turn % 2 == 1) {
            player = playerA;
            opponentPlayer = playerB;
        } else {
            player = playerB;
            opponentPlayer = playerA;
        }

        // Assign the active cards accordingly.
        Card playerCurrent = player.getActiveCard();
        Card opponentPlayerCurrent = opponentPlayer.getActiveCard();
        

        // Updates and lets the active status effect take effect on the active card.
        if (statusEffectsOn) {
            if (statusEffectsOn) {
                // Let the status take effect.
                for (StatusEffect se : activeEffects) {
                    if (se.getCard() == opponentPlayerCurrent) {
                        report += se.takeEffect();
                    }
                }
                
                // Remove every status effect that ran out of duration.
                for (int i = 0; i < activeEffects.size(); i++) {
                    StatusEffect se = activeEffects.get(i); 
                    if (se.getDuration() == 0) {
                        activeEffects.remove(se); 
                        playerCurrent.setHasStatusEffect(false);
                        i--;
                    }
                }
            }
        }

        // Lets the action take place.
        if (action.equalsIgnoreCase("swap")) { // The action is swap. 
            String message = player.swap();
            report += "   " + player.getName() + " swaps out " + playerCurrent.getName() + ".\n" + message;
        } else if (action.equalsIgnoreCase("attack")) { // The action is attack.
            report += "   " + player.getName() + " attacks with " + playerCurrent.getName() + ".\n";
            report += dealDamage(playerCurrent, opponentPlayerCurrent);
        }

        // If an opponent's card dies, the opponent will discard the card.
        if (opponentPlayerCurrent.getHealth() <= 0) {
            opponentPlayer.discard();
            
            report += "   " + opponentPlayer.getName() + " discards " + opponentPlayerCurrent.getName() + ".\n";

            // Determine which card the opponent will draw to replace the discarded card.
            if (deck.size() == 0) { // If the deck is empty, there will be no cards to draw.
                report += "   " + "The deck is empty.\n";
            } else {
                if (deck.size() >= 2) {
                    Card firstCard = deck.get(0);
                    Card secondCard = deck.get(1);
                    int dp1 = firstCard.getPower() * firstCard.getHealth();
                    int dp2 = secondCard.getPower() * secondCard.getHealth();

                    // If the first card has the higher "Determining Product," then draw it. Else, draw the second.
                    if (dp1 >= dp2) {
                        opponentPlayer.drawCard(firstCard);
                        report += "   " + opponentPlayer.getName() + " draws " + deck.get(0).getName() + ".\n";
                        deck.remove(0);
                        deck.add(secondCard);
                    } else {
                        opponentPlayer.drawCard(secondCard);
                        report += "   " + opponentPlayer.getName() + " draws " + deck.get(1).getName() + ".\n";
                        deck.remove(1);
                        deck.add(firstCard);
                    }

                    // Removes the other card.
                    deck.remove(0);

                } else { // Draw the remaining card if there is only 1 left.
                    report += "   " + opponentPlayer.getName() + " draws " + deck.get(0).getName() + ".\n";
                    opponentPlayer.drawCard(deck.get(0));
                    deck.remove(0);
                }
            }
            
            // Adds a token to the player.
            player.claimToken();

            report += "   " + player.getName() + " gets a token!\n";

            // If the player has 3 tokens, then the player wins.
            if (player.getTokens() >= TOKENS_REQUIRED) {
                hasWinner = true;

                report += player.getName() + " wins!!!\n";
            }
        }
        
        // Keep adding a turn, unless there is already a winner.
        if (!hasWinner) {
            turn += 1;
        }

        return report + "\n";
    }

    // 
    /**
     * Checks for the resistance of the first type to the second.
     * 
     * @param type1 type of the receiving card
     * @param type2 type of the attacking card
     * @return true if the receiving card is resistant to the attacking card
     */
    public boolean checkResistance(String type1, String type2) {
        return (type1.equals("Dragon") && type2.equals("Ghost")) ||
                (type1.equals("Ghost") && type2.equals("Fairy")) ||
                (type1.equals("Fairy") && type2.equals("Dragon"));
    }

    /**
     * Checks for the weakness of the first type to the second (The second type is the one attacking). 
     * 
     * @param type1 type of the receiving card
     * @param type2 type of the attacking card
     * @return true if the receiving card is weak to the attacking card
     */
    public boolean checkWeakness(String type1, String type2) {
        return ((type1.equals("Dragon") && type2.equals("Fairy")) ||
                (type1.equals("Fairy") && type2.equals("Ghost")) ||
                (type1.equals("Ghost") && type2.equals("Dragon")) ||
                (type1.equals("Human")) ||
                (type2.equals("Human")))
                && !(type1.equals("Human") && type2.equals("Human")); // The two opposing cards must not be both human.
    }

    /**
     * Gives a player a card from the assembled deck.
     * 
     * @return the report of giving the card.
     */
    public String dealCard() {
        String report = "";

        Player player;
        if (turn % 2 == 1) {
            player = playerA;
        } else {
            player = playerB;
        }

        if (player.handIsFull()) { // Player hand is full.
            report += player.getName() + "'s hand is full.\n\n";
        } else { // Player hand is not full, can still draw a card.
            int index = 0;
            if (randomized) {
                index = rand.nextInt(deck.size());
            }
            player.drawCard(deck.get(index));

            report += player.getName() + " draws " + deck.get(index).getName() + ".\n\n";

            deck.remove(index);
            turn += 1;
        }

        return report;
    }

    /**
     * Deals damage to the opposing card while considering the card's resistance and weaknesses.
     * 
     * @param inPlay the attacking card
     * @param target the opposing card
     * @return a report of the attack
     */
    public String dealDamage(Card inPlay, Card target) {
        String report = "";

        int power = inPlay.getPower();
        String inPlayType = inPlay.getType();
        String targetType = target.getType();

        // Checks the resistance and weakness of the targeted type, and adjusts the damage accordingly.
        if (checkResistance(targetType, inPlayType)) {
            report += "      " + targetType + " is resistant to " + inPlayType + ".\n";
            power /= 2;
        } else if (checkWeakness(targetType, inPlayType)) {
            report += "      " + targetType + " is weak to " + inPlayType + ".\n";
            power *= 2;
        }

        target.takeDamage(power);
        report += "   " + inPlay.getName() + " deals " + power + " damage on " + target.getName() + ".\n";

        // Only inflict status effect if activated, target does not have any, and attacker is not human (a human does not inflict status effects).
        if (statusEffectsOn && !target.hasStatusEffect() && !inPlayType.equals("Human")) {
            activeEffects.add(new StatusEffect(inPlayType, target));

            String statusName = "";
            if (inPlayType.equals("Fairy")) {
                statusName = "Poisoned";
            } else if (inPlayType.equals("Dragon")) {
                statusName = "On Fire";
            } else {
                statusName = "Lurked";
            }

            report += "   *" + target.getName() + " has been inflicted with " + statusName + "!\n";
            target.setHasStatusEffect(true);
        }

        int remainingHealth = target.getHealth();
        report += "   " + target.getName() + " has " + remainingHealth + " health left.\n";
        return report;
    }

    /**
     * Checks if the game already has a winner.
     * 
     * @return true if the game already has a winner.
     */
    public boolean hasWinner() {
        return hasWinner;
    }

    /**
     * Returns a string of the entire game report.
     * 
     * @return the game report
     */
    public String gameReport() {
        String report = "---=== GAME SUMMARY ===---\nThis game lasted " + turn + " turns.\n\n";
        report += playerA.statusReport() + playerB.statusReport();
        return report;
    }

    /**
     * Returns the current amount of turns the game has.
     * For the GameGUI add-on.
     * 
     * @return current turns
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Returns the arrayList of the active status effects.
     * For the GameGUI add-on.
     * 
     * @return arrayList of active status effects
     */
    public ArrayList<StatusEffect> getCurrentStatusEffects() {
        return activeEffects;
    }

    /**
     * Returns whether or not the status effects add-on is active.
     * For the GameGUI add-on.
     * 
     * @return true if status effects on is active
     */
    public boolean statusEffectsEnabled() {
        return statusEffectsOn;
    }
}
