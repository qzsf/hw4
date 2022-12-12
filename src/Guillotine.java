
/**
 * JavaFX Application: Guillotine
 *
 * Player: 2
 *
 * To start the game:
 * java Guillotine      Start a game with two players and default 20 "person" cards in the line.
 * java Guillotine 16   Start a game with two players and 16 "person" cards in the line.
 *
 * @author Alan Zhang
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.Iterator;
import java.util.List;

public class Guillotine extends Application {
    // Linked list holds main deck of cards
    LinkedList<Card> list = new LinkedList<Card>();
    // Score for player 1
    int score1;
    // Score for player 2
    int score2;
    // default number of cards
    int numOfCards = 20;
    // Player1's action box
    VBox leftActions = new VBox();
    // Player2's action box
    VBox rightActions = new VBox();
    // Flag for game start
    boolean gameStarted = false;

    /**
     * Entry point for the JavaFX application
     * 
     * @param primaryStage - Stage
     */
    @Override
    public void start(Stage primaryStage) {
        // Get number of cards to play with
        Parameters params = getParameters();
        List<String> list = params.getRaw();
        if (list.size() > 0) {
            numOfCards = Integer.parseInt(list.get(0));
        }
        // Set up BorderPane as the root
        BorderPane root = new BorderPane();
        // call buildUI
        buildUI(root);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Guillotine");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(550);
        primaryStage.setMinHeight(400);
        primaryStage.show();

    }

    /**
     * Build three sections of the game
     * 
     * @param root - pass in the borderpane
     */
    private void buildUI(BorderPane root) {

        buildLeft(root);
        buildRight(root);
        buildCenter(root, list);
    }

    /**
     * Build the left panel
     * 
     * @param root - the borderpane
     */
    private void buildLeft(BorderPane root) {
        // Linked list for Player1's collected cards
        LinkedList<Card> leftList = new LinkedList<>();
        VBox vBox = new VBox();
        VBox collected = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(collected);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Text
        Text playerName = new Text("Player 1");
        Text score = new Text("Score: 0");
        Text collectedTitle = new Text("People Collected:");

        // Assemble the left pane
        vBox.getChildren().add(playerName);
        vBox.getChildren().add(score);
        vBox.getChildren().add(leftActions);
        vBox.getChildren().add(collectedTitle);
        vBox.getChildren().add(scrollPane);

        // Action buttons
        Button left1 = createButton("Move Front Back 4");
        left1.setOnAction(event -> {
            list.moveBack(4);
            left1.setDisable(true); // disable after used
            buildCenter(root, list); // update card list
        });
        Button left2 = createButton("Move Front Back 3");
        left2.setOnAction(event -> {
            list.moveBack(3);
            left2.setDisable(true);
            buildCenter(root, list);
        });
        Button left3 = createButton("Move Front Back 2");
        left3.setOnAction(event -> {
            list.moveBack(2);
            left3.setDisable(true);
            buildCenter(root, list);
        });
        Button left4 = createButton("Move Front Back 1");
        left4.setOnAction(event -> {
            list.moveBack(1);
            left4.setDisable(true);
            buildCenter(root, list);
        });
        Button left5 = createButton("Move Front to End");
        left5.setOnAction(event -> {
            list.moveFirstToLast();
            left5.setDisable(true);
            buildCenter(root, list);
        });
        Button left6 = createButton("Move Last Person to Front");
        left6.setOnAction(event -> {
            list.moveLastToFirst();
            left6.setDisable(true);
            buildCenter(root, list);
        });
        Button left7 = createButton("Reverse Line");
        left7.setOnAction(event -> {
            list.reverseList();
            left7.setDisable(true);
            buildCenter(root, list);
        });
        Button left8 = createButton("Reverse First 5");
        left8.setOnAction(event -> {
            list.reverseFirstK(5);
            left8.setDisable(true);
            buildCenter(root, list);
        });
        Button left9 = createButton("Skip Turn");
        left9.setOnAction(event -> {
            left9.setDisable(true);
            leftActions.setDisable(true);
            rightActions.setDisable(false);
        });
        Button left10 = createButton("Take Front Person");
        left10.setOnAction(event -> {
            gameStarted = true;
            Card card = list.removeFromFront();
            score1 = calculateScore(score1, leftList, card);
            leftList.addToEnd(card);
            System.out.println(leftList.length());
            collected.getChildren().clear(); // reload collected
            collected.getChildren().add(buildList(leftList));
            score.setText("Score: " + score1);
            buildCenter(root, list); // update card list
            leftActions.setDisable(true); // disable player1
            rightActions.setDisable(false); // enable player2
        });

        // Add action buttons to action box
        leftActions.getChildren().add(left1);
        leftActions.getChildren().add(left2);
        leftActions.getChildren().add(left3);
        leftActions.getChildren().add(left4);
        leftActions.getChildren().add(left5);
        leftActions.getChildren().add(left6);
        leftActions.getChildren().add(left7);
        leftActions.getChildren().add(left8);
        leftActions.getChildren().add(left9);
        leftActions.getChildren().add(left10);

        root.setLeft(vBox);
    }

    /**
     * Calculate player's score
     * 
     * @param score - current score
     * @param list  - player's collected cards
     * @param card  - the card just took
     * @return - the calculated score
     */
    private int calculateScore(int score, LinkedList<Card> list, Card card) {
        // The card's points
        int point = card.getPoint();
        // Player's number of cards in each group
        int numOfChurchMembers = 0;
        int numOfCivicPersons = 0;
        int numOfCommoners = 0;
        int numOfGuards = 0;

        // Loop and sort/count number for each group
        Iterator<Card> it = list.iterator();
        while (it.hasNext()) {
            Card temp = it.next();
            // numbers of members in different groups
            switch (temp.getGroup()) {
                case Civic:
                    numOfCivicPersons++;
                    break;
                case Church:
                    numOfChurchMembers++;
                    break;
                case Commoner:
                    numOfCommoners++;
                    break;
                default:
            }
            if (temp.getName().equals("Palace Guard"))
                numOfGuards++;

            if (card.getName().equals("Count") && temp.getName().equals("Countess"))
                point = 4;
            if (card.getName().equals("Countess") && temp.getName().equals("Count"))
                point = 4;
            if (card.getName().equals("Lord") && temp.getName().equals("Lady"))
                point = 4;
            if (card.getName().equals("Lady") && temp.getName().equals("Lord"))
                point = 4;
        }

        // Points for special cards
        if (card.getName().equals("Heretic"))
            point = numOfChurchMembers;
        if (card.getName().equals("Tax Collector"))
            point = numOfCivicPersons;
        if (card.getName().equals("Tragic Figure"))
            point = -numOfCommoners;
        if (card.getName().equals("Palace Guard")) {
            point = numOfGuards;
        }

        // Add new points to score and return
        score = score + point;
        return score;
    }

    /**
     * Utility method to build a card list
     * 
     * @param list - linked list as data
     * @return - a box that contains cards
     */
    private VBox buildList(LinkedList<Card> list) {
        VBox vBox = new VBox();
        // Loop through the list to create a list of cards
        Iterator<Card> it = list.iterator();
        while (it.hasNext()) {
            Card card = it.next();
            String point = String.valueOf(card.getPoint());
            if (point.equals("0"))
                point = "*";
            Button button = createButton(card.getName() + "\n" + point);
            button.setMinHeight(70);
            switch (card.getGroup()) {
                case Military:
                    button.setStyle("-fx-border-color: red;-fx-border-width:10px;-fx-border-insets: 5px;");
                    break;
                case Civic:
                    button.setStyle("-fx-border-color: green;-fx-border-width:10px;-fx-border-insets: 5px;");
                    break;
                case Royal:
                    button.setStyle("-fx-border-color: purple;-fx-border-width:10px;-fx-border-insets: 5px;");
                    break;
                case Church:
                    button.setStyle("-fx-border-color: blue;-fx-border-width:10px;-fx-border-insets: 5px;");
                    break;
                case Commoner:
                    button.setStyle("-fx-border-color: grey;-fx-border-width:10px;-fx-border-insets: 5px;");
                    break;
                default:
            }
            vBox.getChildren().add(button);
        }
        return vBox;
    }

    /**
     * Method - build the right pane for player2 same as buildLeft
     * 
     * @param root - the borderpane
     */
    private void buildRight(BorderPane root) {
        LinkedList<Card> rightList = new LinkedList<>();
        VBox vBox = new VBox();
        // VBox rightActions = new VBox();
        VBox collected = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(collected);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Text playerName = new Text("Player 2");
        Text score = new Text("Score: 0");
        Text collectedTitle = new Text("People Collected:");

        vBox.getChildren().add(playerName);
        vBox.getChildren().add(score);
        vBox.getChildren().add(rightActions);
        vBox.getChildren().add(collectedTitle);
        vBox.getChildren().add(scrollPane);

        Button right1 = createButton("Move Front Back 4");
        right1.setOnAction(event -> {
            list.moveBack(4);
            right1.setDisable(true);
            buildCenter(root, list);
        });
        Button right2 = createButton("Move Front Back 3");
        right2.setOnAction(event -> {
            list.moveBack(3);
            right2.setDisable(true);
            buildCenter(root, list);
        });
        Button right3 = createButton("Move Front Back 2");
        right3.setOnAction(event -> {
            list.moveBack(2);
            right3.setDisable(true);
            buildCenter(root, list);
        });
        Button right4 = createButton("Move Front Back 1");
        right4.setOnAction(event -> {
            list.moveBack(1);
            right4.setDisable(true);
            buildCenter(root, list);
        });
        Button right5 = createButton("Move Front to End");
        right5.setOnAction(event -> {
            list.moveFirstToLast();
            right5.setDisable(true);
            buildCenter(root, list);
        });
        Button right6 = createButton("Move Last Person to Front");
        right6.setOnAction(event -> {
            list.moveLastToFirst();
            right6.setDisable(true);
            buildCenter(root, list);
        });
        Button right7 = createButton("Reverse Line");
        right7.setOnAction(event -> {
            list.reverseList();
            right7.setDisable(true);
            buildCenter(root, list);
        });
        Button right8 = createButton("Reverse First 5");
        right8.setOnAction(event -> {
            list.reverseFirstK(5);
            right8.setDisable(true);
            buildCenter(root, list);
        });
        Button right9 = createButton("Skip Turn");
        right9.setOnAction(event -> {
            right9.setDisable(true);
            leftActions.setDisable(false);
            rightActions.setDisable(true);
        });
        Button right10 = createButton("Take Front Person");
        right10.setOnAction(event -> {
            gameStarted = true;
            Card card = list.removeFromFront();
            score2 = calculateScore(score2, rightList, card);
            rightList.addToEnd(card);
            System.out.println(rightList.length());
            collected.getChildren().clear();
            collected.getChildren().add(buildList(rightList));
            score.setText("Score: " + score2);

            buildCenter(root, list);
            leftActions.setDisable(false);
            rightActions.setDisable(true);
        });

        rightActions.getChildren().add(right1);
        rightActions.getChildren().add(right2);
        rightActions.getChildren().add(right3);
        rightActions.getChildren().add(right4);
        rightActions.getChildren().add(right5);
        rightActions.getChildren().add(right6);
        rightActions.getChildren().add(right7);
        rightActions.getChildren().add(right8);
        rightActions.getChildren().add(right9);
        rightActions.getChildren().add(right10);

        root.setRight(vBox);
    }

    /**
     * Method - build the center section
     * 
     * @param root - the borderpane
     * @param list - the linked list for the main deck
     */
    private void buildCenter(BorderPane root, LinkedList<Card> list) {
        // initialize all possible cards
        if (list.length() == 0 && !gameStarted) {
            list.addToEnd(new Card("King Louis XIV", Group.Royal, 5));
            list.addToEnd(new Card("Marie Antoinette", Group.Royal, 5));
            list.addToEnd(new Card("Regent", Group.Royal, 4));
            list.addToEnd(new Card("Duke", Group.Royal, 3));
            list.addToEnd(new Card("Baron", Group.Royal, 3));
            list.addToEnd(new Card("Count", Group.Royal, 2));
            list.addToEnd(new Card("Countess", Group.Royal, 2));
            list.addToEnd(new Card("Lord", Group.Royal, 2));
            list.addToEnd(new Card("Lady", Group.Royal, 2));
            list.addToEnd(new Card("Cardinal", Group.Church, 5));
            list.addToEnd(new Card("Archbishop", Group.Church, 4));
            list.addToEnd(new Card("Nun", Group.Church, 3));
            list.addToEnd(new Card("Bishop", Group.Church, 2));
            list.addToEnd(new Card("Priest", Group.Church, 1));
            list.addToEnd(new Card("Priest", Group.Church, 1));
            list.addToEnd(new Card("Heretic", Group.Church, 0));
            list.addToEnd(new Card("Governor", Group.Civic, 4));
            list.addToEnd(new Card("Mayor", Group.Civic, 3));
            list.addToEnd(new Card("Councilman", Group.Civic, 3));
            list.addToEnd(new Card("Judge", Group.Civic, 2));
            list.addToEnd(new Card("Judge", Group.Civic, 2));
            list.addToEnd(new Card("Tax Collector", Group.Civic, 0));
            list.addToEnd(new Card("Sheriff", Group.Civic, 1));
            list.addToEnd(new Card("Sheriff", Group.Civic, 1));
            list.addToEnd(new Card("Palace Guard", Group.Military, 0));
            list.addToEnd(new Card("Palace Guard", Group.Military, 0));
            list.addToEnd(new Card("Palace Guard", Group.Military, 0));
            list.addToEnd(new Card("Palace Guard", Group.Military, 0));
            list.addToEnd(new Card("Palace Guard", Group.Military, 0));
            list.addToEnd(new Card("General", Group.Military, 4));
            list.addToEnd(new Card("Colonel", Group.Military, 3));
            list.addToEnd(new Card("Captain", Group.Military, 2));
            list.addToEnd(new Card("Lieutenant", Group.Military, 1));
            list.addToEnd(new Card("Lieutenant", Group.Military, 1));
            list.addToEnd(new Card("Tragic Figure", Group.Commoner, 0));
            list.addToEnd(new Card("Heroic Figure", Group.Commoner, -3));
            list.addToEnd(new Card("Student", Group.Commoner, -1));
            list.addToEnd(new Card("Student", Group.Commoner, -1));
            list.addToEnd(new Card("Student", Group.Commoner, -1));
            list.addToEnd(new Card("Student", Group.Commoner, -1));

            // shuffle the main deck
            list.shuffle();
            // then take cards default to 20
            list.take(numOfCards);
        }

        // put cards into a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(buildList(list));
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // display winner when game is done
        if (list.length() == 0 && gameStarted) {
            VBox box = new VBox();
            Text text = new Text();
            text.setFont(Font.font("Verdana", 24));
            if (score1 > score2)
                text.setText("Player 1 Won!");
            else if (score1 < score2)
                text.setText("Player 2 Won!");
            else
                text.setText("It's a Tie.");

            box.getChildren().add(text);
            scrollPane.setContent(box);
        }

        root.setCenter(scrollPane);
    }

    /**
     * Utility method to create a button card
     * 
     * @param text - set button text
     * @return - button
     */
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMinWidth(150);
        button.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    /**
     * Entry point for Java application
     * 
     * @param args - arguments
     */
    public static void main(String[] args) {

        launch(args);
    }
}