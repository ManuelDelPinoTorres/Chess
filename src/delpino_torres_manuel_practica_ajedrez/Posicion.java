package delpino_torres_manuel_practica_ajedrez;

public class Posicion {

    private int fila;
    private int columna;

    public Posicion(int fila, int columna) {
        this.setFila(fila);
        this.setColumna(columna);
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int filaIntroducida) throws TableroException {
        if (filaIntroducida < 0) {
            throw new TableroException("No se puede introducir filas negativas");
        } else if (filaIntroducida > 7) {
            throw new TableroException("El valor de la fila no puede ser mayor a 7");
        } else {
            this.fila = filaIntroducida;
        }
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columnaIntroducida) throws TableroException {
         if ( columnaIntroducida < 0) {
            throw new TableroException("No se puede introducir columnas negativas");
        } else if (columnaIntroducida > 7) {
            throw new TableroException("El valor de la columna no puede ser mayor a 7");
        } else {
            this.columna = columnaIntroducida;
        }
    }

    public boolean equals(Posicion posicion) {
        return this.columna == posicion.columna && this.fila == posicion.fila;
    }

    public String toString() {
        return "X: " + this.fila + " Y: " + this.columna;
    }

}
