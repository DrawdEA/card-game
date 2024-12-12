/**
 * The GameApp class handles setting up the GUI of the game, through the GameGUI class.
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
public class GameApp {
    public static void main(String[] args) {
        // Set up the GUI
        GameGUI gui = new GameGUI(600, 800);
        gui.setUpGUI();
        gui.setupUpButtonListeners();
    }
}
