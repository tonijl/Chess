
package shakki;



import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;


public class Ruutu {
    
    int x, y;
    int type; // 0 = tyhjä, 1 = valkoinen, 2 = musta
    int nappula; // 0 = tyhjä, 1 = sotilas, 2 = kuningas, 3 = kuningatar, 4 = lähetti, 5 = hevonen, 6 = torni
    int vaaka, pysty;
    boolean selected = false;
    boolean possibleMove = false;
    boolean hasMoved = false;
    

    public Ruutu(){
 
    }

    public Ruutu(int x, int y,int vaaka, int pysty, int type, int nappula) {
        this.x = x;
        this.y = y;
        this.vaaka = vaaka;
        this.pysty = pysty;
        this.type = type;
        this.nappula = nappula;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean isPossibleMove() {
        return possibleMove;
    }

    public void setPossibleMove(boolean possibleMove) {
        this.possibleMove = possibleMove;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNappula() {
        return nappula;
    }

    public void setNappula(int nappula) {
        this.nappula = nappula;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVaaka() {
        return vaaka;
    }

    public void setVaaka(int vaaka) {
        this.vaaka = vaaka;
    }

    public int getPysty() {
        return pysty;
    }

    public void setPysty(int pysty) {
        this.pysty = pysty;
    }
    
    

    
    
    
    
    
    
    
}

