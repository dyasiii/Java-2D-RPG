package principal.inventario;

import java.awt.Point;

public class ContenedorObjetos {
	
	private Point posicion;
	private Objeto[] objetos;
	
	public ContenedorObjetos(final Point posicion, final int[] objetos, final int[] cantidades) {
		
		this.posicion = posicion;
		this.objetos = new Objeto[objetos.length];
		
		for(int i = 0; i < objetos.length; i++) {
			this.objetos[i] = RegistroObjetos.objetos[objetos[i]];
			this.objetos[i].incrementarCantidad(cantidades[i]);
		}
		
	}
}
