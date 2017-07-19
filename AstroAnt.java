/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonisation;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Bc. Stanislav Brandejs
 */
public class AstroAnt implements Comparable<AstroAnt> {

    @Override
    public int compareTo(AstroAnt a) {
        return this.position - a.position;
    }

    public enum Colony {
        BLUE, GREEN
    }
    private Text txt;
    private final Colony colony;
    private final char id;
    private static int numOfAnts = 0;
    private static final char DEFAULT_CHAR = 'A';
    private int position;

    public void resetNums() {
        numOfAnts = 0;
    }

    public AstroAnt() {

        if (numOfAnts >= 6) {
            numOfAnts = 0;
        }

        if (numOfAnts >= 3) {
            colony = Colony.BLUE;
        } else {
            colony = Colony.GREEN;
        }
        id = (char) (DEFAULT_CHAR + numOfAnts);
        numOfAnts++;
        position = numOfAnts;

        System.out.println("AstroAnt with designation " + id + " from colony " + colony.name() + " just landed on position " + position);

        initText();

    }

    private void initText() {
        txt = new Text(String.valueOf(getId()));
        txt.setFont(Font.font("Verdana", 20));
        if (getColony() == Colony.BLUE) {

            getTxt().setStrokeWidth(2);
            getTxt().setStroke(Color.BLUE);
        } else {

            getTxt().setStrokeWidth(2);
            getTxt().setStroke(Color.GREEN);
        }

    }

    public void reportStatus() {
        System.out.println("Astroant " + getId() + " from colony " + getColony().name() + " is at position " + getPosition());
    }

    public void moveForwardAndSwitchWithAnt(AstroAnt ant, boolean report) {
        if (this.getPosition() == (ant.getPosition() - 1) && this.getPosition() < 6 && ant.getPosition() > 1) {
            switchMove(this, ant);
            System.out.println("Movement between ant " + this.getId() + " and " + ant.getId() + " sucessfull");
            if (report) {
                this.reportStatus();
                ant.reportStatus();
            }
        } else {
            System.out.println("This move is impossible");
        }
    }

    private void switchMove(AstroAnt antF, AstroAnt antB) {
        antF.position++;
        antB.position--;

    }

    /**
     * @return the txt
     */
    public Text getTxt() {
        return txt;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return the id
     */
    public char getId() {
        return id;
    }

    /**
     * @return the colony
     */
    public Colony getColony() {
        return colony;
    }

}
