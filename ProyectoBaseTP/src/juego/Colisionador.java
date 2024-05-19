package juego;

public class Colisionador {
	
	/**
	 * Verifica si hay colision entre el primer y segundo objeto dado 
	 * @param Princesa El primer objeto a vereficar
	 * @param Bloques El segundo objeto a verificar
	 */
    public static boolean verificarColision(Princesa princesa, Bloques bloque) {
        return princesa.getDerecha() > bloque.getIzquierda() &&
               princesa.getIzquierda() < bloque.getDerecha() &&
               princesa.getPiso() > bloque.getTecho() &&
               princesa.getTecho() < bloque.getPiso();
    }
    
	/**
	 * Verifica si hay colision entre el primer objeto y un array de objetos
	 * @param Princesa El primer objeto a vereficar
	 * @param Bloques El array de objetos a verificar
	 */
    public void manejarColisiones(Princesa princesa, Bloques[] bloques) {
        boolean estaApoyada = false;

        for (int i = 0; i < bloques.length; i++) {		// Se recorre los bloques uno por uno
            if (verificarColision(princesa, bloques[i])) {
                if (princesa.getPiso() > bloques[i].getTecho() && princesa.getTecho() < bloques[i].getPiso()) {
                    princesa.setY(bloques[i].getTecho() - princesa.getAlto() / 2);		// Se ubica a la princesa justo encima del bloque
                    princesa.setEstaSaltando(false);
                    estaApoyada = true;                    
                    break;
                }
            }
        }

        princesa.setEstaApoyado(estaApoyada);	// Se informa si la princesa esta sobre un bloque o no
    }
    
}
