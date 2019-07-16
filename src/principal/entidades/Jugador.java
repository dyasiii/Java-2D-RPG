package principal.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.controles.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

public class Jugador {
	
	private double pX;
	private double pY;

	private double velocidad = 1;
	
	private int direccion;
	
	private boolean enMovimiento;
	
	private HojaSprites hs;
	
	private BufferedImage imagenPersonaje;
	
	private final int ANCHO_JUGADOR = 16;
	private final int ALTO_JUGADOR = 16;
	
	private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.ANCHO_JUEGO / 2 - ANCHO_JUGADOR / 2, Constantes.ALTO_JUEGO / 2, ANCHO_JUGADOR, 1);
	private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.ANCHO_JUEGO / 2 - ANCHO_JUGADOR / 2, Constantes.ALTO_JUEGO / 2 + ALTO_JUGADOR, ANCHO_JUGADOR, 1);
	private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.ANCHO_JUEGO / 2 - ANCHO_JUGADOR / 2, Constantes.ALTO_JUEGO / 2, 1, ANCHO_JUGADOR);
	private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.ANCHO_JUEGO / 2 + ANCHO_JUGADOR / 2, Constantes.ALTO_JUEGO / 2,  1, ALTO_JUGADOR);

	private int animacion;
	private int estado;
	
	private Mapa mapa;
	
	public static final int RESISTENCIA_TOTAL = 600;
	public int resistencia = 600;
	private int recuperacion = 0;
	private final int RECUPERACION_MAXIMA = 100;	
	private boolean recuperado = true;
	
	public Jugador(Mapa mapa) {
		pX = mapa.getPosicionInicial().getX();
		pY = mapa.getPosicionInicial().getY();
		
		direccion = 0;
		
		enMovimiento = false;
		
		hs = new HojaSprites(Constantes.RUTA_PERSONAJE, Constantes.LADO_SPRITE, false);
		imagenPersonaje = hs.getSprite(0).getImagen();
		
		animacion = 0;
		estado = 0;
		
		this.mapa = mapa;
	}
	
	public void actualizar() {
		gestionarVelocidadResistencia();
		cambiarAnimacionEstado();
		enMovimiento = false;
		determinarDireccion();
		animar();
	}
	
	public void gestionarVelocidadResistencia() {
		if(GestorControles.teclado.correr && resistencia > 0) {
			velocidad = 2;
			recuperado = false;
			recuperacion = 0;
		}else {
			velocidad = 1;
			if(!recuperado && recuperacion < RECUPERACION_MAXIMA) {
				recuperacion++;
			}
			if(recuperacion == RECUPERACION_MAXIMA && resistencia < 600) {
				resistencia++;
			}
		}
	}
	
	private void animar() {
		if(!enMovimiento) {
			estado = 0;
			animacion = 0;
		}
		
		imagenPersonaje = hs.getSprite(direccion, estado).getImagen();
	}

	private void determinarDireccion() {
		final int velocidadX = evaluarVelocidadX();
		final int velocidadY = evaluarVelocidadY();
		
		if(velocidadX == 0 && velocidadY == 0) {
			return;
		}if((velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)) {
			mover(velocidadX, velocidadY);
		}else {
			if(velocidadX == -1 && velocidadY == -1) {
				if(GestorControles.teclado.izquierda.obtenerUltimaPulsacion() > GestorControles.teclado.arriba.obtenerUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
			
			if(velocidadX == -1 && velocidadY == 1) {
				if(GestorControles.teclado.izquierda.obtenerUltimaPulsacion() > GestorControles.teclado.abajo.obtenerUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
			
			if(velocidadX == 1 && velocidadY == -1) {
				if(GestorControles.teclado.derecha.obtenerUltimaPulsacion() > GestorControles.teclado.arriba.obtenerUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
			
			if(velocidadX == 1 && velocidadY == 1) {
				if(GestorControles.teclado.derecha.obtenerUltimaPulsacion() > GestorControles.teclado.arriba.obtenerUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
		}
	}
	
	private int evaluarVelocidadX() {
		int velocidadX = 0;
		
		if(GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
			velocidadX = -1;
		}else if(GestorControles.teclado.derecha.estaPulsada() && !GestorControles.teclado.izquierda.estaPulsada()) {
			velocidadX = 1;
		}
		
		return velocidadX;
	}
	
	private int evaluarVelocidadY() {
		int velocidadY = 0;
		
		if(GestorControles.teclado.arriba.estaPulsada() && !GestorControles.teclado.abajo.estaPulsada()) {
			velocidadY = -1;
		}else if(GestorControles.teclado.abajo.estaPulsada() && !GestorControles.teclado.arriba.estaPulsada()) {
			velocidadY = 1;
		}
		
		return velocidadY;
	}
	
	private void mover(final int velocidadX, final int velocidadY) {
				
		enMovimiento = true;

		cambiarDireccion(velocidadX, velocidadY);

		if (!fueraMapa(velocidadX, velocidadY)) {
			if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
				pX += velocidadX * velocidad;
				restarResistencia();
			}
			if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
				pX += velocidadX * velocidad;
				restarResistencia();
			}
			if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
				pY += velocidadY * velocidad;
				restarResistencia();
			}
			if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
				pY += velocidadY * velocidad;
				restarResistencia();
			}
		}
	}
	
	private void restarResistencia() {
		if(GestorControles.teclado.correr && resistencia > 0) {
			resistencia--;
		}
	}
	
	private boolean enColisionArriba(int velocidadY) {
		for(int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);
			
			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad + 3 * (int) velocidad;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_ARRIBA.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean enColisionAbajo(int velocidadY) {
		for(int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);
			
			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 3 * (int) velocidad;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_ABAJO.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean enColisionIzquierda(int velocidadX) {
		for(int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);
			
			int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;;
			int origenY = area.y;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_IZQUIERDA.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean enColisionDerecha(int velocidadX) {
		for(int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);
			
			int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;
			int origenY = area.y;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			if(LIMITE_DERECHA.intersects(areaFutura)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean fueraMapa(final int velocidadX, final int velocidadY) {
		int pFuturaX = (int)pX + velocidadX * (int) velocidad;
		int pFuturaY = (int)pY + velocidadY * (int) velocidad;
		
		final Rectangle bordeMapa = mapa.obtenerBordes(pFuturaX, pFuturaY, ANCHO_JUGADOR, ALTO_JUGADOR);
		
		final boolean fuera;
		
		if(LIMITE_ARRIBA.intersects(bordeMapa) || LIMITE_ABAJO.intersects(bordeMapa) || LIMITE_IZQUIERDA.intersects(bordeMapa) || LIMITE_DERECHA.intersects(bordeMapa)) {
			fuera = false;
		}else {
			fuera = true;
		}
		return fuera;
	}
	
	private void cambiarDireccion(final int velocidadX, final int velocidadY) {
		if(velocidadX == -1) {
			direccion = 3;
		}else if(velocidadX == 1) {
			direccion = 2;
		}else if(velocidadY == -1) {
			direccion = 1;
		}else if(velocidadY == 1) {
			direccion = 0;
		}
	}

	private void cambiarAnimacionEstado() {
		if(animacion < 30) {
			animacion++;
		}else {
			animacion = 0;
		}
		
		if(animacion < 15) {
			estado = 1;
		}else {
			estado = 2;
		}
	}
	
	public void dibujar(Graphics g) {
		final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		
		g.drawImage(imagenPersonaje, centroX, centroY, null);
		DibujoDebug.dibujarImagen(g, imagenPersonaje, centroX, centroY);
		g.setColor(Color.white);
	}
	
	public void setPosicionX(double posicionX) {
		this.pX = posicionX;
	}
	
	public double getPosicionX() {
		return pX;
	}
	
	public void setPosicionY(double posicionY) {
		this.pY = posicionY;
	}
	
	public double getPosicionY() {
		return pY;
	}

	public Rectangle getLIMITE_ARRIBA() {
		return LIMITE_ARRIBA;
	}
}
