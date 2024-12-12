/**
 * The Card class is responsible for handling each card in the deck, taking account
 * its name, type, health, and power. The class can also reduce the current health of the card.
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
public class Card {
    private String type;
    private String name;
    private int health;
    private int power;
    private boolean hasStatusEffect;

    /**
     * Initializes the instance fields of the card, given the parameters. Also defaults to not having any status effects.
     * 
     * @param t type of the card
     * @param n name of the card
     * @param h the amount of health the card has
     * @param p the amount of power the card has
     */
    public Card(String t, String n, int h, int p) {
        type = t;
        name = n;
        health = h;
        power = p;
        hasStatusEffect = false;
    }

    /**
     * Returns the name of the card.
     * 
     * @return the card's name.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns what type of card the card is (Can be Ghost, Fairy, Dragon or Human).
     * 
     * @return the string Ghost, Fairy, Dragon, or Human
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the power of the card.
     * 
     * @return the card's power in int
     */
    public int getPower() {
        return power;
    }

    /**
     * Returns the remaining health of the card.
     * 
     * @return the card's health in int
     */
    public int getHealth() {
        return health;
    }

    /**
     * Reduces the amount of health the card has by d.
     * 
     * @param d the damage the the card will take
     */
    public void takeDamage(int d) {
        health -= d;
    }

    // 
    /**
     * Checks if the card has a status effect.
     * For the Mystery Mode add-on.
     * 
     * @return true if the card has an active status effect, false otherwise
     */
    public boolean hasStatusEffect() {
        return hasStatusEffect;
    }

    /**
     * Sets the hasStatusEffect boolean to either true or false.
     * For the Mystery Mode add-on.
     * 
     * @param b what to set the hasStatusEffect boolean to
     */
    public void setHasStatusEffect(boolean b) {
        hasStatusEffect = b;
    }
}
