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
                if (princesa.getPiso() <= bloques[i].getPiso() /*&& princesa.getPiso() > bloques[i].getTecho()*/) {		// Colision entre el piso de la princesa y el piso del bloque
                    princesa.setY(bloques[i].getTecho() - princesa.getAlto() / 2);		// Se ubica a la princesa justo encima del bloque
                	princesa.velocidadSalto(0);
                    princesa.setEstaSaltando(false);
                    princesa.setEstaApoyado(true);
                    estaApoyada = true;                    
                    break;
                }
                
                if (princesa.getTecho() <= bloques[i].getPiso()) {		// Colision entre el techo de la princesa y el piso del bloque
                	if(!bloques[i].isdestrucible()) {		// Si No es destructibles...
                    	princesa.setY(bloques[i].getPiso() + princesa.getAlto() / 2);
                        princesa.velocidadSalto(0);
                        princesa.setEstaSaltando(false);
                        princesa.setEstaApoyado(true);
                	} else {		// Si es destructible...
                		princesa.setY(bloques[i].getPiso() + princesa.getAlto() / 2);
                		princesa.velocidadSalto(0);
                        princesa.setEstaSaltando(false);
                        princesa.setEstaApoyado(true);
                        bloques[i].isdestrucible();
                	}
                }
            }
        }

        princesa.setEstaApoyado(estaApoyada);	// Se informa si la princesa esta sobre un bloque o no
    }
    
}
