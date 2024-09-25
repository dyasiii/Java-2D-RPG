package principal.mapas;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Mapa {

	private String[] datosMapa;
	
	private int ancho;
	private int alto;
	
	private final Point posicionInicial;
	private final Point puntoSalida;
	
	private Rectangle zonaSalida;
	
	private String siguienteMapa;
	
	private final Sprite[] paletaSprites;

	private final boolean[] colisiones;
	
	public ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();
	
	private final int[] spritesExtraidos;
	
	private final int margenX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
	private final int margenY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
	
	public Mapa(final String ruta) {
		String contenidoMapa = CargadorRecursos.leerArchivoTexto(ruta);
		
		datosMapa = contenidoMapa.split("\\*");
		
		ancho = Integer.parseInt(datosMapa[0]);
		alto = Integer.parseInt(datosMapa[1]);

		String hojasUtilizadas = datosMapa[2];
		String[] hojasSeparadas = hojasUtilizadas.split(",");
		
		String paletaEntera = datosMapa[3];
		String[] partesPaleta = paletaEntera.split("#");
		
		paletaSprites = asignarSprites(partesPaleta, hojasSeparadas);
		
		String colisionesTotal = datosMapa[4];
		colisiones = extraerColisiones(colisionesTotal);

		String spritesTotal = datosMapa[5];
		String[] cadenasSprites = spritesTotal.split(" ");
		
		String posicion = datosMapa[6];
		String[] posiciones = posicion.split("-");
		
		posicionInicial = new Point();
		posicionInicial.x = Integer.parseInt(posiciones[0]) * Constantes.LADO_SPRITE;
		posicionInicial.y = Integer.parseInt(posiciones[1]) * Constantes.LADO_SPRITE;
		
		String salida = datosMapa[7];
		String[] datosDeSalida = salida.split("-");
		
		puntoSalida = new Point();
		puntoSalida.x = Integer.parseInt(datosDeSalida[0]);
		puntoSalida.y = Integer.parseInt(datosDeSalida[1]);
		siguienteMapa = datosDeSalida[2];

		spritesExtraidos = extraerSprites(cadenasSprites);
		/*for(int i = 0; i < spritesExtraidos.length; i ++) {
			System.out.println(spritesExtraidos[i]);
		}*/
		
		zonaSalida = new Rectangle();
	}
	
	private Sprite[] asignarSprites(final String[] partesPaleta, final String[] hojasSeparadas) {
		Sprite[] paletaSprites = new Sprite[partesPaleta.length];
		
		HojaSprites hs = new HojaSprites("/imagenes/Hojas-de-Sprites/texturas/" + hojasSeparadas[0] + ".png", 32, false);
		
		for(int i = 0; i < partesPaleta.length; i++) {
			String spriteTemporal = partesPaleta[i];
			String[] partesSprite = spriteTemporal.split("-");
			
			int indicePaleta = Integer.parseInt(partesSprite[0]);
			int indiceSpriteHoja = Integer.parseInt(partesSprite[2]);
			
			paletaSprites[indicePaleta] = hs.getSprite(indiceSpriteHoja);
		}
		
		return paletaSprites;
	}
	
	private boolean[] extraerColisiones(final String cadenaColisiones) {
		
		boolean[] colisiones = new boolean[cadenaColisiones.length()];
		
		for(int i = 0; i < cadenaColisiones.length(); i++) {
			if(cadenaColisiones.charAt(i) == '0') {
				colisiones[i] = false;
			}else {
				colisiones[i] = true;
			}
		}
		return colisiones;
	}
	
	private int[] extraerSprites(final String[] cadenaSprites) {
		ArrayList<Integer> spritesExtraidos = new ArrayList<Integer>();
		
		for(int i = 0; i < cadenaSprites.length; i++) {
			if(cadenaSprites[i].length() == 2) {
				spritesExtraidos.add(Integer.parseInt(cadenaSprites[i]));
			}else {
				String uno = "";
				String dos = "";
				
				String error = cadenaSprites[i];
				
				uno += error.charAt(0);
				uno += error.charAt(1);
				dos += error.charAt(2);
				dos += error.charAt(3);
				
				spritesExtraidos.add(Integer.parseInt(uno));
				spritesExtraidos.add(Integer.parseInt(dos));
			}
		}
		
		int[] vectorSprites = new int[spritesExtraidos.size()];
		
		for(int i = 0; i < spritesExtraidos.size(); i++) {
			vectorSprites[i] = spritesExtraidos.get(i);
		}
		
		return vectorSprites;
	}
	
	public void actualizar(final int pX, final int pY) {
		actualizarAreasColision(pX, pY);
		actualizarZonaSalida(pX, pY);
	}
	
	private void actualizarAreasColision(final int pX, final int pY) {
		if(!areasColision.isEmpty()) {
			areasColision.clear();
		}
		
		for(int y = 0; y < this.alto; y++) {
			for(int x = 0; x < this.ancho; x++) {
				int puntoX = x * Constantes.LADO_SPRITE - pX + margenX;
				int puntoY = y * Constantes.LADO_SPRITE - pY + margenY;
				
				if(colisiones[x + y * this.ancho]) {
					final Rectangle r = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
					areasColision.add(r);
				}
			}
		}
	}
	
	private void actualizarZonaSalida(final int pX, final int pY) {
		int puntoX = ((int) puntoSalida.getX()) * Constantes.LADO_SPRITE - pX + margenX;
		int puntoY = ((int) puntoSalida.getY()) * Constantes.LADO_SPRITE - pY + margenY;
		
		zonaSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}
	
	public void dibujar(Graphics g, int pX, int pY) {
		
		for(int y = 0; y < this.alto; y++) {
			for(int x = 0; x < this.ancho; x++) {
				BufferedImage img = paletaSprites[spritesExtraidos[x + y * this.ancho]].getImagen();
				
				int puntoX = x * Constantes.LADO_SPRITE - pX + margenX;
				int puntoY = y * Constantes.LADO_SPRITE - pY + margenY;
				
				DibujoDebug.dibujarImagen(g, img, puntoX, puntoY);
			}
		}
	}
	
	public Rectangle obtenerBordes(final int pX, final int pY, final int anchoJugador, final int altoJugador) {
		int x = margenX - pX + anchoJugador;
		int y = margenY - pY + altoJugador;
		int ancho = this.ancho * Constantes.LADO_SPRITE - anchoJugador * 2;
		int alto = this.alto * Constantes.LADO_SPRITE - altoJugador * 2;
		
		return new Rectangle(x, y, ancho, alto);
	}

	public Point getPosicionInicial() {
		return posicionInicial;
	}

	public String getSiguienteMapa() {
		return siguienteMapa;
	}

	public void setSiguienteMapa(String siguienteMapa) {
		this.siguienteMapa = siguienteMapa;
	}

	public Point getPuntoSalida() {
		return puntoSalida;
	}

	public Rectangle getZonaSalida() {
		return zonaSalida;
	}

	public void setZonaSalida(Rectangle zonaSalida) {
		this.zonaSalida = zonaSalida;
	}
}
