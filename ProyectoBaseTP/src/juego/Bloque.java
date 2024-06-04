package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	double x;
	double y;
	double escala;
	boolean destructible;
	Image bloque1;
	Image bloque2;

	public Bloque(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.15;
		this.destructible = true;
		if (Math.random() > 0.4) {
			this.destructible = false;
		}
		bloque1 = Herramientas.cargarImagen("bloqueDestructible.jpg");
		bloque2 = Herramientas.cargarImagen("bloqueIndestructible.jpg");
	}

	public void dibujar(Entorno e) {
		if (destructible) {
			e.dibujarImagen(this.bloque1, this.x, this.y, 0, this.escala);
		} else {
			e.dibujarImagen(this.bloque2, this.x, this.y, 0, this.escala);
		}
	}

	public double getAlto() {
		return this.bloque1.getHeight(null) * this.escala;
	}

	public double getAncho() {
		return this.bloque2.getWidth(null) * this.escala;
	}

	public double getTecho() {
		return this.y - this.getAlto() / 2;

	}

	public double getPiso() {
		return this.y + this.getAlto() / 2;
	}

	public double getIzquierda() {
		return this.x - this.getAncho() / 2;

	}

	public double getDerecha() {
		return this.x + this.getAncho() / 2;
	}

}
