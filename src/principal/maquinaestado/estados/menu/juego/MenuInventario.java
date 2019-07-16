package principal.maquinaestado.estados.menu.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;
import principal.inventario.Inventario;

public class MenuInventario extends SeccionMenu{
	
	private int limitePeso = 100;
	private int pesoActual = 20;
	
	private final int margenGeneral = 8;
	
	private final Rectangle barraPeso;
	
	private final EstructuraMenu em;
	
	private final Inventario inventario;

	public MenuInventario(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
		super(nombreSeccion, etiquetaMenu);
		
		int anchoBarra = 100;
		int altoBarra = 8;
		
		this.em = em;
		
		inventario = new Inventario();
		
		barraPeso = new Rectangle(Constantes.ANCHO_JUEGO - anchoBarra - 12, em.BANNER_SUPERIOR.height + margenGeneral, anchoBarra, altoBarra);
	}

	public void actualizar() {
		
	}

	public void dibujar(final Graphics g, SuperficieDibujo sd) {
		dibujarLimitePeso(g);
		//dibujarElementosInventario(g, em);
		dibujarSpritesInventario(g, em);
		dibujarPaginador(g, em);
		if(sd.obtenerRaton().obtenerRectanguloPosicion().intersects(EscaladorElementos.escalarRectanguloArriba(barraPeso))) {
			GeneradorTooltip.dibujarTooltip(g, sd, pesoActual + "/" + limitePeso);
		}
	}
	
	private void dibujarLimitePeso(final Graphics g) {
		
		final Rectangle ContenidoBarra = new Rectangle(barraPeso.x + 1, barraPeso.y + 1, barraPeso.width / (limitePeso / pesoActual), barraPeso.height - 2);
		
		DibujoDebug.dibujarString(g, "Peso", barraPeso.x - 32, barraPeso.y + 8 - 1, Color.black);
		DibujoDebug.dibujarRectangulosRelleno(g, barraPeso, Color.gray);
		DibujoDebug.dibujarRectangulosRelleno(g, ContenidoBarra, Constantes.COLOR_NARANGA);
	}
	
	private void dibujarElementosInventario(final Graphics g, EstructuraMenu em) {
		final Point puntoInicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);
		
		g.setColor(Color.red);
		
		for(int y = 0; y < 7; y++) {
			for(int x = 0; x < 12; x++) {
				DibujoDebug.dibujarRectangulosRelleno(g, puntoInicial.x + x * (Constantes.LADO_SPRITE + margenGeneral),
						puntoInicial.y + y * (Constantes.LADO_SPRITE + margenGeneral), 
						Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			}
		}
	}
	
	private void dibujarSpritesInventario(final Graphics g, EstructuraMenu em) {
		
		final Point puntoInicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);
		
		g.setFont(g.getFont().deriveFont(6f));
		for(int i = 0; i < inventario.objetos.size(); i++) {
			DibujoDebug.dibujarImagen(g, inventario.objetos.get(i).obtenerSprite().getImagen(),
					puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral), puntoInicial.y);
			
			DibujoDebug.dibujarRectangulosRelleno(g, puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral) + Constantes.LADO_SPRITE - 12,
					puntoInicial.y + Constantes.LADO_SPRITE - 8, 12, 8, Color.black);
			
			String texto = ""+inventario.objetos.get(i).obtenerCantidad();
			
			DibujoDebug.dibujarString(g, texto, puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral) + Constantes.LADO_SPRITE - MedidorString.medirAnchoPixeles(g, texto),
					puntoInicial.y + Constantes.LADO_SPRITE - 1, Color.white);
		}
		g.setFont(g.getFont().deriveFont(9f));
	}
	
	private void dibujarPaginador(final Graphics g, EstructuraMenu em) {
		final int anchoBoton = 32;
		final int altoBoton = 16;
		
		final Rectangle anterior = new Rectangle(em.FONDO.x + em.FONDO.width - margenGeneral * 2 - anchoBoton * 2 - 4,
				em.FONDO.y + em.FONDO.height - margenGeneral - altoBoton, anchoBoton, altoBoton);
		
		final Rectangle siguiente = new Rectangle(anterior.x + anterior.width + margenGeneral,
				anterior.y, anchoBoton, altoBoton);
		
		g.setColor(Color.blue);
		
		DibujoDebug.dibujarRectangulosRelleno(g, anterior);
		DibujoDebug.dibujarRectangulosRelleno(g, siguiente);
	}
	

}
