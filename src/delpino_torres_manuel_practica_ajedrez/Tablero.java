package delpino_torres_manuel_practica_ajedrez;

import delpino_torres_manuel_practica_ajedrez.Reina;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tablero {

    private final int TAMANIO = 8;
    private Pieza[][] tablero;
    private ArrayList<Pieza> piezaAtaque;
    private ArrayList<Pieza> piezaAtacada;
    private Color turno;

    /*Me he decantado por el uso de una matriz como estructura para crear el tablero
    ya que coincide no la forma de un tablero de ajedrez y me va a permitir moverme
    por el usando las posiciones que ya hemos creado con las clases anteriores
     */
    public Tablero() {
        this.tablero = new Pieza[this.TAMANIO][this.TAMANIO];
        this.piezaAtaque = new ArrayList<>();
        this.piezaAtacada = new ArrayList<>();
        this.setTurno(Color.B);
    }

    public void setTurno(Color turno) {
        this.turno = turno;
    }

    public Color getTurno() {
        return turno;
    }

    public Pieza[][] getTablero() {
        return tablero;
    }

    public void setTablero(Pieza[][] tablero) {
        this.tablero = tablero;
    }

    public ArrayList<Pieza> getPiezaAtaque() {
        return piezaAtaque;
    }

    public void setPiezaAtaque(ArrayList<Pieza> piezaAtaque) {
        this.piezaAtaque = piezaAtaque;
    }

    public ArrayList<Pieza> getPiezaAtacada() {
        return piezaAtacada;
    }

    public void setPiezaAtacada(ArrayList<Pieza> piezaAtacada) {
        this.piezaAtacada = piezaAtacada;
    }

    public boolean posOcupada(Posicion posicion) {
        return this.tablero[posicion.getFila()][posicion.getColumna()] != null;
    }
//devolvemos 0 en caso de que se puedan añadir las piezas y una excepcion en caso de que no

    public int ponerCaballo(Posicion posicion, Color color) throws TableroException {
        if (!this.posOcupada(posicion)) {
            this.tablero[posicion.getFila()][posicion.getColumna()] = new Caballo(posicion, color);
            return 0;
        }

        throw new TableroException("No puedes colocar una pieza en un posicion ocupada");
    }

    public int ponerCaballoSeguro(Posicion posicion, Color color) {
        if (!this.hayCaballoAtacandoPosicion(posicion, color) && !this.hayReinaAtacandoPosicion(posicion, color)) {
            this.tablero[posicion.getFila()][posicion.getColumna()] = new Caballo(posicion, color);
            return 0;
        }
        throw new TableroException("No puedes colocar un caballo en esta posicion ya que puede ser atacado");
    }

    public int ponerReina(Posicion posicion, Color color) throws TableroException {
        if (!this.posOcupada(posicion)) {
            this.tablero[posicion.getFila()][posicion.getColumna()] = new Reina(posicion, color);
            return 0;
        }
        throw new TableroException("No puedes colocar una pieza en un posicion ocupada");
    }

    public int ponerReinaSegura(Posicion posicion, Color color) throws TableroException {
        if (!this.hayCaballoAtacandoPosicion(posicion, color) && !this.hayReinaAtacandoPosicion(posicion, color)) {
            this.tablero[posicion.getFila()][posicion.getColumna()] = new Reina(posicion, color);
            return 0;
        }
        throw new TableroException("No puedes colocar un caballo en esta posicion ya que puede ser atacado");
    }

    public int eliminarPieza(Posicion posicion) throws TableroException {
        if (this.posOcupada(posicion)) {
            this.tablero[posicion.getFila()][posicion.getColumna()] = null;
            return 0;
        }
        throw new TableroException("La posicion ya se encontraba vacia");
    }

    //metodos para comprobar los posibles ataques de la reina y el caballo
    private boolean comprobarHorizontalReinaPosicionSegura(Posicion posicion, Color color) {
        boolean esReinaPeligrosa = false;
        for (int i = 0; i < this.tablero.length; i++) {
            if (this.tablero[posicion.getFila()][i] != null) {
                if (this.tablero[posicion.getFila()][i].getClass() == Reina.class && this.tablero[posicion.getFila()][i].getColor() != color) {
                    esReinaPeligrosa = true;
                }
            }
        }
        return esReinaPeligrosa;
    }

    private boolean comprobarDiagonalReinaPosicionSegura(Posicion posicion, Color color) {
        boolean esReinaPeligrosa = false;
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero[i].length; j++) {
                //diagonal izquierda a derecha
                if ((Math.abs(posicion.getColumna() - posicion.getFila())) == (Math.abs(i - j)) || (Math.abs(posicion.getColumna() + posicion.getFila())) == (Math.abs(i + j))) {
                    if (this.tablero[i][j] != null) {
                        if (this.tablero[i][j].getClass() == Reina.class && this.tablero[i][j].getColor() != color) {
                            esReinaPeligrosa = true;
                        }
                    }
                }
            }
        }
        return esReinaPeligrosa;
    }

    private boolean comprobarVerticalReinaPosicionSegura(Posicion posicion, Color color) {
        //Reina r = new Reina(posicion, Color.N);
        boolean esReinaPeligrosa = false;
        for (int i = 0; i < this.tablero.length; i++) {
            if (this.tablero[i][posicion.getColumna()] != null) {
                if (this.tablero[i][posicion.getColumna()].getClass() == Reina.class && this.tablero[i][posicion.getColumna()].getColor() != color) {
                    esReinaPeligrosa = true;
                }
            }
        }
        return esReinaPeligrosa;
    }

    private boolean hayReinaAtacandoPosicion(Posicion posicion, Color color) {
        return this.comprobarDiagonalReinaPosicionSegura(posicion, color) || this.comprobarHorizontalReinaPosicionSegura(posicion, color) || this.comprobarVerticalReinaPosicionSegura(posicion, color);

    }

    private boolean hayCaballoAtacandoPosicion(Posicion posicion, Color color) {
        boolean esCaballoPeligroso = false;
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero[i].length; j++) {
                //comprobamos en el primer if el movimiento 1 del caballo y en el segundo el movimiento 2 caballo
                if (i == posicion.getFila() - 1 || i == posicion.getFila() + 1) {
                    if (j == posicion.getColumna() - 2 || j == posicion.getColumna() + 2) {
                        if (this.tablero[i][j] != null) {
                            if (this.tablero[i][j].getClass() == Caballo.class && this.tablero[i][j].getColor() != color) {
                                esCaballoPeligroso = true;
                            }
                        }
                    }
                } else if (i == posicion.getFila() - 2 || i == posicion.getFila() + 2) {
                    if (j == posicion.getColumna() - 1 || j == posicion.getColumna() + 1) {
                        if (this.tablero[i][j] != null) {
                            if (this.tablero[i][j].getClass() == Caballo.class && this.tablero[i][j].getColor() != color) {
                                esCaballoPeligroso = true;
                            }
                        }
                    }
                }
            }
        }
        return esCaballoPeligroso;
    }
