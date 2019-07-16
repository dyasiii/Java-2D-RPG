package principal.controles;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DatosDebug;

public class Raton extends MouseAdapter {
	
	private final Cursor cursor;
	private Point posicion;
	private boolean click;
	
	public Raton(final SuperficieDibujo sd) {
		Toolkit configuracion = Toolkit.getDefaultToolkit();
		
		BufferedImage iconoRaton = CargadorRecursos.cargarImagenCompatibleTranslucida(Constantes.ICONO_RATON);
		
		Constantes.LADO_CURSOR = iconoRaton.getWidth();
		
		Point punto = new Point(0, 0);
		
		this.cursor = configuracion.createCustomCursor(iconoRaton, punto, "Cursor Default");
		
		posicion = new Point();
		actualizarPosicion(sd);
		
		click = false;
	}
	
	private void actualizarPosicion(final SuperficieDibujo sd) {
		final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
		
		SwingUtilities.convertPointFromScreen(posicionInicial, sd);
		
		posicion.setLocation(posicionInicial.getX(), posicionInicial.getY());
	}
	
	public Point obtenerPuntoPosicion() {
		return posicion;
	}
	
	public Rectangle obtenerRectanguloPosicion() {
		final Rectangle area = new Rectangle(posicion.x, posicion.y, 1, 1);
		
		return area;
	}
	
	public void actualizar(final SuperficieDibujo sd) {
		actualizarPosicion(sd);

	}
	
	public void dibujar(final Graphics g) {
		DatosDebug.enviarDatos("Raton X:" + posicion.getX());
		DatosDebug.enviarDatos("Raton Y:" + posicion.getY());
	}
	
	public Cursor getCursor() {
		return this.cursor;
	}
	
	public void mouseClicked(MouseEvent e) {
		if(!click) {
			click = true;
		}
		
	}

	public boolean obtenerClick() {
		return click;
	}

	public void reiniciarClick() {
		if(click) {
			click = false;
		}
	}
	
	
	
}
