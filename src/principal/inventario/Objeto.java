package principal.inventario;

import principal.Constantes;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Objeto {
	
	public static HojaSprites hojaObjetos = new HojaSprites(Constantes.RUTA_OBJETOS, Constantes.LADO_SPRITE, false);
	
	private final int id;
	private final String nombre;
	private final String descripcion;
	private final Sprite sprite;
	
	private int cantidad;
	private int cantidadMaxima;
	
	public Objeto(final int id, final String nombre, final String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sprite = hojaObjetos.getSprite(id);
		
		cantidad = 0;
		cantidadMaxima = 64;
	}
	
	public Objeto(final int id, final String nombre, final String descripion, final int cantidad) {
		this(id, nombre, descripion);
		if(cantidad <= cantidadMaxima) {
			this.cantidad = cantidad;
		}
	}
	
	public boolean incrementarCantidad(final int incremento) {
		boolean incrementado = false;
		
		if(cantidad + incremento <= cantidadMaxima) {
			cantidad += incremento;
			incrementado = true;
		}
		
		return incrementado;
	}
	
	public boolean reducirCantidad(final int reduccion) {
		boolean reducido = false;
		
		if(cantidad - reduccion >= 0) {
			cantidad -= reduccion;
			reducido = true;
		}
		
		return reducido;
	}
	
	public Sprite obtenerSprite() {
		return sprite;
	}
	
	public int obtenerCantidad() {
		return cantidad;
	}
	
	public int obtenerID() {
		return id;
	}
}
