package Logica;

public class ArbolEnario {

	public Nodo raiz;
	public Archivo archivo;
	final char FINP = '}';

	public ArbolEnario() {
		raiz = new Nodo('?');
		raiz.hijo = new Nodo(FINP);
		archivo=new Archivo();
		if(archivo.exists){
			try {
				raiz = archivo.recrearArbol();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			raiz.num_p=archivo.siguienteLibre();
			raiz.hijo.num_p=archivo.siguienteLibre();
			archivo.modificarNodo(raiz);
			archivo.modificarNodo(raiz.hijo);
		}
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
					p.num_p=archivo.siguienteLibre();
					p.hermano = q;
					archivo.modificarNodo(p);
					if (r == null) {
						o.hijo = p;
						archivo.modificarNodo(o);
					} else {
						r.hermano = p;
						archivo.modificarNodo(r);
					}
					q = p;
					for (int j = i + 1; j < palabra.length; j++) {
						p = new Nodo(palabra[j]);
						p.num_p=archivo.siguienteLibre();
						archivo.modificarNodo(p);
						q.hijo = p;
						archivo.modificarNodo(q);
						q = q.hijo;
					}
					r=new Nodo(FINP);
					r.num_p=archivo.siguienteLibre();
					archivo.modificarNodo(r);
					p.hijo = r;// agrega finalizador a la frase
					archivo.modificarNodo(p);
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
				p.num_p=archivo.siguienteLibre();
				r.hermano = p;
				archivo.modificarNodo(p);
				archivo.modificarNodo(r);/*
				o=new Nodo(FINP);
				o.num_p=archivo.siguienteLibre();
				archivo.modificarNodo(o);
				p.hermano = o;
				archivo.modificarNodo(p);*/
				q = p;
				for (int j = i + 1; j < palabra.length; j++) {
					p = new Nodo(palabra[j]);
					p.num_p=archivo.siguienteLibre();
					archivo.modificarNodo(p);
					q.hijo = p;
					archivo.modificarNodo(q);
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
			p = new Nodo(FINP);
			p.num_p=archivo.siguienteLibre();
			archivo.modificarNodo(p);
			r.hermano=new Nodo(FINP);
			archivo.modificarNodo(r);
		}
	}

	public boolean buscarPalabra(String p){
		p+="}";
		raiz.conPalabra=true;
		raiz.inPalabra=true;
		raiz.hijo.conPalabra=true;
		raiz.hijo.inPalabra=false;
		char[] palabra=p.toCharArray();
		Nodo q=raiz.hijo;
		for(int i=0; i<palabra.length;i++){
			while(q!=null){
				if(q.caracter==palabra[i]){
					q.conPalabra=true;
					q.inPalabra=true;
					q=q.hijo;
					break;
				}
				else{
					q.conPalabra=true;
					q.inPalabra=false;
					q=q.hermano;
				}
			}
			if(q==null && palabra[i]!='}')
				return false;
		}
		return true;
	}
	
}
