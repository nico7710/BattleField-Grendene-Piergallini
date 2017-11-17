package warriors;

import java.util.ArrayList;
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
			
		int distancia = aStar.buscarDistancia(myPosition, cajas.get(0), hunterPosition);
		FieldCell masCerca = bf.getFieldCell(cajas.get(0).getX(), cajas.get(0).getY());
		
		for (int i = 1; i < cajas.size(); i++) {
			int distTemp = aStar.buscarDistancia(myPosition, cajas.get(i), hunterPosition);
			if(distTemp<distancia)
				masCerca = bf.getFieldCell(cajas.get(i).getX(), cajas.get(i).getY());
				distancia=distTemp;
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
