package principal.graficos.menu.componentes;

import java.awt.Graphics;

public class GestorCuadroTexto {
	
	public void dibujarCuadroTexto(Graphics g) {
		CuadroTexto ct = new CuadroTexto(0, 0, 0, 0, null, null, null, null);
		
		ct.dibujar(g);
	}
	
}
