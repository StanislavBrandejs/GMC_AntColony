/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonisation;



import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Bc. Stanislav Brandejs
 */
public class Colonisation extends Application {

    List<AstroAnt> ants;
    GridPane gp;
    TextArea ta;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    //Save the old System.out!
   // PrintStream old = System.out;

    @Override
    public void start(Stage primaryStage) {
        System.setOut(ps);
        Scene scene = new Scene(initPane(), 450, 600);
        primaryStage.setTitle("Coloantisation!");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private AnchorPane initPane() {

        gp = new GridPane();
        gp.setLayoutY(550);
        gp.setLayoutX(170);
        ta = new TextArea();
        ta.setMinHeight(500);
        ta.setMinWidth(400);
        ta.setPrefWidth(450);
        ta.setLayoutY(25);

        Button btn = new Button();
        btn.setLayoutX(0);
        btn.setText("Deploy Ant colony");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                deployColony(gp);
                ta.setText(baos.toString());
                fillGridPane(gp);
                ta.end();
            }
        });
        Button btn2 = new Button();
        btn2.setLayoutX(140);
        btn2.setLayoutY(0);
        btn2.setText("Move forward");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                System.out.println("------------------------");
                Collections.sort(ants);
                for (int i = ants.size() - 1; i >= 1; i--) {
                    if (ants.get(i).getColony() != ants.get(i - 1).getColony() && ants.get(i - 1).getColony().equals(AstroAnt.Colony.GREEN)) {
                        ants.get(i - 1).moveForwardAndSwitchWithAnt(ants.get(i), true);

                    }

                }

                ta.setText(baos.toString());

                fillGridPane(gp);
                ta.end();

            }
        });

        Button btnA = new Button();
        btnA.setLayoutX(300);
        btnA.setLayoutY(0);
        btnA.setText("A ->");
        btnA.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                manageMovement('A');
                ta.setText(baos.toString());

                fillGridPane(gp);
                ta.end();

            }
        });

        Button btnB = new Button();
        btnB.setLayoutX(350);
        btnB.setLayoutY(0);
        btnB.setText("B ->");
        btnB.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                manageMovement('B');
                ta.setText(baos.toString());

                fillGridPane(gp);
                ta.end();

            }
        });

        Button btnC = new Button();
        btnC.setLayoutX(400);
        btnC.setLayoutY(0);
        btnC.setText("C ->");
        btnC.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                manageMovement('C');
                ta.setText(baos.toString());

                fillGridPane(gp);
                ta.end();

            }
        });

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(btn, btn2, btnA, btnB, btnC, ta, gp);
        return root;

    }

    private void deployColony(GridPane gridpane) {
        baos.reset();
        if (ants != null) {
            for (AstroAnt ant : ants) {
                gridpane.getChildren().remove(ant.getTxt());
            }
        }
        ants = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            ants.add(new AstroAnt());
        }
    }

    // This method is used for movement of single ants forward without comparables and sorting methods. 
    private void manageMovement(char ch) {

        AstroAnt forwardAnt = null, backwardAnt = null;
        int backwardAntPos = 0;

        for (int i = 0; i < ants.size(); i++) {
            if (ants.get(i).getId() == ch && ants.get(i).getPosition() < 6) {
                forwardAnt = ants.get(i);
                backwardAntPos = ants.get(i).getPosition() + 1;
            }
        }

        for (int i = 0; i < ants.size(); i++) {
            if (ants.get(i).getPosition() == backwardAntPos && backwardAntPos > 0) {
                backwardAnt = ants.get(i);

            }
        }

        if (forwardAnt != null && backwardAnt != null) {
            forwardAnt.moveForwardAndSwitchWithAnt(backwardAnt, true);
        } else {
            System.out.println("You can't move " + ch + " any further");
        }

    }

    private void fillGridPane(GridPane gridpane) {

        for (AstroAnt ant : ants) {
            gridpane.getChildren().remove(ant.getTxt());
            gridpane.add(ant.getTxt(), ant.getPosition(), 0);
        }
    }

}
