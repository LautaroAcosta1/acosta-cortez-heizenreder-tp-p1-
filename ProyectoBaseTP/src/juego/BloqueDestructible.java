package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BloqueDestructible {
	
	private Image img;
	private double x;
	private double y;
	private double angulo;
	
	/* Nota: Agregar array de objetos para formar una fila de bloques. */
	
	public BloqueDestructible(double x, double y) {
		this.x = x;
		this.y = y;
		this.angulo = 0;
		img = Herramientas.cargarImagen("bloqueDestructible.jpg");
	}
	
	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarRectangulo(this.x, this.y, 40, 40, 0, Color.CYAN);;
		entorno.dibujarImagen(img, this.x, this.y, angulo, 0.20);
	}
	
}
