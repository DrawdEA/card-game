/**
	The StatusEffect handles the status effects inflicted on the cards at play. 
    
    These status effects include:
    1. Poisoned - Inflicted by Fairy cards. Deals 10 damage to the inflicted card for 5 turns.
    2. On Fire - Inflicted by Dragon cards. deals 6 damage to the inflicted card for 3 turns, doubling the damage each turn.
    3. Lurked - Inflicted by Ghost cards. Deals no damage for the first 2 turns, and deals 50 damage on the third.

    This class is made for the purposes of fulfilling add-on X: Mystery Mode.
	
	@author Edward Joshua M. Diesta (241571)
	@version November 28, 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/
public class StatusEffect {
    private static final int POISON_DAMAGE = 10;
    private static final int ON_FIRE_STARTING_DAMAGE = 6;
    private static final int ON_FIRE_DAMAGE_INCREMENT = 2;
    private static final int LURKED_STARTING_DAMAGE = 0;
    private static final int LURKED_FINAL_DAMAGE = 50;
    private static final int POISON_DURATION = 5;
    private static final int ON_FIRE_DURATION = 3;
    private static final int LURKED_DURATION = 3;

    private Card card;
    private String type;
    private int power;
    private int duration;
    private String abbreviation;

    /**
     * Creates a status effect, assigned to a card.
     * 
     * @param t type of the card inflicting the status effect
     * @param c the card being inflicted with the status effect
     */
    public StatusEffect(String t, Card c) {
        card = c;

        if (t.equals("Fairy")) {
            duration = POISON_DURATION;
            power = POISON_DAMAGE;
            type = "Poisoned";
            abbreviation = "PSND";
        } else if (t.equals("Dragon")) {
            duration = ON_FIRE_DURATION;
            power = ON_FIRE_STARTING_DAMAGE;
            type = "On Fire";
            abbreviation = "BRND";
        } else {
            duration = LURKED_DURATION;
            power = LURKED_STARTING_DAMAGE;
            type = "Lurked";
            abbreviation = "LRKD";
        }
    }

    /**
     * Get the status effect to take effect, taking 1 duration.
     * 
     * @return a report of the status taking effect
     */
    public String takeEffect() {
        String report = "";
        card.takeDamage(power);
        duration -= 1;

        if (duration >= 0) {
            if (type.equals("Poisoned")) {
                report += "   *" + card.getName() + " is poisoned, taking " + power + " damage!\n";
            } else if (type.equals("On Fire")) {
                report += "   *" + card.getName() + " is on fire, taking " + power + " damage!\n";
                power *= ON_FIRE_DAMAGE_INCREMENT; // Increase the damage of the fire.
            } else {
                report += "   *" + card.getName() + " is lurked, taking " + power + " damage!\n";
                if (duration == 1) {power = LURKED_FINAL_DAMAGE;} // Only deal the damage on the last duration.
            } 
        }
        
        return report;
    }

    /**
     * Returns the card that is being inflicted by the status effect.
     * 
     * @return the card with the status effect
     */
    public Card getCard() {
        return card;
    }

    /**
     * Returns the duration left of the status effect.
     * 
     * @return the remaining duration of the status effect
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the abbreviation of the status effect.
     * For the GameGUI add-on.
     * 
     * @return the status effect's abbreviation
     */
    public String getAbbreviataion() {
        return abbreviation;
    }
}
