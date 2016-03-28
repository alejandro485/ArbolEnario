package Vista;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import Logica.Nodo;

public class CanvasArbol extends Canvas {

	private static final long serialVersionUID = 1L;
	private Image imagen;
	private Graphics graficas;
	private Nodo cabeza;
	private int difX;
	private int difY;
	private int desp;
	private String inOrden;

	private int opcion;

	public void setCabeza(Nodo c, int op) {
		opcion = op;
		cabeza = c;
		repaint();
	}

	@Override
	public void update(Graphics g) {
		imagen = createImage(this.getWidth(), this.getHeight());
		graficas = imagen.getGraphics();
		graficas.setColor(Color.black);
		graficas.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (cabeza != null) {
			if (opcion == 1) {
				desp = 0;
				difX = this.getWidth() / 40;
				difY = this.getHeight() / 20;
				inOrden = "";
				pintarArbolInOrden(cabeza, 40, 40, 40, 40, g);
			}
			if(opcion==2){
				
			}
		}
		g.drawImage(imagen, 0, 0, this);
	}

	private void pintarArbolInOrden(Nodo n, int pocX, int pocY, int pocAX,
			int pocAY, Graphics gg) {
		if (n != null) {
			graficas.setColor(Color.cyan);
			graficas.drawLine(pocX + 1, pocY + 1, pocAX + 1, pocAY + 1);
			graficas.drawLine(pocX, pocY, pocAX, pocAY);
			graficas.drawLine(pocX - 1, pocY - 1, pocAX - 1, pocAY - 1);
			pintarArbolInOrden(n.hijo, pocX, pocY + difY, pocX, pocY, gg);
			graficas.setColor(Color.white);
			graficas.fillRect(pocX, pocY - 20, 20, 20);
			graficas.setColor(Color.blue);
			graficas.drawString(n.caracter + "", pocX + 5, pocY - 5);
			try {
				graficas.setColor(Color.white);
				graficas.drawString(n.caracter + "",
						(inOrden.length() * 9) + 9, 15);
				inOrden += n.caracter + "";
				gg.drawImage(imagen, 0, 0, this);
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (n.hermano != null)
				desp += difX;
			pintarArbolInOrden(n.hermano, desp + pocX, pocY, pocX, pocY, gg);
		}
	}
}
