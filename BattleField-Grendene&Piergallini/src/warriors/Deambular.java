package warriors;

import java.util.ArrayList;
import java.util.Random;

import Astar.Astar;
import Astar.Nodo;
import ia.battle.core.BattleField;
import ia.battle.core.ConfigurationManager;
import ia.battle.core.FieldCell;
import ia.battle.core.FieldCellType;
import ia.battle.core.WarriorData;
import ia.battle.core.actions.Move;

public class Deambular extends Move {

	int mapWidth;
	int mapHeight;
	private Astar aStar;
	private ArrayList<FieldCell> ruta;
	private FieldCell hunterPosition;
	private BattleField bf;

	public Deambular(FieldCell myPosition) {
		mapWidth = ConfigurationManager.getInstance().getMapWidth();
		mapHeight = ConfigurationManager.getInstance().getMapHeight();
		
		bf = BattleField.getInstance();
		WarriorData hunterData = bf.getHunterData();
		hunterPosition = hunterData.getFieldCell();
		
		FieldCell randomCell;
		
		do{
			Random random = new Random();
			randomCell = bf.getFieldCell(random.nextInt(mapWidth), random.nextInt(mapHeight));
		}while(randomCell.getFieldCellType()==FieldCellType.BLOCKED);
		
	
		aStar = new Astar();
		ruta = transformToCell(aStar.buscarRuta(myPosition, randomCell, hunterPosition));
		
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
