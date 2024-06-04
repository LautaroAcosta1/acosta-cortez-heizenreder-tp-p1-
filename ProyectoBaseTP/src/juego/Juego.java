package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Entorno entorno;
	private Image fondo, fondoMenu, botonInicio, botonGameover, botonWinner;
	private Disparo disparo;
	private Princesa princesa;
	private Piso[] piso;
	private Coin coin;
	private Magma magma;
	private Dinosaurios[] Dinosaurio;
	int contNull, vidas;
	private int direccionDisparo, dire, puntos, eliminados;
	boolean agachada, menu, victoria, perdio, gano;

	Juego() {
		this.entorno = new Entorno(this, "Super Elizabeth", 800, 600);
		this.fondo = Herramientas.cargarImagen("magma.jpg");
		this.fondoMenu = Herramientas.cargarImagen("magma.jpg");
		this.botonInicio = Herramientas.cargarImagen("magma.jpg");
		this.botonGameover = Herramientas.cargarImagen("gameOver.png");
		this.botonWinner = Herramientas.cargarImagen("winner.png");
		princesa = new Princesa(400, 530);
		coin = new Coin(400, 88, 0.15);
		princesa.dibujarVidas(entorno);
		menu = true;
		puntos = 0;
		vidas = 3;
		eliminados = 0;
		piso = new Piso[5];
		for (int i = 0; i < piso.length; i++) {
			piso[i] = new Piso(152 * (i + 0.5) + 50);
			magma = new Magma(376, 610, 10000, 50);
		}
		
		
		Dinosaurio = new Dinosaurios[6];
		contNull = 0;
		for (int j = 0; j < Dinosaurio.length; j++) {
			double y = 50 + (j / 2) * 120;
			Dinosaurio[j] = new Dinosaurios(obtenerXRandom(entorno), y);
			if (j > 0) {
				if (colisiona(Dinosaurio[j], Dinosaurio[j - 1])) {
					Dinosaurio[j].setX(Dinosaurio[j].getX() + 100);
				}
			}
		}

		this.entorno.iniciar();
	}

	public void tick1() {

		if (menu) {
			entorno.dibujarImagen(fondoMenu, entorno.ancho() / 2, entorno.alto() / 2, 0);
			entorno.dibujarImagen(botonInicio, 400, 230, 0, 1.5);

			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				menu = false;
			}
		}

		if (princesa != null && !menu) {
			entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
			entorno.cambiarFont("Super Mario 256", 15, Color.PINK);
			entorno.escribirTexto("Puntos: " + puntos, 10, 15);
			entorno.escribirTexto("Enemigos eliminados: " + eliminados, 100, 15);

			// Colision entre princesa y tiranosaurio
			for (int i = 0; i < Dinosaurio.length; i++) {
				if (Dinosaurio[i] != null) {
					Dinosaurio[i].setContadorBomba(Dinosaurio[i].getContadorBomba() + 10);
					Dinosaurio[i].disparo(entorno);
					if (princesa != null && chocoCon(princesa.getX(), princesa.getY(), Dinosaurio[i].getX(), Dinosaurio[i].getY(), 50)) {
						princesa.reducirVida();
						if (princesa.getVidas() <= 0) {
							perdio = true;
						}
					}

					// COLISION BOMBA CON LA PRINCESA
					if (Dinosaurio[i].getBomba() != null && Dinosaurio[i].getBomba().colisionaCon(princesa.getX(), princesa.getY(), princesa.getAncho(), princesa.getAlto())) {
						princesa.reducirVida();
						Dinosaurio[i].setBomba(null); // Eliminar bomba después de colisionar
					}
					if (princesa.getVidas() <= 0) {
						perdio = true;
					}
				}
			}

			// Movimiento y acciones de la princesa
			if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
				princesa.direccion = false;
				princesa.mover(entorno);
				direccionDisparo = 1;
			}
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
				princesa.direccion = true;
				princesa.mover(entorno);
				direccionDisparo = 2;
			}

			if (entorno.sePresiono(' ') && !princesa.saltando) {
				princesa.saltando = true;
			}
			
			if (entorno.sePresiono('l')) {
				if (disparo == null) {
					disparo = new Disparo(princesa.getX(), princesa.getY());
					dire = direccionDisparo;
				}
			}

			// Dibujar y mover disparo//
			if (disparo != null) {
				disparo.dibujar(entorno);
				disparo.moverDisparo(dire);
				if (!disparo.estaDentroDelMapa(entorno)) {
					disparo = null;
				}
			}
			
			// Obtencion de la moneda
	        if (coin != null) {
	            if (colisionPRINCESAmoneda(princesa, coin)) {
	                coin = null; // La moneda desaparece
	                gano = true;
	            } else {
	                coin.dibujarCoin(entorno);
	            }
	        }

			// Movimiento y dibujo de la princesa//
			princesa.caerSubir(entorno);
			princesa.dibujar(entorno);

			// Dibujo de los pisos//
			for (int i = 0; i < piso.length; i++) {
				piso[i].dibujar(entorno);
			}

			// Dibujo de las vidas
			princesa.dibujarVidas(entorno);

			// Movimiento y dibujo de los tiranosaurios//
			for (int i = 0; i < Dinosaurio.length; i++) {
				if (Dinosaurio[i] != null) {
					Dinosaurio[i].caerSubir(entorno);
					Dinosaurio[i].mover(entorno);
					Dinosaurio[i].dibujarTiranosaurio(entorno);

					// Detectar colisión entre disparo y tiranosaurio//
					if (disparo != null && detectarColision(disparo, Dinosaurio[i])) {
						Dinosaurio[i] = null; // Eliminar tiranosaurio
						contNull++;
						disparo = null; // Eliminar disparo
						puntos += 2;
						eliminados += 1;
					}

					// Detectar colision entre magma y tiranosaurio
					if (colisionTiranosaurioMagma(Dinosaurio[i])) {
						Dinosaurio[i] = null;
						contNull++;
					}

					// Detectar colisión entre disparo y bomba del tiranosaurio//
					if (disparo != null && Dinosaurio[i].getBomba() != null
							&& disparo.colisionaCon(Dinosaurio[i].getBomba().getX(),
									Dinosaurio[i].getBomba().getY(), Dinosaurio[i].getBomba().getAncho(),
									Dinosaurio[i].getBomba().getAlto())) {
						disparo = null; // Eliminar disparo
						Dinosaurio[i].setBomba(null); // Eliminar bomba
					}
				}

//				if (contNull > 6) {
//					Dinosaurio[i] = respawnearTiranosaurio(Dinosaurio[i]);
//					contNull--;
//				}
			}
			

			

			// DIBUJO LA MAGMA
			magma.dibujarMagma(entorno);
			magma.comienzo += 0.5; // lo que tarda la magma para poder comenzar a subir
			magma.cont += 2; // hace como una especie de reloj para q la magma suba lento o rapido (en este
								// caso la idea es lento)
			if (magma.cont >= 15 && magma.comienzo > 300) {
				magma.actualizar();
				magma.cont = 0;
			}

			if (colisionPRINCESAmagma(princesa)) {
				princesa.vidas = 0;
				perdio = true;
			}
		}

