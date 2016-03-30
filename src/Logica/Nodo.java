package Logica;

public class Nodo {
	public int valor;
	public char caracter;
	// para la busqueda de palabra
	public boolean inPalabra;
	public boolean conPalabra;
	
	public Nodo hermano;
	public Nodo hijo;
	public int num_p;
	
	public Nodo(char c) {
		caracter = c;
		valor =c;
		inPalabra=false;
		
		hermano=null;
		hijo=null;
		
		num_p=0;
	}
}
