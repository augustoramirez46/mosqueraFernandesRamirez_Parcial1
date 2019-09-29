import processing.core.PApplet;

public class Cuadro {
	//no creemos que se deba comentar todo esto, es la clase de los cuadros que se pintan en la pantalla
	private int x;
	private int y;
	private String color;
	
	//Tiene entrada de X y Y, definidas en android
	public Cuadro(int x, int y, String color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	
	public void pintar(PApplet app) {
		
		//El switch corresponde a la variable recibida de android
		switch(color) {
		case "rojo":
			app.fill(255,0,0);
			break;
		case "verde":
			app.fill(0,255,0);
			break;
		case "azul":
			app.fill(0,0,255);
			break;
		}
		
		//pinta el instance del cuadro con el color y la posición
		app.rectMode(app.CENTER);
		app.rect(x, y, 50, 50);
	}	

}
