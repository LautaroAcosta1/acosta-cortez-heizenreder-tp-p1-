package juego;

import entorno.*;

public class Juego extends InterfaceJuego
{
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    
    // Variables y métodos propios de cada grupo
    // ...
    private Fondo fondo;
    private Bloques[] primerFila;
    private Bloques[] bloques;
    private Princesa princesa;
    private Colisionador colisionador;
    
    Juego()
    {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Attack on Titan, Final Season - Grupo ... - v1", 800, 600);
        
        // Inicializar lo que haga falta para el juego
        // ...
        
        // Fondo
        fondo = new Fondo(400, 300);
        
        // Filas de bloques
        this.primerFila = Bloques.crearFilaDeBloques(21, 18, 580, 39.5);
        this.bloques = Bloques.crearMultiplesFilasDeBloques(4, 10, 18, 434, 39.5, 146);
        
        // Princesa
        this.princesa = new Princesa(400, 525);
        
        // Colisionador
        this.colisionador = new Colisionador();

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
        for (int i = 0; i < primerFila.length; i++) {	// Primer fila de bloques (piso).
			primerFila[i].dibujarBloqueDestructible(entorno);
		}
        
        
        int contBloques = 0;
        for (int i = 0; i < bloques.length; i++) {
            if (contBloques < 5) {
                bloques[i].dibujarBloqueDestructible(entorno);
                contBloques++;
            } else {
                for (int j = 0; j < 3; j++) {	// Dibuja tres bloques indestructibles
                    bloques[i + j].dibujarBloqueIndestructible(entorno);                   
                }
                i += 2;// Se incrementa i en 2 para saltar los bloques ya dibujados
                contBloques = 0;	// Se reinicia el contador al dibujar 3 bloques indestructibles
            }
        }
        
        // Princesa
        princesa.dibujarse(entorno);
        colisionador.manejarColisiones(princesa, primerFila);
        colisionador.manejarColisiones(princesa, bloques);
        
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			princesa.setDireccion(false);
			princesa.moverIzquierda();
		}
		
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			princesa.setDireccion(true);
			princesa.moverDerecha();
		}
		
		if(entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			princesa.saltar();
		}
		
		
    }

    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}


