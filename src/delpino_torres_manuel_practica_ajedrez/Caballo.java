package delpino_torres_manuel_practica_ajedrez;

public class Caballo extends Pieza {

    public Caballo(Posicion posicion, Color color) {
        super(posicion, color);
    }

    private boolean comprobarFilaMov1(Posicion posicion) {
        return (posicion.getFila() == super.getPosicion().getFila() - 2) || (posicion.getFila() == super.getPosicion().getFila() + 2);
    }

    private boolean comprobarColumnaMov1(Posicion posicion) {
        return (posicion.getColumna() == super.getPosicion().getColumna() - 1) || (posicion.getColumna() == super.getPosicion().getColumna() + 1);
    }

    private boolean comprobarFilaMov2(Posicion posicion) {
        return (posicion.getFila() == super.getPosicion().getFila() - 1) || (posicion.getFila() == super.getPosicion().getFila() + 1);
    }

    private boolean comprobarColumnaMov2(Posicion posicion) {
        return (posicion.getColumna() == super.getPosicion().getColumna() - 2) || (posicion.getColumna() == super.getPosicion().getColumna() + 2);
    }

    @Override
    public boolean atacaPos(Posicion posicion) {
        return this.comprobarColumnaMov1(posicion) && this.comprobarFilaMov1(posicion) || this.comprobarColumnaMov2(posicion) && this.comprobarFilaMov2(posicion);
    }

    private boolean esColor(Pieza pieza) {
        return pieza.getColor() != this.getColor();
    }

    @Override
    public boolean atacaPieza(Pieza pieza) {
        return this.esColor(pieza) && this.atacaPos(pieza.getPosicion());
    }

    @Override
    public boolean mover(Posicion posicion) {
        if (this.comprobarColumnaMov1(posicion) && this.comprobarFilaMov1(posicion) || this.comprobarColumnaMov2(posicion) && this.comprobarFilaMov2(posicion)) {
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
        return "Caballo{Posicion: " + super.getPosicion() + " Color de pieza: " + this.colorDePieza() + '}';
    }

}
