package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Coin {
	
	private int x;
	private int y;
	private double escala;
	Image coin;

	public Coin(int x, int y, double escala) {
		this.x = x;
		this.y = y;
		this.escala = escala;
		coin = Herramientas.cargarImagen("coin.gif");
	}
	
	public void dibujarCoin(Entorno entorno) {
		entorno.dibujarImagen(coin, this.x, this.y, 0, this.escala);
	}

	public double getAlto() {
		return this.coin.getHeight(null) * this.escala;
	}

	public double getAncho() {
		return this.coin.getWidth(null) * this.escala;
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
