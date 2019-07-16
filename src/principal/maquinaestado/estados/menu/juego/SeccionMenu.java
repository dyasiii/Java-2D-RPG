package principal.maquinaestado.estados.menu.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;

public abstract class SeccionMenu {
	
	protected final String nombreSeccion;
	protected final Rectangle etiquetaMenu;
	
	public SeccionMenu(final String nombreSeccion, final Rectangle etiquetaMenu) {
		this.nombreSeccion = nombreSeccion;
		this.etiquetaMenu = etiquetaMenu;
	}
	
	public abstract void actualizar();
	public abstract void dibujar(final Graphics g, final SuperficieDibujo sd);
	
	public void dibujarEtiquetaInactiva(final Graphics g) {
		DibujoDebug.dibujarRectangulosRelleno(g, etiquetaMenu, Color.white);
		DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	public void dibujarEtiquetaActiva(final Graphics g) {
		DibujoDebug.dibujarRectangulosRelleno(g, etiquetaMenu, Color.white);
		
		final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
		final Color colorActivo = new Color(0xff6700);
		
		DibujoDebug.dibujarRectangulosRelleno(g, marcaActiva, colorActivo);
		DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	public void dibujarEtiquetaInactivaResaltada(final Graphics g) {
		DibujoDebug.dibujarRectangulosRelleno(g, etiquetaMenu, Color.white);
		
		DibujoDebug.dibujarRectangulosRelleno(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, 
				etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10),new Color(0x2a2a2a));
		
		DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	public void dibujarEtiquetaActivaResaltada(final Graphics g) {
		DibujoDebug.dibujarRectangulosRelleno(g, etiquetaMenu, Color.white);
		
		final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
		final Color colorActivo = new Color(0xff6700);
		
		DibujoDebug.dibujarRectangulosRelleno(g, marcaActiva, colorActivo);
		
		DibujoDebug.dibujarRectangulosRelleno(g, new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, 
				etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10),new Color(0x2a2a2a));
		
		DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
	}
	
	public String obtenerNombreSeccion() {
		return nombreSeccion;
	}
	
	public Rectangle obtenerEtiquetaMenu() {
		return etiquetaMenu;
	}
	
	public Rectangle obtenerEtiquetaMenuEscalada() {
		final Rectangle etiquetaEscalada = new Rectangle((int)(etiquetaMenu.x * Constantes.FACTOR_ESCALADO_X), 
				(int) (etiquetaMenu.y * Constantes.FACTOR_ESCALADO_Y),
				(int) (etiquetaMenu.width * Constantes.FACTOR_ESCALADO_X), 
				(int) (etiquetaMenu.height * Constantes.FACTOR_ESCALADO_Y));
	
		return etiquetaEscalada;
	}
}
