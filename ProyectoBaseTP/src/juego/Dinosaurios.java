package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Dinosaurios {

	private double x;
	private double y;
	private double velocidad;
	private Image izq;
	private Image der;
	private boolean moviendoDerecha;
	private double escala;
	private boolean estaApoyado;
	private boolean saltando;
	private int contadorSalto;
	private Bomba bomba;
	private int contadorBomba;

	public Dinosaurios(double d, double y) {
		this.x = d;
		this.y = y;
		this.velocidad = 2;
		this.escala = 0.07;
		this.izq = Herramientas.cargarImagen("dinoizq.png");
		this.der = Herramientas.cargarImagen("dinoder.png");
		this.moviendoDerecha = true; // Empieza moviéndose a la derecha
		this.estaApoyado = false;
		this.saltando = false;
		this.contadorSalto = 0;
		this.contadorBomba = 0;
	}

	public void mover(Entorno entorno) {
		if (estaApoyado) {
			if (moviendoDerecha) {
				this.x += this.velocidad;
				if (this.x + this.getAncho() / 2 >= entorno.ancho()) {
					moviendoDerecha = false; // Cambia de dirección
				}
			} else {
				this.x -= this.velocidad;
				if (this.x - this.getAncho() / 2 <= 0) {
					moviendoDerecha = true; // Cambia de dirección
				}
			}
		}
	}

	public void caerSubir(Entorno entorno) {
		if (!estaApoyado) {
			this.y += 3.5; // Velocidad de caída
		} else if (saltando) {

			contadorSalto++;
		}

		if (this.y >= entorno.alto() - this.getAlto() / 2) {
			this.y = entorno.alto() - this.getAlto() / 2;
			estaApoyado = true;
		}
	}

	public void disparo(Entorno entorno) {
		if (contadorBomba > 2000 && bomba == null) {
			bomba = new Bomba(this.x, this.y, this.moviendoDerecha);
			contadorBomba = 0; // El contador de bomba se resetea despues de disparar
		}
		if (bomba != null) {
			bomba.dibujarBomba(entorno);
			bomba.moverBomba();
			if (!bomba.estaDentroDelMapa(entorno)) {
				bomba = null;
			}
		}

	}

	public Bomba getBomba() {
		return bomba;
	}

	public void setBomba(Bomba bomba) {
		this.bomba = bomba;
	}

	public int getContadorBomba() {
		return contadorBomba;
	}

	public void setContadorBomba(int contadorBomba) {
		this.contadorBomba = contadorBomba;
	}

	public void dibujarTiranosaurio(Entorno entorno) {
		if (moviendoDerecha) {
			entorno.dibujarImagen(der, x, y, 0, escala);
		} else {
			entorno.dibujarImagen(izq, x, y, 0, escala);
		}
	}

	public static Dinosaurios crear() {
		return new Dinosaurios(Math.random() * 300, 00);
	}

	public double getAncho() {
		return izq.getWidth(null) * this.escala;
	}

	public double getAlto() {
		return izq.getHeight(null) * this.escala;
	}

	public double getTecho() {
		return this.y - this.getAlto() / 2;
	}

	public double getPiso() {
		return this.y + this.getAlto() / 2;
	}

	public double getIzquierda() {
		return this.x - this.getAncho() / 4;
	}

	public double getDerecha() {
		return this.x + this.getAncho() / 4;
	}

	public void cambiarDireccion() {
		this.moviendoDerecha = !this.moviendoDerecha;
	}

	public void setEstaApoyado(boolean estaApoyado) {
		this.estaApoyado = estaApoyado;
	}

	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}
}