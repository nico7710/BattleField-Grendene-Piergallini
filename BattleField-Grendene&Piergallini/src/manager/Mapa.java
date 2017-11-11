package manager;

import java.util.ArrayList;
import ia.battle.core.FieldCell;

public class Mapa {
	
	ArrayList<FieldCell> celdasLodo;
	ArrayList<FieldCell> celdasPaquetes;
	private static Mapa instance;


	private Mapa() {
		celdasLodo = new ArrayList<>();
		celdasPaquetes = new ArrayList<>();
		
	}
	
	public static Mapa getInstance() {
		if(instance==null)
			instance = new Mapa();
		
		return instance;
	}

	public ArrayList<FieldCell> getCeldasLodo() {
		return celdasLodo;
	}

	public void addCeldaLodo(FieldCell celdaLodo) {
		this.celdasLodo.add(celdaLodo);
	}

	public ArrayList<FieldCell> getCeldasPaquetes() {
		return celdasPaquetes;
	}

	public void addCeldaPaquete(FieldCell celdaPaquete) {
		this.celdasPaquetes.add(celdaPaquete);
	}
	
	

}
