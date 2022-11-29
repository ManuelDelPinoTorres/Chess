/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package delpino_torres_manuel_practica_ajedrez;

import java.io.File;

/**
 *
 * @author Usuario
 */
public class DelPino_Torres_Manuel_Practica_Ajedrez {

    /**
     * @param args the command line arguments
     */
   // public static void main(String[] args) {
        File fichero = new File("jugada.txt");
        Posicion inicial = new Posicion(0, 0);
        Posicion finalP = new Posicion(0, 1);
        Posicion horizontal = new Posicion(6, 7);
        Posicion diagonal = new Posicion(4, 1);

        Tablero t = new Tablero();

        //t.leerPartida(fichero);
       /* System.out.println(t);
        System.out.println(t.ponerReinaSegura(inicial, Color.N));
        System.out.println(t.ponerReinaSegura(finalP, Color.B));
        */
        

        //System.out.println(t.jugar(false));
        //System.out.println(t);
        //System.out.println(t.jugar(true));
        //System.out.println(t);
   // }

}
