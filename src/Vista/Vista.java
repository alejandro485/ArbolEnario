package Vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import Logica.ArbolEnario;

public class Vista extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JButton btnAgregar;
	private JButton btnBuscar;
	private JButton btnPintar;
	private JButton btnCursor;
	private JTextField txtPalabra;
	private CanvasArbol canvas;
	
	private ArbolEnario arbol;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1088, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPalabra=new JTextField();
		txtPalabra.setBounds(90,12,300,20);
		contentPane.add(txtPalabra);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(422, 12, 117, 25);
		btnAgregar.setActionCommand("ag");
		btnAgregar.addActionListener(this);
		contentPane.add(btnAgregar);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(551, 12, 117, 25);
		btnBuscar.setActionCommand("br");
		btnBuscar.addActionListener(this);
		contentPane.add(btnBuscar);
		
		btnPintar = new JButton("Pintar");
		btnPintar.setBounds(680, 12, 117,25);
		btnPintar.setActionCommand("pt");
		btnPintar.addActionListener(this);
		contentPane.add(btnPintar);
		
		btnCursor=new JButton("Cursor");
		btnCursor.setBounds(810,12,117,25);
		btnCursor.setActionCommand("cs");
		btnCursor.addActionListener(this);
		contentPane.add(btnCursor);
		
		canvas=new CanvasArbol();
		canvas.setBounds(12,40,1000,600);
		canvas.setVisible(true);
		contentPane.add(canvas);
		
		arbol=new ArbolEnario();
		canvas.setCabeza(arbol.raiz);
		
		JLabel lblPalabra = new JLabel("Palabra");
		lblPalabra.setBounds(12, 17, 70, 15);
		contentPane.add(lblPalabra);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ag")){
			arbol.agregarPalabra(txtPalabra.getText());
			canvas.setCabeza(arbol.raiz);
		}
		if(e.getActionCommand().equals("br")){
			if(arbol.buscarPalabra(txtPalabra.getText())){
				canvas.buscarPalabra(arbol.raiz);
			}
			else{
				JOptionPane.showConfirmDialog(null, "No se encontro palabra");
				canvas.setCabeza(arbol.raiz);
			}
		}
		if(e.getActionCommand().equals("pt")){
			canvas.setCabeza(arbol.raiz);
		}
		if(e.getActionCommand().equals("cs")){
			canvas.pintarCursor();
		}
	}
}
