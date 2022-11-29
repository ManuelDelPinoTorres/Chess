package delpino_torres_manuel_practica_ajedrez;

public abstract class Pieza {

    private Posicion posicion;
    private Color color;

    public Pieza(Posicion posicion, Color color) {
        this.setPosicion(posicion);
        this.setColor(color);
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract boolean atacaPos(Posicion posicion);

    public abstract boolean atacaPieza(Pieza pieza);

    public abstract boolean mover(Posicion posicion);

}
