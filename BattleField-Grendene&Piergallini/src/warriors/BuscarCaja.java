package warriors;

import java.util.ArrayList;
import java.util.List;
import Astar.Astar;
import Astar.Nodo;
import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.WarriorData;

public class BuscarCaja extends ia.battle.core.actions.Move {
	
	private ArrayList<FieldCell> ruta;
	private BattleField bf;
	private List<FieldCell> cajas; 
	private Astar aStar;
	private FieldCell myPosition, hunterPosition; 

	public BuscarCaja(FieldCell myPosition){
	
		bf = BattleField.getInstance();	
		this.myPosition = myPosition;
	
		aStar = new Astar();
		this.cajas = bf.getSpecialItems();	
	
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
		
		for (FieldCell caja : cajas) {
			
			camino = aStar.buscarRuta(myPosition, caja, hunterPosition);
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
