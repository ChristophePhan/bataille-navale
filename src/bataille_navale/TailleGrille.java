/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bataille_navale;

/**
 * TailleGrille
 * @author Chayem Samy, Neret Tristan, Phan Christophe
 */
public class TailleGrille {
    private int x;
    private int y;
    
    public TailleGrille(int x, int y){
        this.x = x;
        this.y = y;
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
    
}
