package principal.herramientas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;

public class GeneradorTooltip {
	
	private static Point generarTooltip(final Point puntoInicial) {
		
		final int x = puntoInicial.x;
		final int y = puntoInicial.y;
		
		final Point centroCanvas = new Point(Constantes.CENTRO_VENTANA_X, Constantes.CENTRO_VENTANA_Y);
		
		final Point centroCanvasEscalado = new Point(
				EscaladorElementos.escalarPuntoArriba(centroCanvas).x, 
				EscaladorElementos.escalarPuntoArriba(centroCanvas).y
				);
		
		final int margenCursor = 5;
		
		final Point puntoFinal = new Point();
		
		if(x <= centroCanvasEscalado.x) {
			if(y <= centroCanvasEscalado.y) {
				puntoFinal.x = x + Constantes.LADO_CURSOR + margenCursor;
				puntoFinal.y = y + Constantes.LADO_CURSOR + margenCursor;
			}else {
				puntoFinal.x = x + Constantes.LADO_CURSOR + margenCursor;
				puntoFinal.y = y - Constantes.LADO_CURSOR - margenCursor;
			}
		}else {
			if(y <= centroCanvasEscalado.y) {
				puntoFinal.x = x - Constantes.LADO_CURSOR - margenCursor;
				puntoFinal.y = y + Constantes.LADO_CURSOR + margenCursor;
			}else {
				puntoFinal.x = x - Constantes.LADO_CURSOR - margenCursor;
				puntoFinal.y = y - Constantes.LADO_CURSOR - margenCursor;
			}
		}
		
		return puntoFinal;
	}
	
	private static String obtenerPosicionTooltip(final Point puntoInicial) {
		
		final int x = puntoInicial.x;
		final int y = puntoInicial.y;
		
		final Point centroCanvas = new Point(Constantes.CENTRO_VENTANA_X, Constantes.CENTRO_VENTANA_Y);
		
		final Point centroCanvasEscalado = new Point(
				EscaladorElementos.escalarPuntoArriba(centroCanvas).x, 
				EscaladorElementos.escalarPuntoArriba(centroCanvas).y
				);
		
		String posicion = "";
		
		if(x <= centroCanvasEscalado.x) {
			if(y <= centroCanvasEscalado.y) {
				posicion = "no";
			}else {
				posicion = "so";
			}
		}else {
			if(y <= centroCanvasEscalado.y) {
				posicion = "ne";
			}else {
				posicion = "se";
			}
		}
		
		return posicion;
		
	}
	
	public static void dibujarTooltip(final Graphics g, final SuperficieDibujo sd, final String texto) {
		
		final Point posicionRaton = sd.obtenerRaton().obtenerPuntoPosicion();
		final Point posicionTooltip = GeneradorTooltip.generarTooltip(posicionRaton);
		final String pistaPosicion = GeneradorTooltip.obtenerPosicionTooltip(posicionRaton);
		final Point posicionTooltipEscalada = EscaladorElementos.escalarPuntoAbajo(posicionTooltip);
		
		final int ancho = MedidorString.medirAnchoPixeles(g, texto);
		final int alto = MedidorString.medirAltoPixeles(g, texto);
		final int margenFuente = 3;
		
		Rectangle tooltip = null;
		
		switch(pistaPosicion) {
			case "no":
				tooltip = new Rectangle(posicionTooltipEscalada.x, posicionTooltipEscalada.y, ancho + margenFuente * 2, alto + margenFuente);
				break;
			case "ne":
				tooltip = new Rectangle(posicionTooltipEscalada.x - ancho, posicionTooltipEscalada.y, ancho + margenFuente * 2, alto + margenFuente);
				break;
			case "so":
				tooltip = new Rectangle(posicionTooltipEscalada.x, posicionTooltipEscalada.y - alto, ancho + margenFuente * 2, alto + margenFuente);
				break;
			case "se":
				tooltip = new Rectangle(posicionTooltipEscalada.x - ancho, posicionTooltipEscalada.y - alto, ancho + margenFuente * 2, alto + margenFuente);
				break;
		}
		
		DibujoDebug.dibujarRectangulosRelleno(g, tooltip, Color.black);
		DibujoDebug.dibujarString(g, texto, 
				new Point(tooltip.x + margenFuente, tooltip.y + tooltip.height - margenFuente), Color.white);
		
	}
	
}
