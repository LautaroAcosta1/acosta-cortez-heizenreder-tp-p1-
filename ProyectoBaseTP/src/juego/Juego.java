package juego;

import entorno.*;

public class Juego extends InterfaceJuego
{
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    
    // Variables y métodos propios de cada grupo
    // ...
    private Fondo fondo;
    private Bloques[] bloques;
    
    Juego()
    {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Attack on Titan, Final Season - Grupo ... - v1", 800, 600);
        
        // Inicializar lo que haga falta para el juego
        // ...
        
        // Fondo
        fondo = new Fondo(400, 300);
        
        // Filas de bloques
        this.bloques = Bloques.crearMultiplesFilasDeBloques(4, 21, 18, 580, 39.5, 146);

        // Inicia el juego!
        this.entorno.iniciar();
    }

    public void tick()
    {
        // Procesamiento de un instante de tiempo
        // ...
    	// Fondo
        fondo.dibujarse(entorno);
        
        // Filas de bloques
        for (int i = 0; i < bloques.length; i++) {
            bloques[i].dibujarse(entorno);
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}


