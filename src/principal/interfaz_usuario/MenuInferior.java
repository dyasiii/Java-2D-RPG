package principal.interfaz_usuario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constantes;
import principal.entidades.Jugador;
import principal.herramientas.DibujoDebug;

public class MenuInferior {
	
	private Rectangle areaInventario;
	private Rectangle bordeAreaInventario;
	
	private Color grisMenu;
	private Color rojoClaro;
	private Color rojoOscuro;
	private Color azulClaro;
	private Color azulOscuro;
	private Color verdeClaro;
	private Color verdeOscuro;
	private Color rosaClaro;
	private Color rosaOscuro;
	
	public MenuInferior(final Jugador jugador) {
		
		int altoMenu = 64;
		areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO, altoMenu);
		bordeAreaInventario = new Rectangle(areaInventario.x, areaInventario.y - 1, areaInventario.width, 1);
		grisMenu = new Color(107, 109, 112);
		rojoOscuro = new Color(150, 0, 0);
		rojoClaro = new Color(255, 0, 0);
		azulClaro = new Color(0, 0, 255);
		azulOscuro = new Color(0, 0, 150);
		verdeClaro = new Color(0, 255, 0);
		verdeOscuro = new Color(0, 150, 0);
		rosaClaro = new Color(247, 25, 255);
		rosaOscuro = new Color(180, 18, 186);
	}
	
	public void dibujar(final Graphics g, final Jugador jugador) {
		dibujarAreaInventario(g);
		dibujarBarraVida(g);
		dibujarBarraPoder(g);
		dibujarBarraResistencia(g, jugador.resistencia);
		dibujarBarraExperiencia(g,63);
		dibujarRanurasObjetos(g);
	}
	
	private void dibujarAreaInventario(final Graphics g) {
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario, grisMenu);
		DibujoDebug.dibujarRectangulosRelleno(g, bordeAreaInventario, Color.white);
	}
	
	private void dibujarBarraVida(final Graphics g) {
		final int medidaVertical = 4;
		final int anchoTotal = 100;
		
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical, anchoTotal, medidaVertical, rojoClaro);
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 2, anchoTotal, medidaVertical, rojoOscuro);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "VIDA", areaInventario.x + 5, areaInventario.y + (medidaVertical * 3) - 1);
		DibujoDebug.dibujarString(g, "100%", 37 + anchoTotal, areaInventario.y + (medidaVertical * 3) - 1);
		
	}
	
	private void dibujarBarraPoder(final Graphics g) {
		
		final int medidaVertical = 4;
		final int anchoTotal = 100;
		
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 4, anchoTotal, medidaVertical, azulClaro);
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 5, anchoTotal, medidaVertical, azulOscuro);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "POW", areaInventario.x + 5, areaInventario.y + (medidaVertical * 6) - 1);
		DibujoDebug.dibujarString(g, "100%", 37 + anchoTotal, areaInventario.y + (medidaVertical * 6) - 1);
	}
	
	private void dibujarBarraResistencia(final Graphics g, final int resistencia) {
		
		final int medidaVertical = 4;
		final int anchoTotal = 100;
		final int ancho = anchoTotal * resistencia / Jugador.RESISTENCIA_TOTAL;
		
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 7, ancho, medidaVertical, verdeClaro);
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 8, ancho, medidaVertical, verdeOscuro);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "RST.", areaInventario.x + 5, areaInventario.y + (medidaVertical * 9) - 1);
		DibujoDebug.dibujarString(g, ""+resistencia/6+"%", 37 + anchoTotal, areaInventario.y + (medidaVertical * 9) - 1);
	}
	
	private void dibujarBarraExperiencia(final Graphics g, final int experiencia) {
		final int medidaVertical = 4;
		final int anchoTotal = 100;
		final int ancho = anchoTotal * experiencia / anchoTotal; 
		
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 10, ancho, medidaVertical, rosaClaro);
		DibujoDebug.dibujarRectangulosRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 11, ancho, medidaVertical, rosaOscuro);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "EXP.", areaInventario.x + 5, areaInventario.y + (medidaVertical * 12) - 1);
		DibujoDebug.dibujarString(g, experiencia+"%", 37 + anchoTotal, areaInventario.y + (medidaVertical * 12) - 1);
	}
	
	private void dibujarRanurasObjetos(final Graphics g) {
		final int anchoRanura = 32;
		final int numeroRanuras = 4;
		final int espaciadoRanura = 10;
		final int anchoTotal = anchoRanura * 10 + espaciadoRanura * numeroRanuras;
		final int xInicial = Constantes.ANCHO_JUEGO - anchoTotal;
		final int anchoRanuraYEspacio = anchoRanura + espaciadoRanura;
		
		g.setColor(Color.white);
		for(int i = 0; i < numeroRanuras; i++) {
			int xActual = xInicial + anchoRanuraYEspacio * i;
			
			Rectangle ranura = new Rectangle(xActual, areaInventario.y + 4, anchoRanura, anchoRanura);
			DibujoDebug.dibujarRectangulosRelleno(g, ranura);
			DibujoDebug.dibujarString(g, ""+ (i+1) , xActual + 13, areaInventario.y + 54);
		}
	}
}
