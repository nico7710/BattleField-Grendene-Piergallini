package warriors;

import java.util.ArrayList;
import Astar.Astar;
import Astar.Nodo;
import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.actions.Move;

public class BuscarEnemigo extends Move {

	private Astar aStar;
	private ArrayList<FieldCell> ruta;
	
	
	public BuscarEnemigo(FieldCell myPosition, FieldCell hunter) {

		FieldCell enemyCell = BattleField.getInstance().getEnemyData().getFieldCell();
		
		aStar = new Astar();
		ruta = transformToCell(aStar.buscarRuta(myPosition, enemyCell, hunter));
		
		
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
