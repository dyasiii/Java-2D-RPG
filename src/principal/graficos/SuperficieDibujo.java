package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.controles.GestorControles;
import principal.controles.Raton;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
import principal.maquinaestado.GestorEstados;

public class SuperficieDibujo extends Canvas {

	//Variables
	private static final long serialVersionUID = 1L;
	
	private int ancho;
	private int alto;
	
	private Raton raton;
	
	//Constructor
	
	public SuperficieDibujo(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;
		
		this.raton = new Raton(this);
		
		setCursor(raton.getCursor());
		setIgnoreRepaint(true);
		setPreferredSize(new Dimension(Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA));
		addKeyListener(GestorControles.teclado);
		addMouseListener(raton);
		setFocusable(true);
		requestFocus();
	}
	
	//Funciones
	
	public void actualizar() {
		raton.actualizar(this);
	}
	
	public void dibujar(final GestorEstados ge) {
		final BufferStrategy buffer = getBufferStrategy();
		if(buffer == null) {
			createBufferStrategy(4);
			return;
		}
		
		final Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		
		DibujoDebug.reiniciarContadorObjetos();
		
		g.setFont(Constantes.FUENTE_PRINCIPAL);
		
		DibujoDebug.dibujarRectangulosRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA, Color.black);
		
		if(Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y != 1.0) {
			g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);
		}
		
		ge.dibujar(g);
		
		g.setColor(Color.white);
		
		DibujoDebug.dibujarString(g, "Fps: "+ GestorPrincipal.getFps(), 20, 20);
		DibujoDebug.dibujarString(g, "Aps: "+ GestorPrincipal.getAps(), 20, 30);
		DatosDebug.enviarDatos("Escala X: " + Constantes.FACTOR_ESCALADO_X);
		DatosDebug.enviarDatos("Escala Y: " + Constantes.FACTOR_ESCALADO_Y);
		DatosDebug.enviarDatos("OPF: "+DibujoDebug.getObjetosDibujados());
		
		if(GestorControles.teclado.debug) {
			DatosDebug.dibujarDatos(g);
		}else {
			DatosDebug.vaciarDatos();
		}
		
		raton.dibujar(g);
		
		Toolkit.getDefaultToolkit().sync();
		
		g.dispose();
		
		buffer.show();
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public int getAlto() {
		return alto;
	}

	public Raton obtenerRaton() {
		return raton;
	}
	
}
