package Astar;
import java.util.ArrayList;
import java.util.Collections;

import ia.battle.core.BattleField;
import ia.battle.core.ConfigurationManager;
import ia.battle.core.FieldCell;
import ia.battle.core.FieldCellType;
import manager.DataManager;

public class Astar {

	private ArrayList<Nodo> nodos; //solo va a tener los nodos donde se puede caminar
	private ArrayList<Nodo> nodosCerrados, nodosAbiertos;
	private Nodo origen, destino;
	private int mapHeight;
	private int mapWidth;
	private BattleField bf;
	
	public Astar() {
	
	}

	public ArrayList<Nodo> buscarRuta(FieldCell origen, FieldCell destino, FieldCell hunter) {
		
		nodos = new ArrayList<>();
		nodosCerrados = new ArrayList<Nodo>();
		nodosAbiertos = new ArrayList<Nodo>();
		
		mapWidth = ConfigurationManager.getInstance().getMapWidth();
		mapHeight = ConfigurationManager.getInstance().getMapHeight();
			
		bf = BattleField.getInstance();
		FieldCell cell;
		
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				
				cell = bf.getFieldCell(x, y);
				
				if(cell.getFieldCellType() == FieldCellType.NORMAL) { //pregunto si se puede caminar
					Nodo newNodo = new Nodo(cell.getX(), cell.getY());
					newNodo.setCost(cell.getCost());
					nodos.add(newNodo);
				}
			}
		}

		esquivarHunter(hunter, origen);
		
		
		this.origen = nodos.get(nodos.indexOf(new Nodo(origen.getX(), origen.getY())));
		this.destino = nodos.get(nodos.indexOf(new Nodo(destino.getX(), destino.getY())));
		
		Nodo nodoActual = this.origen;
		
		while (!nodoActual.equals(this.destino)) {
			procesarNodo(nodoActual);
			nodoActual = obtenerFminimo();
		}

		return obtenerRuta();
	}
	

	private ArrayList<Nodo> obtenerRuta() {
		ArrayList<Nodo> ruta = new ArrayList<Nodo>();
		Nodo nodo = destino;

		while (!nodo.equals(origen)) {
			ruta.add(nodo);
			nodo = nodo.getParent();
		}

		Collections.reverse(ruta); //busco la ruta al revez

		return ruta;
	}

	private void procesarNodo(Nodo nodo) {

		ArrayList<Nodo> adj = obtenerNodosAdyacentes(nodo);

		nodosAbiertos.remove(nodo);
		nodosCerrados.add(nodo);
		
	
		
		for (Nodo n : adj) {
			
			//System.out.println("costo"+n.getCost());

			if (nodosCerrados.contains(n))
				continue;

			//calcula distancia Manhattan de nodo 'n' al destino
			int h = Math.abs(destino.getX() - n.getX());
			h += Math.abs(destino.getY() - n.getY());
			
			//calcula distancia  de origen al  nodo 'n' 
			int g = nodo.getG();
			if (nodo.getX() == n.getX() || nodo.getY() == n.getY())
				g += 1*nodo.getCost(); //usemos el costo
			else
				g += 1.41*nodo.getCost(); //para redondear

			if (!nodosAbiertos.contains(n)) {

				n.setParent(nodo);
				n.setH(h);
				n.setG(g);

				nodosAbiertos.add(n);
			} else {

				if (h + g < n.getF()) {

					n.setParent(nodo);
					n.setH(h);
					n.setG(g);
				}
			}
		}
	}

	//busca el f minimo de los nodos abiertos
	private Nodo obtenerFminimo() {
		
		Nodo nodo = nodosAbiertos.get(0);


		for (Nodo n : nodosAbiertos)
			if (nodo.getF() > n.getF())
				nodo = n;

		return nodo;
	}

	
	private ArrayList<Nodo> obtenerNodosAdyacentes(Nodo nodo) {
		ArrayList<Nodo> nodosAdyacentes = new ArrayList<Nodo>();

		int x = nodo.getX();
		int y = nodo.getY();

		if (nodos.indexOf(new Nodo(x + 1, y)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x + 1, y))));

		if (nodos.indexOf(new Nodo(x, y + 1)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x, y + 1))));

		if (nodos.indexOf(new Nodo(x - 1, y)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x - 1, y))));

		if (nodos.indexOf(new Nodo(x, y - 1)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x, y - 1))));

		if (nodos.indexOf(new Nodo(x - 1, y - 1)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x - 1, y - 1))));

		if (nodos.indexOf(new Nodo(x + 1, y + 1)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x + 1, y + 1))));

		if (nodos.indexOf(new Nodo(x - 1, y + 1)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x - 1, y + 1))));

		if (nodos.indexOf(new Nodo(x + 1, y - 1)) >= 0)
			nodosAdyacentes.add(nodos.get(nodos.indexOf(new Nodo(x + 1, y - 1))));

		return nodosAdyacentes;
	}

	
	public void esquivarHunter(FieldCell hunter, FieldCell origen) {
		
		double distance = calcularDistancia(origen, hunter);
		
		int rango = DataManager.getInstance().getRangoHunter();
		Nodo nodo = null;
		FieldCell fieldcell;
		
		for (int x = 0; x < rango; x++){ 
			for (int y = 0; y < rango; y++){
				
				if(hunter.getX()+x<mapWidth && hunter.getY()+y<mapHeight){
					fieldcell = BattleField.getInstance().getFieldCell(hunter.getX()+x, hunter.getY()+y);
					if(calcularDistancia(fieldcell, hunter)<distance && fieldcell.getFieldCellType()==FieldCellType.NORMAL){
						nodo = nodos.get(nodos.indexOf(new Nodo(fieldcell.getX(), fieldcell.getY())));
						nodos.remove(nodo);
					}
				}

			}
		}
	
	}
	
	private double calcularDistancia(FieldCell origen, FieldCell destino){
		double distance = Math.pow(origen.getX() - destino.getX(), 2) + Math.pow(origen.getY() - destino.getY(), 2);
		return Math.sqrt(distance);
	}
	

}
