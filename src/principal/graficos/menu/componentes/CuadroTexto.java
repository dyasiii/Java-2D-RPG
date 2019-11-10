package principal.graficos.menu.componentes;

import java.awt.Color;
import java.awt.Graphics;

import principal.herramientas.DibujoDebug;



public class CuadroTexto {
	
	//Variables
	
	private int anchoCuadro;
	private int altoCuadro;
	
	private int posicionX;
	private int posicionY;
	
	private int posicionTextoX;
	private int posicionTextoY;
	
	private Color colorBorde;
	private Color colorFondo;
	private Color colorTexto;
	
	String textoCuadro;
	
	//Constructor
	
	public CuadroTexto(int posicionX, int posicionY, int anchoCuadro, int altoCuadro, 
			String textoCuadro, Color colorBorde, Color colorFondo, Color colorTexto) {
		this.anchoCuadro = anchoCuadro;
		this.altoCuadro = altoCuadro;
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.textoCuadro = textoCuadro;
		this.colorBorde = colorBorde;
		this.colorFondo = colorFondo;
		this.colorTexto = colorTexto;
		
		posicionTextoX = posicionX + anchoCuadro/16;
		posicionTextoY = posicionY + altoCuadro/2;
		

	}
	
	//Metodos 
	
	public void actualizar() {
		
	}
	
	public void dibujar(Graphics g) {
		DibujoDebug.dibujarRectangulos(g, posicionX, posicionY, anchoCuadro, altoCuadro, colorBorde);
		DibujoDebug.dibujarRectangulosRelleno(g, posicionX, posicionY, anchoCuadro, altoCuadro, colorFondo);
		DibujoDebug.dibujarString(g, textoCuadro, posicionTextoX, posicionTextoY, colorTexto);
	}
	
	
}
