package principal.graficos;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private String titulo;
	
	private final ImageIcon iconoVentana;
	
	public Ventana(final String titulo, final SuperficieDibujo sd) {
		this.titulo = titulo;
		
		BufferedImage imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(Constantes.ICONO_VENTANA);
		this.iconoVentana = new ImageIcon(imagen);
		
		configurarVentana(sd);
	}
	
	private void configurarVentana(final SuperficieDibujo sd) {
		setTitle(titulo);
		setIconImage(iconoVentana.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		add(sd, BorderLayout.CENTER);
		setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