//usamos codigos unicode para representar el tablero y fichas
//las filas impares son blancas y las pares negras.

    public String toString() {
        String tablero = "\n";

        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero[i].length; j++) {
                boolean esPrimeraCasillaBlanca = false;
                if (this.tablero[i][j] == null) {
                    if ((i + 1) % 2 != 0) {
                        esPrimeraCasillaBlanca = true;
                    }

                    if (esPrimeraCasillaBlanca) {
                        if (j % 2 != 0) {
                            tablero += "\u2B1B";
                        } else {
                            tablero += "\u25A1";
                        }
                    } else {
                        if (j % 2 != 0) {
                            tablero += "\u25A1";
                        } else {
                            tablero += "\u2B1B";
                        }
                    }

                } else {
                    if (this.tablero[i][j].getClass() == Reina.class) {
                        if (this.tablero[i][j].getColor() == Color.B) {
                            tablero += "\u2655";
                        } else {
                            tablero += "\u265B";
                        }

                    } else {
                        if (this.tablero[i][j].getColor() == Color.B) {
                            tablero += "\u2658";
                        } else {
                            tablero += "\u265E";
                        }

                    }
                }
            }
            tablero += "\n";
        }
        tablero += "\n";
        return tablero;
    }

    public void leerPartida(File nombreFichero) throws TableroException {
        String linea = "";
        String items[];
        try {
            FileReader lector = new FileReader(nombreFichero);
            BufferedReader buffer = new BufferedReader(lector);
            linea = buffer.readLine();

            while (linea != null) {
                if (!linea.isEmpty()) {
                    items = linea.split(" ");
                    if (items[0].equalsIgnoreCase("Reina")) {
                        if (items[1].equalsIgnoreCase("blanca")) {
                            this.ponerReina(new Posicion(Integer.parseInt(items[2]), Integer.parseInt(items[3])), Color.B);
                        } else {
                            this.ponerReina(new Posicion(Integer.parseInt(items[2]), Integer.parseInt(items[3])), Color.N);
                        }
                    } else {
                        if (items[1].equalsIgnoreCase("blanca")) {
                            this.ponerCaballo(new Posicion(Integer.parseInt(items[2]), Integer.parseInt(items[3])), Color.B);
                        } else {
                            this.ponerCaballo(new Posicion(Integer.parseInt(items[2]), Integer.parseInt(items[3])), Color.N);
                        }
                    }
                }
                linea = buffer.readLine();
            }
            buffer.close();
            lector.close();
        } catch (IOException e) {
            throw new TableroException("Error al introducir el fichero");
        }
    }

    public Posicion puedeAtacarCaballo(Posicion posicion) {
        Posicion primerAtaqueCaballo = null;
        for (int i = 0; i < this.tablero.length && primerAtaqueCaballo == null; i++) {
            for (int j = 0; j < this.tablero[i].length && primerAtaqueCaballo == null; j++) {
                //comprobamos en el primer if el movimiento 1 del caballo y en el segundo el movimiento 2 caballo
                if (i != posicion.getFila() && j != posicion.getColumna()) {
                    if (i == posicion.getFila() - 1 || i == posicion.getFila() + 1) {
                        if (j == posicion.getColumna() - 2 || j == posicion.getColumna() + 2) {
                            if (this.tablero[i][j] != null && this.tablero[i][j].getColor() == Color.N) {
                                primerAtaqueCaballo = new Posicion(i, j);
                            }
                        }
                    } else if (i == posicion.getFila() - 2 || i == posicion.getFila() + 2) {
                        if (j == posicion.getColumna() - 1 || j == posicion.getColumna() + 1) {
                            if (this.tablero[i][j] != null && this.tablero[i][j].getColor() == Color.N) {
                                primerAtaqueCaballo = new Posicion(i, j);
                            }
                        }
                    }
                }
            }
        }
        return primerAtaqueCaballo;
    }

    public Posicion comprobarAtaqueDiagonalReina(Posicion posicion, Color color) {
        Posicion posicionPiezaAtacarDiagonal = null;
        for (int i = 0; i < this.tablero.length && posicionPiezaAtacarDiagonal == null; i++) {
            for (int j = 0; j < this.tablero[i].length && posicionPiezaAtacarDiagonal == null; j++) {
                //diagonal izquierda a derecha
                if (i != posicion.getFila() && j != posicion.getColumna()) {
                    if ((Math.abs(posicion.getColumna() - posicion.getFila())) == (Math.abs(i - j)) || (Math.abs(posicion.getColumna() + posicion.getFila())) == (Math.abs(i + j))) {
                        if (this.tablero[i][j] != null && this.tablero[i][j].getColor() != color) {
                            posicionPiezaAtacarDiagonal = new Posicion(i, j);
                        }
                    }
                }
            }
        }
        return posicionPiezaAtacarDiagonal;
    }

    public Posicion comprobarAtaqueVerticalReina(Posicion posicion, Color color) {
        Posicion primeraPosicionAtacarVertical = null;
        for (int i = 0; i < this.tablero.length && primeraPosicionAtacarVertical == null; i++) {
            if (i != posicion.getFila()) {
                if (this.tablero[i][posicion.getColumna()] != null && this.tablero[i][posicion.getColumna()].getColor() != color) {
                    primeraPosicionAtacarVertical = new Posicion(i, posicion.getColumna());
                }
            }
        }
        return primeraPosicionAtacarVertical;
    }

    public Posicion comprobarAtaqueHorizontalReina(Posicion posicion, Color color) {
        Posicion primeraPosicionAtacarHorizontal = null;
        for (int i = 0; i < this.tablero.length && primeraPosicionAtacarHorizontal == null; i++) {
            if (i != posicion.getColumna()) {
                if (this.tablero[posicion.getFila()][i] != null && this.tablero[posicion.getFila()][i].getColor() != color) {
                    primeraPosicionAtacarHorizontal = new Posicion(posicion.getFila(), i);
                }
            }
        }
        return primeraPosicionAtacarHorizontal;

    }

    /*Con este metodo comprobamos si la reina puede atacar, en caso de que pueda atacar varias posiciones
    primero atacaria la diagonal,en caso contrario atacaria la vertical y por ultimo atacaria la horizontal*/
    public Posicion puedeAtacarReina(Posicion posicion, Color color) {
        Posicion primeraPosicionAtacada = null;
        if (this.comprobarAtaqueDiagonalReina(posicion, color) != null) {
            primeraPosicionAtacada = this.comprobarAtaqueDiagonalReina(posicion, color);
        } else if (this.comprobarAtaqueVerticalReina(posicion, color) != null) {
            primeraPosicionAtacada = this.comprobarAtaqueVerticalReina(posicion, color);
        } else if (this.comprobarAtaqueHorizontalReina(posicion, color) != null) {
            primeraPosicionAtacada = this.comprobarAtaqueHorizontalReina(posicion, color);
        }
        return primeraPosicionAtacada;
    }

    public Posicion primeraPiezaAtacada(Color color) {
        Posicion posicionFinalAtacar = null;

        for (int i = 0; i < this.tablero.length && posicionFinalAtacar == null; i++) {
            for (int j = 0; j < this.tablero[i].length && posicionFinalAtacar == null; j++) {
                if (this.tablero[i][j] != null) {
                    if (this.tablero[i][j].getColor() == color) {
                        /*en caso de que una reina y un caballo puedan atacar siempre tendra preferencia la reina*/
                        Posicion posicionComprobar = new Posicion(i, j);
                        //bucamos las piezas que puedan atacar y comprobamos si devuelve posicion para ello
                        if (this.tablero[i][j].getClass() == Reina.class && this.puedeAtacarReina(posicionComprobar, color) != null) {
                            //guardamos la posicion que puede ser atacada
                            posicionFinalAtacar = this.puedeAtacarReina(posicionComprobar, color);
                            //guardamos la posicion del atacante y la del atacado antes de que estas cambien
                            this.piezaAtaque.add(this.tablero[i][j]);
                            this.piezaAtacada.add(this.tablero[posicionFinalAtacar.getFila()][posicionFinalAtacar.getColumna()]);
                            //eliminamos la posicion atacada
                            this.eliminarPieza(posicionFinalAtacar);
                            //ponemos la pieza que ataca
                            this.ponerReina(posicionFinalAtacar, color);
                            //eliminamos su posicion anterior
                            this.eliminarPieza(this.tablero[i][j].getPosicion());
                        } else if (this.tablero[i][j].getClass() == Caballo.class && this.puedeAtacarCaballo(posicionComprobar) != null) {
                            posicionFinalAtacar = this.puedeAtacarCaballo(posicionComprobar);
                            this.piezaAtaque.add(this.tablero[i][j]);
                            this.piezaAtacada.add(this.tablero[posicionFinalAtacar.getFila()][posicionFinalAtacar.getColumna()]);
                            this.eliminarPieza(posicionFinalAtacar);
                            this.ponerCaballo(posicionFinalAtacar, color);
                            this.eliminarPieza(this.tablero[i][j].getPosicion());

                        }
                    }
                }
            }
        }
        return posicionFinalAtacar;
    }

    public boolean esVacioTablero() {
        boolean esVacio = true;
        for (int i = 0; i < this.tablero.length && esVacio; i++) {
            for (int j = 0; j < this.tablero[i].length && esVacio; j++) {
                if (this.tablero[i][j] != null) {
                    esVacio = false;
                }
            }
        }
        return esVacio;
    }

    public boolean hayBlancas() {
        boolean hayBlanca = false;
        for (int i = 0; i < this.tablero.length && !hayBlanca; i++) {
            for (int j = 0; j < this.tablero[i].length && !hayBlanca; j++) {
                if (this.tablero[i][j] != null) {
                    if (this.tablero[i][j].getColor() == Color.B) {
                        hayBlanca = true;
                    }
                }
            }
        }
        return hayBlanca;
    }

    public boolean hayNegras() {
        boolean hayNegra = false;
        for (int i = 0; i < this.tablero.length && !hayNegra; i++) {
            for (int j = 0; j < this.tablero[i].length && !hayNegra; j++) {
                if (this.tablero[i][j] != null) {
                    if (this.tablero[i][j].getColor() == Color.N) {
                        hayNegra = true;
                    }
                }
            }
        }
        return hayNegra;
    }

    public boolean ataqueTurnoColor() {
        if (this.turno == Color.N) {
            if (this.primeraPiezaAtacada(this.turno) != null) {
                this.setTurno(Color.B);
                return true;
            }
            return false;

        } else {
            if (this.primeraPiezaAtacada(this.turno) != null) {
                this.setTurno(Color.N);
                return true;
            }
            return false;
        }
    }

    public String colorPiezaAtaque(Color color) {
        if (color == Color.N) {
            return "Negra";
        }
        return "Blanca";
    }

    public String modoDetallado() {
        if (this.hayNegras() && this.hayBlancas()) {
            if (this.ataqueTurnoColor()) {
                return this.getPiezaAtaque().get(this.getPiezaAtaque().size() - 1).getClass().getSimpleName() + " " + this.colorPiezaAtaque(this.getPiezaAtaque().get(this.getPiezaAtaque().size() - 1).getColor()) + " en la posicion " + this.getPiezaAtaque().get(this.getPiezaAtaque().size() - 1).getPosicion() + " ,ha atacado a " + this.getPiezaAtaque().get(this.getPiezaAtaque().size() - 1).getClass().getSimpleName() + " " + this.colorPiezaAtaque(this.getPiezaAtacada().get(this.getPiezaAtacada().size() - 1).getColor()) + " en la posicion " + this.getPiezaAtacada().get(this.getPiezaAtacada().size() - 1).getPosicion();
            }
            return "No hay pieza de para atacar";
        } else {
            if (!this.hayNegras()) {
                return "No quedan piezas negras";
            } else {
                return "No quedan piezas blancas";
            }
        }
    }

    public String modoSinDetalle() {
        if (this.hayNegras() && this.hayBlancas()) {
            if (this.ataqueTurnoColor()) {
                return "Ataque realizado";
            }
            return "No hay pieza de para atacar";
        } else {
            if (!this.hayNegras()) {
                return "No quedan piezas negras";
            } else {
                return "No quedan piezas blancas";
            }
        }
    }

    public String jugar(boolean detallado) {
        //empiezo con el turno en color blanco
        if (!this.esVacioTablero()) {
            if (detallado) {
                return this.modoDetallado();
            } else {
                return this.modoSinDetalle();
            }
        } else {
            return "No se puede jugar el tablero esta vacio";
        }

    }

    public void resetearTablero() {
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero[i].length; j++) {
                this.tablero[i][j] = null;
            }
        }
    }

}
