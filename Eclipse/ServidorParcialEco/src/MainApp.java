import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class MainApp extends PApplet implements Observer {

	public static void main(String[] args) {
		
		// Se define el import del core
		PApplet.main("MainApp");
	}
	
	@Override
	public void settings() {
		
		// Tamano del canvas
		size(500,500);
	}
	
	// Variable que define la comunicación
	Comunicacion com;
	
	// Arreglo de los cuadros
	ArrayList<Cuadro> cuadros;
	
	@Override
	public void setup() {
		
		// Se inicializa el arraylist
		cuadros = new ArrayList<Cuadro>();
		
		// Se inicializa la comunicacion
		com = new Comunicacion();
		
		// Se implementa el observer, con this para atarlo a la clase main
		com.addObserver(this);
		
		// Se inicia el thread de la comunicacion
		new Thread(com).start();		
	}
	
	@Override
	public void draw() {
		
		// El fondo
		background(255);
		
		// Pinta el arraylist de la clase Cuadros
		for (int i = 0; i < cuadros.size(); i++) {
			cuadros.get(i).pintar(this);
		}
	}

	@Override
	public void update(Observable arg0, Object o) {	
		
		// Toda la implementacion del observer con los datos del cuadro
		if(o instanceof String) {
			
			// Recibe el string de android, y lo parsea como un string
			String mensaje = (String)o;
			
			// Parte el mensaje con un : y lo guarda en un arreglo de stirngs
			String[] partes = mensaje.split(":");
			
			// Define el x y el y de el cuadrado con las dos partes del string
			int x = Integer.parseInt(partes[0]);
			int y = Integer.parseInt(partes[1]);
			
			// Define el color del cuadrado con últiam parte del string
			String color = partes[2];
			
			// Anade el cuadrado al arraylist de cuadrados del main
			cuadros.add(new Cuadro(x, y, color));
			
			// Envia el # de cuadrados en el arreglo
			com.enviar("num:"+cuadros.size());
		}
	}
	
	// Al final, no nos funciono. Si conecta pero en android nunca pasa a la pantalla 2 y se crashea

}
