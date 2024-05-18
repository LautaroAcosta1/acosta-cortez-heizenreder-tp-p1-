package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	
    private Image img;
    private double x;
    private double y;
    private double desplazamiento;
    private double desplazamientoSaltar;
	
	public Princesa(double x, double y, double desplazamiento, double desplazamientoSaltar){
		this.img = Herramientas.cargarImagen("");
		this.x = x;
		this.y = y;
		this.desplazamiento = desplazamiento;
		this.desplazamientoSaltar = desplazamientoSaltar;
		
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, 26, 70, 0, Color.CYAN);
	}
	
	public void moverIzquierda() {
		x -= desplazamiento;
	}
	
	public void moverDerecha() {
		x += desplazamiento;
	}
	
	public void saltar() {
		y -= desplazamientoSaltar;
	}
	
	public void gravedad() {
		y += desplazamiento;
	}
	
}