//		 Detectar colisiones entre Dinosaurios//
//		detectarColisionesEntreTiranosaurios();

		// Detectar apoyo de la princesa//
		if (detectarApoyo(princesa, piso)) {
			princesa.estaApoyado = true;
		} else {
			princesa.estaApoyado = false;
		}

		// Detectar colisión de la princesa con los pisos//
		if (detectarColision(princesa, piso)) {
			princesa.saltando = false;
			princesa.contadorSalto = 0;
		}

		// Detectar apoyo de los tiranosaurios//
		for (int i = 0; i < Dinosaurio.length; i++) {
			if (Dinosaurio[i] != null) {
				if (detectarApoyo(Dinosaurio[i], piso)) {
					Dinosaurio[i].setEstaApoyado(true);
				} else {
					Dinosaurio[i].setEstaApoyado(false);
				}
			}
		}
	}

	public void tick() {

		if (!perdio && !gano) {
			tick1();

		} else {
			if (perdio) {
				entorno.dibujarImagen(fondo, 350, 330, 0, 1.0);
				entorno.dibujarImagen(botonGameover, 390, 300, 0, 0.6);
				entorno.cambiarFont("Super Mario 256", 15, Color.PINK);
				entorno.escribirTexto("Puntos: " + puntos, 10, 15);
				entorno.escribirTexto("Enemigos eliminados: " + eliminados, 100, 15);
			} else if (gano) {
				entorno.dibujarImagen(fondo,  400, 300, 0, 0.85);
				entorno.dibujarImagen(botonWinner, 390, 300, 0, 1);
				entorno.cambiarFont("Super Mario 256", 15, Color.PINK);
				entorno.escribirTexto("Puntos: " + puntos, 10, 15);
				entorno.escribirTexto("Enemigos eliminados: " + eliminados, 100, 15);
			}
		}
	}

