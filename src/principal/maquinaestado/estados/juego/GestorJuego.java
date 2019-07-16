package principal.maquinaestado.estados.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.controles.GestorControles;
import principal.entidades.Jugador;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
import principal.interfaz_usuario.MenuInferior;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;

public class GestorJuego implements EstadoJuego {

	//Variables
	
	Mapa mapa;
	Jugador jugador;
	MenuInferior menuInferior;
	
	//Constructor
	
	public GestorJuego() {
		iniciarMapa(Constantes.RUTA_MAPA);
		iniciarJugador();
		menuInferior = new MenuInferior(jugador);
	}
	
	//Metodos Inicializadores
	
	private void recargarJuego() {
		final String ruta = "/Archivos-texto/Mapas/" + mapa.getSiguienteMapa();
		
		iniciarMapa(ruta);
		iniciarJugador();
		//jugador.setPosicionX(mapa.getPosicionInicial().getX());
		//jugador.setPosicionY(mapa.getPosicionInicial().getY());
	}
	
	private void iniciarMapa(final String ruta) {
		mapa = new Mapa(ruta);
	}
	
	private void iniciarJugador() {
		jugador = new Jugador(mapa);
	}
	
	//Actualizar y Dibujar
	
	public void actualizar() {
		if(jugador.getLIMITE_ARRIBA().intersects(mapa.getZonaSalida())) {
			recargarJuego();
		}
		jugador.actualizar();
		mapa.actualizar((int)jugador.getPosicionX(),(int) jugador.getPosicionY());
		
		
	}
	
	public void dibujar(Graphics g) {
		mapa.dibujar(g,(int) jugador.getPosicionX(),(int) jugador.getPosicionY());
		jugador.dibujar(g);
		menuInferior.dibujar(g, jugador);
		
		DatosDebug.enviarDatos("X =" + jugador.getPosicionX());
		DatosDebug.enviarDatos("Y =" + jugador.getPosicionY());
		DatosDebug.enviarDatos("Siguiente Mapa: "+mapa.getSiguienteMapa());
		DatosDebug.enviarDatos("Coordenadas de salida: X="+mapa.getPuntoSalida().getX() * Constantes.LADO_SPRITE +" Y="+mapa.getPuntoSalida().getY() * Constantes.LADO_SPRITE);
		
	}

}
