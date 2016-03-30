package Logica;

import java.io.RandomAccessFile;

public class Archivo {
	public RandomAccessFile a;
	public boolean exists;
	String file = "arbol.dat";

	public Archivo() {
		try {
			a = new RandomAccessFile(file, "rw");
			if (a.length() > 0) {
				exists = true;
			} else {
				for (int i = 1; i < 3; i++) {
					a.writeInt(0);
					a.writeInt(0);
					a.writeInt(i);
				}
				a.writeInt(0);
				a.writeInt(0);
				a.writeInt(0);
				a.seek(0);
				a.writeInt(1);
				exists = false;
			}
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int aumentarArchivo() {
		int b = 0;
		try {
			b = (int) (a.length()/12);
			a.seek(b * 12);
			for (int i = 1; i < 5; i++) {
				a.writeInt(0);
				a.writeInt(0);
				a.writeInt(b + i);
			}
			a.writeInt(0);
			a.writeInt(0);
			a.writeInt(0);
			a.seek(8);
			a.writeInt(b);
		} catch (Exception ex) {
			ex.getStackTrace();
		}
		return b;
	}

	public int siguienteLibre() {
		int i, j;
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(8);
			i = a.readInt();
			if (i == 0) {
				i = aumentarArchivo();
			}
			a.seek((i *12) + 8);
			j = a.readInt();
			a.seek(8);
			a.writeInt(j);
			a.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			i = 0;
		}
		return i;
	}

	public void modificarNodo(Nodo n) {
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(n.num_p * 12);
			a.writeInt(n.valor);
			if (n.hijo != null)
				a.writeInt(n.hijo.num_p);
			else
				a.writeInt(0);
			if (n.hermano != null)
				a.writeInt(n.hermano.num_p);
			else
				a.writeInt(0);
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarNodo(int n) {
		int i = 0;
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(8);
			i = a.readInt();
			a.seek(12 * n);
			a.writeInt(0);
			a.writeInt(0);
			a.writeInt(i);
			a.seek(8);
			a.writeInt(n);
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Nodo recrearArbol() throws Exception {
		a = new RandomAccessFile(file, "rw");
		int b = a.readInt();
		a.close();
		Nodo n = reconstruir(b);
		return n;
	}

	private Nodo reconstruir(int poc) {
		int i = 0, j = 0;
		Nodo n = null;
		try {
			a = new RandomAccessFile(file, "rw");
			a.seek(12 * poc);
			i = a.readInt();
			n = new Nodo((char)i);
			n.num_p = poc;
			i = a.readInt();
			j = a.readInt();
			a.close();
			if (i > 0) {
				n.hijo = reconstruir(i);
			}
			if (j > 0) {
				n.hermano = reconstruir(j);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

}