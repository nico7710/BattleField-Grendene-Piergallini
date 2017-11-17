package warriors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Astar.Astar;
import Astar.Nodo;
import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.WarriorData;
import manager.DataManager;

public class BuscarCaja extends ia.battle.core.actions.Move {
	
	private ArrayList<FieldCell> ruta;
	private BattleField bf;
	private List<FieldCell> cajas; 
	private Astar aStar;
	private FieldCell myPosition, hunterPosition; 

	public BuscarCaja(FieldCell myPosition){
		
		aStar = new Astar();

		bf = BattleField.getInstance();	
		this.myPosition = myPosition;
	
		this.cajas = DataManager.getInstance().getSpecialItems();
			
		WarriorData hunterData = bf.getHunterData();
		hunterPosition = hunterData.getFieldCell(); 
		
		FieldCell cajamasCerca = buscarElMasCerca();

		ruta = transformToCell(aStar.buscarRuta(myPosition, cajamasCerca, hunterPosition));

		
	}	
	
	
	public FieldCell buscarElMasCerca() {
		

		ArrayList<Nodo> camino = aStar.buscarRuta(myPosition, cajas.get(0), hunterPosition);
		Nodo ultimoNodo = camino.get(camino.size()-1);
		int distancia = ultimoNodo.getG();
		FieldCell masCerca = bf.getFieldCell(ultimoNodo.getX(), ultimoNodo.getY());
		
		for (int i = 1; i < cajas.size(); i++) {
			camino = aStar.buscarRuta(myPosition, cajas.get(i), hunterPosition);
			ultimoNodo = camino.get(camino.size()-1);
			if(ultimoNodo.getG()<distancia)
				masCerca = bf.getFieldCell(ultimoNodo.getX(), ultimoNodo.getY());
		}

		
		
		return masCerca;
	}
	
	@Override
	public ArrayList<FieldCell> move() {
		return ruta;
	}
	
	public static ArrayList<FieldCell> transformToCell(ArrayList<Nodo> source){
		ArrayList<FieldCell> result = new ArrayList<>();
		BattleField bf = BattleField.getInstance();
		
		for(Nodo n:source){
			result.add(bf.getFieldCell(n.getX(), n.getY()));
		}
		
		return result;
	}
	

}
