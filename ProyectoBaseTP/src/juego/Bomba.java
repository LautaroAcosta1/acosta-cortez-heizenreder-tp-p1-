package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bomba {
	private double x, y;
	private Image proyectil;
	private boolean direccion;
	private double velocidad;

	// CONSTRUCTOR//

	public Bomba(double x, double y, boolean direccion) {
		this.x = x;
		this.y = y;
		this.direccion = direccion;
		this.proyectil = Herramientas.cargarImagen("disparo.gif");
		this.velocidad = 5.0;
	}

	// METODOS GETTERS Y SETTERS//

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Image getProtectil() {
		return proyectil;
	}

	public void setProyectil(Image proyectil) {
		this.proyectil = proyectil;
	}

	public boolean esDireccion() {
		return direccion;
	}

	public void setDireccion(boolean direccion) {
		this.direccion = direccion;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public double getAncho() {
		return proyectil.getWidth(null) * 0.1;
	}

	public double getAlto() {
		return proyectil.getHeight(null) * 0.1;
	}

	// METODO PARA DIBUJAR LA BOMBA//

	public void dibujarBomba(Entorno entorno) {
		entorno.dibujarImagen(proyectil, this.x, this.y, 0, 0.12);
	}

	// METODO PARA MOVER LA BOMBA//

	public void moverBomba() {
		if (this.direccion) {
			this.x += this.velocidad;

		} else {
			this.x -= this.velocidad;
		}
	}

	// METODO PARA CHEQUEAR SI LA BOMBA ESTA DENTRO DEL MAPA//
	public boolean estaDentroDelMapa(Entorno entorno) {
		return this.x >= 0 && this.x <= entorno.ancho() && this.y >= 0 && this.y <= entorno.alto();
	}

	// METODO PARA CHEQUEAR SI HAY COLISION CON OTRO OBJETO//
	public boolean colisionaCon(double otroX, double otroY, double ancho, double alto) {
		return this.x < otroX + ancho && this.x + this.proyectil.getWidth(null) * 0.1 > otroX && this.y < otroY + alto
				&& this.y + this.proyectil.getHeight(null) * 0.1 > otroY;
	}
}