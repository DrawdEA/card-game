/**
 * The GameGUI class handles setting up the entirety of the GUI of the card game, 
 * including setting up the components, layouts, buttons, and event listeners.
 * 
 * This class is made for the purposes of fulfilling add-on X: Mystery Mode.
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameGUI {
    private GameMaster gameMaster;

    private JFrame frame;

    private JToggleButton randomizeToggle;
    private JToggleButton newDeckToggle;
    private JToggleButton statusEffectsToggle;

    private JPanel panelGame;
    private JPanel panelPregame;
    private JPanel panelCustomize;
    private JPanel panelPlayers;
    private JPanel panelStart;
    private JPanel panelGameArea;

    private JPanel panelButton;
    private JPanel panelText;
    private JPanel panelP1Cards;
    private JPanel panelP2Cards;
    private JPanel panelP1Bar;
    private JLabel nameP1;
    private JPanel cardNamesP1;
    private JPanel statusP1;

    private JLabel[] player1CardNames = new JLabel[5];
    private JLabel[] player2CardNames = new JLabel[5];

    private JPanel panelP2Bar;
    private JLabel nameP2;
    private JPanel cardNamesP2;
    private JPanel statusP2;

    private JPanel panelChoices;

    private JTextArea output;
    private JLabel prompt;
    private JLabel labelP1;
    private JLabel labelP2;
    private JTextField inputP1;
    private JTextField inputP2;
    private JButton attack;
    private JButton swap;
    private JButton startButton;

    private JLabel[] player1Cards = new JLabel[5];
    private JLabel[] player2Cards = new JLabel[5];

    private int width;
    private int height;

    private Player player1;
    private Player player2;

    /**
     * Sets up the visuals of the card, including its image, name, type, health, power, and position in the hand.
     * 
     * @param imageLabel the actual card object
     * @param t the health and power of the card
     * @param type the type of the card
     * @param cardName the container for the name of the card
     * @param name the name of the card
     * @param i the position of the card in the player's hand
     */
    private void setUpCard(JLabel imageLabel, String t, String type, JLabel cardName, String name, int i) {
        imageLabel.setText(t);
        imageLabel.setForeground(Color.white);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.TOP); 
        imageLabel.setIconTextGap(-22);
        imageLabel.setFont(new Font("Helvetica", Font.BOLD, 13));

        // Set up the name of card.
        cardName.setText("[" + i + "] " + name);
        cardName.setHorizontalAlignment(JLabel.CENTER);

        // Set up the images depending on the type of the card.
        if (type.equals("Dragon")) {
            imageLabel.setIcon(new ImageIcon("resources/Dragon.png"));
        } else if (type.equals("Fairy")) {
            imageLabel.setIcon(new ImageIcon("resources/Fairy.png"));
        } else if (type.equals("Ghost")) {
            imageLabel.setIcon(new ImageIcon("resources/Ghost.png"));
        } else if (type.equals("Human")) {
            imageLabel.setIcon(new ImageIcon("resources/Human.png"));
        } else {
            imageLabel.setIcon(new ImageIcon("resources/Blank.png"));
        }
    }

    /**
     * Instantiates the components of the GUI and up height and width.
     * 
     * @param h height of the GUI
     * @param w width of the GUI
     */
    public GameGUI(int h, int w) {

        frame = new JFrame();

        randomizeToggle = new JToggleButton("Randomize Starting Hand");
        newDeckToggle = new JToggleButton("Add Your New Deck");
        statusEffectsToggle = new JToggleButton("Add Status Effects");

        for (int i = 0; i < 5; i++) {
            player1Cards[i] = new JLabel();
            player2Cards[i] = new JLabel();
            player1CardNames[i] = new JLabel();
            player2CardNames[i] = new JLabel();
        }

        panelGame = new JPanel();
        panelPregame = new JPanel();
        panelCustomize = new JPanel();
        panelPlayers = new JPanel();
        panelGameArea = new JPanel();
        panelButton = new JPanel();
        panelStart = new JPanel();
        panelText = new JPanel();
        panelP1Cards = new JPanel();
        panelP2Cards = new JPanel();
        panelP1Bar = new JPanel();
        panelP2Bar = new JPanel();
        panelChoices = new JPanel();

        cardNamesP1 = new JPanel();
        cardNamesP2 = new JPanel();
        statusP1 = new JPanel();
        statusP2 = new JPanel();

        nameP1 = new JLabel("Player1's Hand [Tokens: 0]");
        nameP2 = new JLabel("Player2's Hand [Tokens: 0]");

        prompt = new JLabel("  Turn 0: Attack or Swap?  ");

        inputP1 = new JTextField(10);
        inputP2 = new JTextField(10);

        labelP1 = new JLabel("Player 1:");
        labelP2 = new JLabel("     Player 2:");

        attack = new JButton("Attack");
        swap = new JButton("Swap");
        startButton = new JButton("Start Game");

        output = new JTextArea(35, 22);

        width = w;
        height = h;
    }

    /**
     * Set up the GUI from the instantiated components, including their layout, position, and order.
     */
    public void setUpGUI() {
        Container cp = frame.getContentPane();
        cp.setBackground(Color.black);
        BorderLayout border = new BorderLayout();
        cp.setLayout(border);
        frame.setSize(width, height);
        frame.setTitle("Card Game");

        // Set up the pregame layout.
        BorderLayout pregameBorder = new BorderLayout();
        panelGame.setLayout(pregameBorder);
        panelGame.setBackground(Color.green);

        GridLayout buttonGrid = new GridLayout(3, 1);
        panelPregame.setLayout(buttonGrid);

        FlowLayout flow = new FlowLayout();
        panelCustomize.setLayout(flow);
        panelCustomize.add(randomizeToggle);
        panelCustomize.add(newDeckToggle);
        panelCustomize.add(statusEffectsToggle);

        panelPlayers.setLayout(flow);
        panelPlayers.add(labelP1);
        panelPlayers.add(inputP1);
        panelPlayers.add(labelP2);
        panelPlayers.add(inputP2);

        panelStart.setLayout(flow);
        panelStart.add(startButton);

        panelPregame.add(panelCustomize);
        panelPregame.add(panelPlayers);
        panelPregame.add(panelStart);

        // Set up the card visual part.
        BorderLayout gameAreaBorder = new BorderLayout();
        panelGameArea.setLayout(gameAreaBorder);

        // Set up player 1 and 2 bar.
        BorderLayout p1Border = new BorderLayout();
        panelP1Bar.setLayout(p1Border);
        statusP1.setLayout(flow);
        statusP1.add(nameP1);
        panelP1Bar.add(cardNamesP1, BorderLayout.SOUTH);
        panelP1Bar.add(statusP1, BorderLayout.CENTER);

        BorderLayout p2Border = new BorderLayout();
        panelP2Bar.setLayout(p2Border);
        statusP2.setLayout(flow);
        statusP2.add(nameP2);
        panelP2Bar.add(cardNamesP2, BorderLayout.NORTH);
        panelP2Bar.add(statusP2, BorderLayout.CENTER);

        // Set up the attack and swap prompt.
        panelChoices.setLayout(flow);
        panelChoices.add(attack);
        panelChoices.add(prompt);
        panelChoices.add(swap);

        // Put them all together.
        BorderLayout gameplayBorder = new BorderLayout();
        panelButton.setLayout(gameplayBorder);
        panelButton.add(panelP1Bar, BorderLayout.SOUTH);
        panelButton.add(panelP2Bar, BorderLayout.NORTH);
        panelButton.add(panelChoices, BorderLayout.CENTER);

        // Set up the output text.
        FlowLayout flowMiddle = new FlowLayout(FlowLayout.LEFT);
        panelText.setLayout(flowMiddle);
        panelText.add(output);

        // Set up the cards.
        GridLayout gridCards = new GridLayout(1,4);
        panelP1Cards.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        panelP2Cards.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        panelP1Cards.setLayout(gridCards);
        panelP2Cards.setLayout(gridCards);

        GridLayout gridNames = new GridLayout(1,4);
        cardNamesP1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        cardNamesP2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        cardNamesP1.setLayout(gridNames);
        cardNamesP2.setLayout(gridNames);

        // Sets up the card visuals to be blank.
        for (int i = 0; i < 5; i++) {
            setUpCard(player1Cards[i], "   ⚔️ -   ❤️ -", "Blank", player1CardNames[i], "Empty", i + 1);
            setUpCard(player2Cards[i], "   ⚔️ -   ❤️ -", "Blank", player2CardNames[i], "Empty", i + 1);

            panelP1Cards.add(player1Cards[i]);
            panelP2Cards.add(player2Cards[i]);
            cardNamesP1.add(player1CardNames[i]);
            cardNamesP2.add(player2CardNames[i]);
        }

        // Set up the game area.
        panelGameArea.add(panelP2Cards, BorderLayout.NORTH);
        panelGameArea.add(panelButton, BorderLayout.CENTER);
        panelGameArea.add(panelP1Cards, BorderLayout.SOUTH);

        // Connect the panels together.
        panelGame.add(panelPregame, BorderLayout.NORTH);
        panelGame.add(panelGameArea, BorderLayout.CENTER);

        // Separate the output area to the game.
        cp.add(panelText, BorderLayout.EAST);
        cp.add(panelGame, BorderLayout.CENTER);
        
        // Set the values for the frame.
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Sets up the button listeners for the actions of the GUI, such as swap, attack, and start game.
     */
    public void setupUpButtonListeners() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object o = ae.getSource();

                if (o == startButton) { // Start the game 
                    String p1 = inputP1.getText();
                    String p2 = inputP2.getText();

                    player1 = new Player(p1);
                    player2 = new Player(p2);

                    if (randomizeToggle.isSelected() || newDeckToggle.isSelected() || statusEffectsToggle.isSelected()) {
                        gameMaster = new GameMaster(player1, player2, randomizeToggle.isSelected(), newDeckToggle.isSelected(), statusEffectsToggle.isSelected());
                    } else {
                        gameMaster = new GameMaster(player1, player2);
                    }

                    for (int i = 0; i < 10; i++) {
                        gameMaster.dealCard();
                    }
                } else {
                    if (!gameMaster.hasWinner()) { // There is already a winner, therefore no need to attack or swap.
                        if (o == swap){ // Swap
                            output.setText(gameMaster.play("swap"));
                        } else { // Attack
                            output.setText(gameMaster.play("attack"));
                        }
                    }
                }

                // Update the visuals of the card, its names, the player's bar, and the prompt.
                nameP1.setText(player1.getName() + "'s Hand [Tokens: " + player1.getTokens() + "]");
                nameP2.setText(player2.getName() + "'s Hand [Tokens: " + player2.getTokens() + "]");
                String currentPlayer = (gameMaster.getTurn() % 2 == 1) ? player1.getName() : player2.getName();
                prompt.setText("  " + currentPlayer + "'s Turn: Attack or Swap? ["  + gameMaster.getTurn() + "]  ");

                // Update the card visuals each turn.
                ArrayList<Card> p1Hand = player1.getHand();
                ArrayList<Card> p2Hand = player2.getHand();
                for (int i = 0; i < 5; i++) {
                    if (p1Hand.size() >= i + 1) {
                        Card c1 = p1Hand.get(i);
                        setUpCard(player1Cards[i], "   ⚔️ " + c1.getPower() + "   ❤️ " + c1.getHealth(), c1.getType(), player1CardNames[i], c1.getName(), i + 1);
                    } else {
                        setUpCard(player1Cards[i], "   ⚔️ -   ❤️ -", "Blank", player1CardNames[i], "Empty", i + 1);
                    }
                    
                    if (p2Hand.size() >= i + 1) {
                        Card c2 = p2Hand.get(i);
                        setUpCard(player2Cards[i], "   ⚔️ " + c2.getPower() + "   ❤️ " + c2.getHealth(), c2.getType(), player2CardNames[i], c2.getName(), i + 1);
                    } else {
                        setUpCard(player2Cards[i], "   ⚔️ -   ❤️ -", "Blank", player2CardNames[i], "Empty", i + 1);
                    }
                }

                // Update the status effects every turn, replacing the index with it.
                if (gameMaster.statusEffectsEnabled()) {
                    for (int i = 0; i < p1Hand.size(); i++) {
                        for (StatusEffect se : gameMaster.getCurrentStatusEffects()) {
                            Card current = p1Hand.get(i);
                            if (se.getCard() == current) {
                                player1CardNames[i].setText("[" + se.getAbbreviataion() +"] " + current.getName());
                            }
                        }
                    }
                    for (int i = 0; i < p2Hand.size(); i++) {
                        for (StatusEffect se : gameMaster.getCurrentStatusEffects()) {
                            Card current = p2Hand.get(i);
                            if (se.getCard() == current) {
                                player2CardNames[i].setText("[" + se.getAbbreviataion() +"] " + current.getName());
                            }
                        }
                    }
                }
                
                // Set the prompt in the middle to whomever won the game if there is a winner.
                if (gameMaster.hasWinner()) {
                    if (gameMaster.getTurn() % 2 == 1) {
                        prompt.setText(player1.getName() + " Wins!!!");
                    } else {
                        prompt.setText(player2.getName() + " Wins!!!");
                    }

                    output.setText(gameMaster.gameReport());
                }
            }
        };

        // Add the action listeners to the buttons.
        attack.addActionListener(buttonListener);
        swap.addActionListener(buttonListener);
        startButton.addActionListener(buttonListener);
    }
}
