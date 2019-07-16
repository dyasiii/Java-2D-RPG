package principal;

import principal.maquinaestado.GestorEstados;
import principal.controles.GestorControles;
import principal.graficos.*;

public class GestorPrincipal {
	
	//Atributos Principales 
	
	private boolean enFuncionamiento = false;
	private String titulo;
	private int ancho;
	private int alto;
	
	private static int fps = 0;
	private static int aps = 0;
	
	private SuperficieDibujo sd;
	private Ventana ventana;
	private GestorEstados ge;
	
	//Constructor 
	
	private GestorPrincipal(final String titulo, final int ancho, final int alto) {
		this.titulo = titulo;
		this.ancho = ancho;
		this.alto = alto;
		
	}
	
	//Metodo main() que inicia el juego

	public static void main(String[] args) {
		GestorPrincipal gp = new GestorPrincipal("Shaun of the Dead", Constantes.ANCHO_JUEGO, Constantes.ALTO_JUEGO);
		gp.iniciarJuego();
		gp.iniciarBuclePrincipal();
	}
	
	//Funciones que inician el juego

	private void iniciarJuego() {
		enFuncionamiento = true;
		inicializar();
		
	}
	
	private void inicializar() {
		sd = new SuperficieDibujo(ancho, alto);
		ventana = new Ventana(titulo, sd);
		ge = new GestorEstados(sd);
	}

	//Bucle principal 
	
	private void iniciarBuclePrincipal() {
		
		int actualizacionesAcumuladas = 0;
		int framesAcumulados = 0;

		final int NS_POR_SEGUNDO = 1000000000;
		final int APS_OBJETIVO = 60;
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0;

		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime();

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			while (delta >= 1) {
				actualizar();
				actualizacionesAcumuladas++;
				delta--;
			}

			dibujar();
			framesAcumulados++;

			if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
				
				aps = actualizacionesAcumuladas;
				fps = framesAcumulados;
				
				actualizacionesAcumuladas = 0;
				framesAcumulados = 0;
				
				referenciaContador = System.nanoTime();
			}
		}
	}
	
	//Metodos que actualizan y dibujan constantemente a traves del bucle principal 

	private void actualizar() {
		if(GestorControles.teclado.inventarioActivo) {
			ge.cambiarEstadoActual(1);
		}else {
			ge.cambiarEstadoActual(0);
		}
		
		ge.actualizar();
		sd.actualizar();
	}
	
	private void dibujar() {
		sd.dibujar(ge);
	}
	
	//Geters y Seters

	public static int getFps() {
		return fps;
	}
	
	/*
	public static void setFps(int fps) {
		GestorPrincipal.fps = fps;
	}
	*/

	public static int getAps() {
		return aps;
	}
	
	/*
	public static void setAps(int aps) {
		GestorPrincipal.aps = aps;
	}
	*/

}