//	public Dinosaurios respawnearTiranosaurio(Dinosaurios t) {
//		double y = 50 + (int) (Math.random() * 7) * 120;
//		t = new Dinosaurios(obtenerXRandom(entorno), y);
//		return t;
//	}

	// Método para detectar colisión de princesa//
	public boolean chocoCon(double x1, double y1, double x2, double y2, double pixelesColision) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) < pixelesColision * pixelesColision;
	}

	// Método para detectar apoyo de la princesa sobre un bloque//
	public boolean detectarApoyo(Princesa p, Bloque b) {
		return (Math.abs(p.getPiso() - b.getTecho()) < 2) && (p.getDerecha() > b.getIzquierda())
				&& (p.getIzquierda() < b.getDerecha());
	}

	// Método para detectar apoyo de la princesa sobre un piso//
	public boolean detectarApoyo(Princesa p, Piso so) {
		for (int i = 0; i < so.piso.length; i++) {
			if (so.piso[i] != null && detectarApoyo(p, so.piso[i])) {
				return true;
			}
		}
		return false;
	}

	// Método para detectar apoyo de la princesa sobre una lista de pisos//
	public boolean detectarApoyo(Princesa p, Piso[] l) {
		for (int i = 0; i < l.length; i++) {
			if (detectarApoyo(p, l[i])) {
				return true;
			}
		}
		return false;
	}

	// Método para detectar apoyo de un tiranosaurio sobre un bloque//
	public boolean detectarApoyo(Dinosaurios t, Bloque b) {
		return (Math.abs(t.getPiso() - b.getTecho()) < 2) && (t.getDerecha() > b.getIzquierda())
				&& (t.getIzquierda() < b.getDerecha());
	}

	// Método para detectar apoyo de un tiranosaurio sobre un piso//
	public boolean detectarApoyo(Dinosaurios t, Piso so) {
		for (int i = 0; i < so.piso.length; i++) {
			if (so.piso[i] != null && detectarApoyo(t, so.piso[i])) {
				return true;
			}
		}
		return false;
	}

	// Método para detectar apoyo de un tiranosaurio sobre una lista de pisos//
	public boolean detectarApoyo(Dinosaurios t, Piso[] l) {
		for (int i = 0; i < l.length; i++) {
			if (detectarApoyo(t, l[i])) {
				return true;
			}
		}
		return false;
	}

	// Método para detectar colisión de la princesa con un bloque//
	public boolean detectarColision(Princesa p, Bloque b) {
		return (Math.abs(p.getTecho() - b.getPiso()) < 5) && (p.getDerecha() > b.getIzquierda())
				&& (p.getIzquierda() < b.getDerecha());
	}

	// Método para detectar colisión de la princesa con un piso//
	public boolean detectarColision(Princesa p, Piso so) {
		for (int i = 0; i < so.piso.length; i++) {
			if (so.piso[i] != null && detectarColision(p, so.piso[i])) {
				if (so.piso[i].destructible) {
					so.piso[i] = null;
				}
				return true;
			}
		}
		return false;
	}

//	// Método para detectar colisiones entre tiranosaurios//
//	public void detectarColisionesEntreTiranosaurios() {
//		for (int i = 0; i < Dinosaurio.length; i++) {
//			for (int j = i + 1; j < Dinosaurio.length; j++) {
//				if (Dinosaurio[i] != null && Dinosaurio[j] != null && colisiona(Dinosaurio[i], Dinosaurio[j])) {
//					Dinosaurio[i].cambiarDireccion();
//					Dinosaurio[j].cambiarDireccion();
//				}
//			}
//		}
//	}

	// Método para detectar colisión entre dos tiranosaurios//
	public boolean colisiona(Dinosaurios t1, Dinosaurios t2) {
		return t1.getDerecha() > t2.getIzquierda() && t1.getIzquierda() < t2.getDerecha()
				&& t1.getPiso() > t2.getTecho() && t1.getTecho() < t2.getPiso();
	}

	// Método para detectar colisión de la princesa con una lista de pisos//
	public boolean detectarColision(Princesa p, Piso[] l) {
		for (int i = 0; i < l.length; i++) {
			if (detectarColision(p, l[i])) {
				return true;
			}
		}
		return false;
	}

	// Método para detectar colisión entre un disparo y un tiranosaurio//
	public boolean detectarColision(Disparo d, Dinosaurios t) {
		return d.getX() < t.getDerecha() && d.getX() + d.getAncho() > t.getIzquierda() && d.getY() < t.getPiso()
				&& d.getY() + d.getAlto() > t.getTecho();
	}

	// Método para detectar colisión entre magma y la princesa//
	public boolean colisionPRINCESAmagma(Princesa p) {
		return magma.getTecho()-20 < p.getTecho();
	}

	// Método para detectar colisión entre magma y el tiranosaurio//
	public boolean colisionTiranosaurioMagma(Dinosaurios t) {
		return t != null && t.getPiso() >= magma.getTecho()+30;
	}
	
	// Método para detectar colisión entre la moneda y la princesa//
	public boolean colisionPRINCESAmoneda(Princesa p, Coin c) {
        return p.getDerecha() > c.getIzquierda() &&
                p.getIzquierda() < c.getDerecha() &&
                p.getPiso() > c.getTecho() &&
                p.getTecho() < c.getPiso();
	}

	public double obtenerXRandom(Entorno e) {
		double x = (Math.random() * e.getWidth()) + 1;
		return x;
	}

	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}