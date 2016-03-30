package Vista;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.RandomAccessFile;

import Logica.Nodo;

public class CanvasArbol extends Canvas {

	private static final long serialVersionUID = 1L;
	private Image imagen;
	private Graphics graficas;
	private Nodo cabeza;
	private int difX;
	private int difY;
	private String inOrden;
	private RandomAccessFile a;
	private boolean buscar;

	public void setCabeza(Nodo c) {
		buscar=false;
		cabeza = c;
		repaint();
	}
	
	public void pintarCursor(){
		buscar=false;
		cabeza=null;
		repaint();
	}
	
	public void buscarPalabra(Nodo c){
		buscar=true;
		cabeza=c;
		repaint();
	}

	@Override
	public void update(Graphics g) {
		imagen = createImage(this.getWidth(), this.getHeight());
		graficas = imagen.getGraphics();
		graficas.setColor(Color.black);
		graficas.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (cabeza != null) {
			difX = this.getWidth() / 40;
			difY = this.getHeight() / 20;
			inOrden = "";
			if(buscar){
				buscarPalabra(cabeza, 40, 40, 40, 40);
			}
			else{
				pintarArbolInOrden(cabeza, 40, 40, 40, 40, g);
			}
		}
		else{
			pintarTabla();
		}
		g.drawImage(imagen, 0, 0, this);
	}

	private int pintarArbolInOrden(Nodo n, int pocX, int pocY, int pocAX,
			int pocAY, Graphics gg) {
		int a;
		if (n != null) {
			n.inPalabra=false;
			n.conPalabra=false;
			graficas.setColor(Color.cyan);
			graficas.drawLine(pocX + 1, pocY + 1, pocAX + 1, pocAY + 1);
			graficas.drawLine(pocX, pocY, pocAX, pocAY);
			graficas.drawLine(pocX - 1, pocY - 1, pocAX - 1, pocAY - 1);
			a=pintarArbolInOrden(n.hijo, pocX, pocY + difY, pocX, pocY, gg);
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
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			a+=pintarArbolInOrden(n.hermano, a+ pocX+difX, pocY, pocX, pocY, gg);
			if(n.hermano!=null)
				a+=difX;
			return a;
		}
		return 0;
	}
	
	private int buscarPalabra(Nodo n, int pocX, int pocY, int pocAX,
			int pocAY) {
		int a;
		if (n != null) {
			if(n.conPalabra){
				graficas.setColor(Color.cyan);
				graficas.drawLine(pocX + 1, pocY + 1, pocAX + 1, pocAY + 1);
				graficas.drawLine(pocX, pocY, pocAX, pocAY);
				graficas.drawLine(pocX - 1, pocY - 1, pocAX - 1, pocAY - 1);
				a=buscarPalabra(n.hijo, pocX, pocY + difY, pocX, pocY);
				if(n.inPalabra){
					graficas.setColor(Color.white);
					graficas.fillRect(pocX, pocY - 20, 20, 20);
					graficas.setColor(Color.blue);
					graficas.drawString(n.caracter + "", pocX + 5, pocY - 5);
				}
				a+=buscarPalabra(n.hermano, difX + pocX, pocY, pocX, pocY);
				if(n.hermano!=null)
					a+=difX;
				return a;
			}
		}
		return 0;
	}
	

	public void pintarTabla() {
		try {
			a = new RandomAccessFile("arbol.dat", "rw");
			int lado = 20;
			long l = a.length()/12;
			int ay = 0;
			int pocX=0;
			graficas.setColor(Color.cyan);
			graficas.drawString("char", (lado * 2)+3 , lado-3);
			graficas.drawString("hijo", (lado * 4)+3, lado-3);
			graficas.drawString("herm", (lado * 6)+3, lado-3);
			a.seek(0);
			graficas.setColor(Color.white);
			for (int i = 0; i < l ; i++) {
				if(i%27==0 && i!=0){
					pocX+=lado*9;
				}
				graficas.drawString("->"+i, pocX+3, (((i%27)+2)*lado)-3);
				ay = a.readInt();
				graficas.drawRect(pocX+(2 *lado), lado*((i%27)+1), 2*lado, lado);
				graficas.drawString((char)(ay)+"", pocX+(lado * 2)+3, (((i%27) + 2) * lado)-3);
				ay = a.readInt();
				graficas.drawRect(pocX+(4 *lado), lado*((i%27)+1), 2*lado, lado);
				graficas.drawString(ay+"", pocX+(lado * 4)+3, (((i%27) + 2) * lado)-3);
				ay = a.readInt();
				graficas.drawRect(pocX+(6 *lado), lado*((i%27)+1), 2*lado, lado);
				graficas.drawString(ay+"", pocX+(lado * 6)+3, (((i%27) + 2) * lado)-3);
			}
			a.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
