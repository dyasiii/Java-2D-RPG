package principal;

import java.awt.Color;
import java.awt.Font;

import principal.herramientas.CargadorRecursos;

public class Constantes {	
	public static final int LADO_SPRITE = 32;
	public static int LADO_CURSOR = 0;
	
	public static int ANCHO_JUEGO = 640; //640
	public static int ALTO_JUEGO = 360; //360
	
	public static int ANCHO_PANTALLA_COMPLETA = 1600;//1600
	public static int ALTO_PANTALLA_COMPLETA = 900; //900
	
	public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA / (double) ANCHO_JUEGO;
	public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA / (double) ALTO_JUEGO;
	
	public static int CENTRO_VENTANA_X = ANCHO_JUEGO / 2;
	public static int CENTRO_VENTANA_Y = ALTO_JUEGO / 2;
	
	public static String RUTA_MAPA = "/Archivos-texto/Mapas/testmap01.game.resource.map";
	public static String ICONO_RATON = "/imagenes/Iconos/iconoCursor.png";
	public static String RUTA_PERSONAJE = "/imagenes/hojasPersonajes/2.png";
	public static String ICONO_VENTANA = "/imagenes/Iconos/iconoVentana.png";
	public static String RUTA_LOGOTIPO = "/imagenes/Iconos/logotipo.png";
	public static String RUTA_OBJETOS = "/imagenes/hojasObjetos/1.png";
	
	public static Font FUENTE_PRINCIPAL = CargadorRecursos.cargarFuente("/Fuentes/BULKYPIX.TTF");
	
	public static Color COLOR_NARANGA = new Color(0xff6700);

}
