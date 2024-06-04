package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	private double x;
	double y;
	private double velocidad;
	private double escala;
	private Image izq, der, corazon;
	boolean direccion; // false es derecha
	boolean estaApoyado;
	boolean saltando;
	int contadorSalto;
	static final double escalaVida = 0.06;
	int vidas;

	Princesa(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 2.5;
		this.escala = 0.35;
		izq = Herramientas.cargarImagen("princesaIzquierda.png");
		der = Herramientas.cargarImagen("princesaDerecha.png");
		this.direccion = false;
		this.estaApoyado = false;
		this.saltando = false;
		this.contadorSalto = 0;
		this.vidas = 3;
		this.corazon = Herramientas.cargarImagen("corazon.png");
	}

	public int getVidas() {
		return vidas;
	}

	public void reducirVida() {
		if (vidas > 0) {
			vidas--;
		}

	}

	public void dibujarVidas(Entorno entorno) {
		for (int i = 0; i < vidas; i++) {
			entorno.dibujarImagen(corazon, 720 + (i * 30), 20, 0, Princesa.escalaVida);
		}
	}

	public void mover(Entorno e) {
		if (estaApoyado || !saltando) {
			this.x += this.direccion ? - this.velocidad : this.velocidad;
			if (this.x > e.ancho() - this.getAncho() / 2) {
				this.x = e.ancho() - this.getAncho() / 2;
			}
			if (this.x < this.getAncho() / 2) {
				this.x = this.getAncho() / 2;
			}
		}
	}

	public void caerSubir(Entorno e) {
		if (!this.estaApoyado && !saltando) {
			this.y += 3.5;
		}
		if (saltando) {
			this.y -= 8;
			this.contadorSalto++;
			saltando = true;
		}
		if (this.contadorSalto > 20) {
			saltando = false;
			this.contadorSalto = 0;
		}
	}

	public void dibujar(Entorno e) {
		if (direccion) {
			e.dibujarImagen(this.izq, this.x, this.y, 0, this.escala);
		} else {
			e.dibujarImagen(this.der, this.x, this.y, 0, this.escala);
		}
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
		return this.x - this.getAncho() / 3;

	}

	public double getDerecha() {
		return this.x + this.getAncho() / 3;
	}

	public double getAbajo() {
		return this.x + this.getAncho() / 4;

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
