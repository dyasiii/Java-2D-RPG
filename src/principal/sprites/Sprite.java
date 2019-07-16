package principal.sprites;

import java.awt.image.BufferedImage;

public class Sprite {
	
	//Variables
	
	private final BufferedImage imagen;
	
	private final int ancho;
	private final int alto;
	
	//Constructor
	
	public Sprite(final BufferedImage imagen) {
		this.imagen = imagen;
		
		ancho = imagen.getWidth();
		alto = imagen.getHeight();
	}
	
	//Funciones
	
	public BufferedImage getImagen() {
		return imagen;
	}
	public int getAncho() {
		return ancho;
	}
	public int getAlto() {
		return alto;
	}
}
