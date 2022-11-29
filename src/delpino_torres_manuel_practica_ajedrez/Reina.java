/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package delpino_torres_manuel_practica_ajedrez;

/**
 *
 * @author Neizan
 */
public class Reina extends Pieza {

    public Reina(Posicion posicion, Color color) {
        super(posicion, color);
    }

    private boolean movimientoVertical(Posicion posicion) {
        return super.getPosicion().getColumna() == posicion.getColumna() && super.getPosicion().getFila() != posicion.getFila();
    }

    private boolean movimientoHorizontal(Posicion posicion) {
        return super.getPosicion().getColumna() != posicion.getColumna() && super.getPosicion().getFila() == posicion.getFila();
    }

    private boolean movimientoDiagonal(Posicion posicion) {
        //diagonal de izquierda a derecha

        if (Math.abs(super.getPosicion().getColumna() - super.getPosicion().getFila()) == Math.abs(posicion.getColumna() - posicion.getFila())) {
            return true;
        } else if (Math.abs(super.getPosicion().getColumna() + super.getPosicion().getFila()) == Math.abs(posicion.getColumna() + posicion.getFila())) {
            //diagonal derecha a izquierda
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean atacaPos(Posicion posicion) {
        return this.movimientoDiagonal(posicion) || this.movimientoHorizontal(posicion) || this.movimientoVertical(posicion);
    }

    private boolean esColor(Pieza pieza) {
        return pieza.getColor() != this.getColor();
    }

    @Override
    public boolean atacaPieza(Pieza pieza) {
        return this.atacaPos(pieza.getPosicion()) && this.esColor(pieza);
    }

    @Override
    public boolean mover(Posicion posicion) {
        if (this.atacaPos(posicion)) {
            super.setPosicion(posicion);
            return true;
        }
        return false;
    }

    private String colorDePieza() {
        if (super.getColor() == Color.B) {
            return "Blanca";
        } else {
            return "Negra";
        }

    }

    @Override
    public String toString() {
        return "Reina{Posicion: " + super.getPosicion() + " Color de pieza: " + this.colorDePieza() + '}';
    }

}
