package principal.inventario;

import java.util.ArrayList;

import principal.Constantes;
import principal.sprites.HojaSprites;

public class Inventario {

	private final HojaSprites hojaObjetos;
	public final ArrayList<Objeto> objetos;
	
	public Inventario() {
		hojaObjetos = new HojaSprites(Constantes.RUTA_OBJETOS, Constantes.LADO_SPRITE, false);
		objetos = new ArrayList<Objeto>();
		
		objetos.add(RegistroObjetos.objetos[0]);
		objetos.add(RegistroObjetos.objetos[1]);
		objetos.add(RegistroObjetos.objetos[2]);
		objetos.add(RegistroObjetos.objetos[3]);
		objetos.add(RegistroObjetos.objetos[4]);
		objetos.add(RegistroObjetos.objetos[5]);
		
		incrementarObjeto(RegistroObjetos.objetos[0], 64);
	}
	
	public boolean incrementarObjeto(final Objeto objeto, final int cantidad) {
		boolean incrementado = false;
		
		for(Objeto objetoActual : objetos) {
			if(objetoActual.obtenerID() == objeto.obtenerID()) {
				objetoActual.incrementarCantidad(cantidad);
				incrementado = true;
				break;
			}
		}
		
		return incrementado;
	}
	
}
