package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Disparo {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Image disparoPrincesa;

	public Disparo(double x, double y) {
		this.x = x;
		this.y = y;
		this.ancho = 25;
		this.alto = 20;
		this.disparoPrincesa = Herramientas.cargarImagen("disparoPrincesa.gif");

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(disparoPrincesa, x, y, 0, 0.12);
	}

	public void moverDisparo(int dire) {
		if (dire == 1) {
			this.x += 10;
		} else {
			this.x -= 10;
		}
	}

	public boolean estaDentroDelMapa(Entorno e) {
		if (this.x < -50 || this.x > e.ancho() + 50 || this.y < -50 || this.y > e.alto() + 50)
			return false;
		return true;
	}

	public boolean colisionaCon(double otroX, double otroY, double ancho, double alto) {
		return this.x < otroX + ancho && this.x + this.ancho > otroX && this.y < otroY + alto
				&& this.y + this.alto > otroY;
	}

}