package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bloques {
    
    private Image img;
    private double x;
    private double y;
    private double angulo;
    
    public Bloques(double x, double y) {
        this.x = x;
        this.y = y;
        this.angulo = 0;
        img = Herramientas.cargarImagen("bloqueDestructible.jpg");
    }
    
    public void dibujarse(Entorno entorno)
    {
        entorno.dibujarRectangulo(this.x, this.y, 38, 38, 0, Color.CYAN);
        entorno.dibujarImagen(img, this.x, this.y, angulo, 0.15);
    }
    
    public static Bloques[] crearFilaDeBloques(int cant, double x, double y, double espacio) {
        Bloques[] filaDeBloques = new Bloques[cant];	// Se agrega un array de bloques del tamaño dado en cant
        for (int i = 0; i < cant; i++) {
            filaDeBloques[i] = new Bloques(x + i * espacio, y);		// Se crea un nuevo bloque con sus respectivas medidas	
        }
        return filaDeBloques;
    }
    
    public static Bloques[] crearMultiplesFilasDeBloques(int filas, int cantPorFila, double x, double y, double espacioX, double espacioY) {
        Bloques[] multiplesFilas = new Bloques[filas * cantPorFila];	// Se agrega un array de filas (cada una del tamaño dado en cantPorFila)
        int pos = 0;	// Posicion en el array
        for (int i = 0; i < filas; i++) {
            Bloques[] fila = crearFilaDeBloques(cantPorFila, x, y - i * espacioY, espacioX);	// Se crea una fila
            for (int j = 0; j < fila.length; j++) {
                multiplesFilas[pos++] = fila[j];	// Se asigna el bloque actual al array multiplesFilas
            }
        }
        return multiplesFilas;
    }
}


