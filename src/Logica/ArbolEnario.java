package Logica;

public class ArbolEnario {

	public Nodo raiz;
	final char FINP = '}';

	public ArbolEnario() {
		raiz = new Nodo('?');
		raiz.hijo = new Nodo(FINP);
	}

	public void agregarPalabra(String pa) {
		char[] palabra = pa.toCharArray();
		Nodo o = raiz;
		Nodo p = null;
		Nodo q = null;
		Nodo r = null;
		for (int i = 0; i < palabra.length; i++) {
			q = o.hijo;
			p = new Nodo(palabra[i]);
			while (q != null) {
				if (q.valor > p.valor) {
					p.hermano = q;
					if (r == null) {
						o.hijo = p;
					} else {
						r.hermano = p;
					}
					q = p;
					for (int j = i + 1; j < palabra.length; j++) {
						p = new Nodo(palabra[j]);
						q.hijo = p;
						q = q.hijo;
					}
					p.hijo = new Nodo(FINP);// agrega finalizador a la frase
					return;
				} else if (q.valor < p.valor) {// avance en el nivel actual
					r = q;
					q = q.hermano;
				} else {// el caracter actual esta ya en el nivel
					r=null;
					o = q;
					break;
				}
			}
			if (q == null) {
				r.hermano = p;
				p.hermano = new Nodo(FINP);
				q = p;
				for (int j = i + 1; j < palabra.length; j++) {
					p = new Nodo(palabra[j]);
					q.hijo = p;
					q = q.hijo;
				}
				p.hijo = new Nodo(FINP);// agrega finalizador a la palabra
				return;
			}
		}
		q=q.hijo;
		while(q!=null){
			r=q;
			q=q.hermano;
		}
		if(r.caracter!=FINP){
			r.hermano=new Nodo(FINP);
		}
	}

}
