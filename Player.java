/**
 * The Player class is responsible for handling the properties of the two players in the game
 * including their name, hand, the hand's status (full or not), amount of tokens, and returning a report of the player.
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

public class Player {
    private String name;
    private int tokens;
    ArrayList<Card> hand;
    private boolean fullHand;

   /**
    * Constructs a new player object given the name. Initializes tokens to 0, and creates an empty hand to the player.

    * @param n the name of the player.
    */
    public Player(String n) {
        name = n;
        tokens = 0;
        hand = new ArrayList<Card>();
        fullHand = false;
    }

    /**
     * Adds card c to the hand. If player has 5 cards on hand, then it is full. Method is only called when hand isn't full.
     * 
     * @param c the card to be added in the player's hand.
     */
    public void drawCard(Card c) {
        hand.add(c);
        if (hand.size() >= 5) {
            fullHand = true;
        }
    }

    /**
     * Removes the active card on hand (Index 0). Only called when a card in hand is eliminated. 
     * Adds card in position sets to null left after discarding if there are no more cards left.
     */
    public void discard() {
        hand.remove(0);
        if (hand.size() == 0) {
            hand.add(null);
        }
        fullHand = false;
    }

    /**
     * Returns the highest "Determining Product" of the inactive cards, which is calculated as the power multiplied by their health. 
     * This method is used by the swap() method.
     * 
     * @return the index of the card of the highest "Determining Product."
     */
    private int findCard() {
        int highest = 0;
        int highestIndex = -1;
        for (int i = 1; i < hand.size(); i++) {
            Card c = hand.get(i);
            int determiningProduct = c.getPower() * c.getHealth();
            if (determiningProduct > highest) {
                highest = determiningProduct;
                highestIndex = i;
            }
        }

        return highestIndex;
    }

    /**
     * Swaps the active card (Index 0) with the card with the highest "Determining Product".
     * 
     * @return the report of the swap.
     */
    public String swap() {
        if (hand.size() >= 2) {
            int index = findCard();
            Card tmp = hand.get(0);
            hand.set(0, hand.get(index));
            hand.set(index, tmp);
            tmp = hand.get(0);

            return "   " + tmp.getName() + " is now active with " + tmp.getHealth() + " health.\n";
        } else {
            return "   " + name + " has no other card to swap with. Turn forfeited.\n";
        }
    }

    /**
     * Adds a token to the player.
     */
    public void claimToken() {
        tokens += 1;
    }

    /**
     * Returns the active card (Which is at index 0 of the player's hand).
     * 
     * @return the active card of the player
     */
    public Card getActiveCard() {
        return hand.get(0);
    }

    /**
     * Returns the name of the player.
     * 
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of tokens the player has.
     * 
     * @return token amount
     */
    public int getTokens() {
        return tokens;
    }
    /**
     * Returns whether or not the player's hand has 5 cards.
     * 
     * @return true if the player's hand is full, false otherwise
     */
    public boolean handIsFull() {
        return fullHand;
    }
    /**
     * Returns the name of the player and their hand, with the names and the remaining health of the cards therein.
     * 
     * @return a string of the status report
     */
    public String statusReport() {
        String status = name.toUpperCase() + "\n";
        for (Card c : hand) {
            if (!(c == null)) {
                status += String.format("%11s : %3s\n", c.getName(), c.getHealth());
            }
            
        }

        return status;
    }

    /**
     * Returns the player's hand.
     * For the GameGUI add-on.
     * @return the current hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    };
}
