package warriors;
import java.util.ArrayList;
import java.util.List;
import Astar.Astar;
import Astar.Nodo;
import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorData;
import ia.battle.core.actions.Action;
import ia.battle.core.actions.Attack;
import ia.battle.core.actions.Move;
import ia.battle.core.actions.Skip;
import ia.exceptions.RuleException;

public class WanchopeAbila extends Warrior  {
	
	private Astar aStar;
	private ArrayList<FieldCell> ruta;

	public WanchopeAbila(String name, int health, int defense, int strength, int speed, int range)
			throws RuleException {
		super(name, health, defense, strength, speed, range);

		this.aStar = new Astar();
		this.ruta = new ArrayList<>();		
		
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {

		//Accion por default saltear
		Action action = new Skip();
		
		BattleField bf = BattleField.getInstance();	
		
		//enemigo
		WarriorData enemyData = bf.getEnemyData();
		FieldCell enemyPosition = enemyData.getFieldCell();
		
		//hunter   
		WarriorData hunterData = bf.getHunterData();
		FieldCell hunterPosition = hunterData.getFieldCell(); //por ahora me chupa un huevo el hunter	
		
		
		// mi posicion
		FieldCell myPosition = this.getPosition();		
		

		
		//ataco si esta dentro del rango
		if(enemyData.getInRange()) {
			action = new Attack(enemyData.getFieldCell());
			return action;
		}
		
		
		//obtengo paquetes
		List<FieldCell> boxes = bf.getSpecialItems();
		List<FieldCell> myPrize = new ArrayList<>();
		
		
			for(FieldCell box:boxes){
				int distanceToBox = (int) Math.ceil(bf.calculateDistance(myPosition, box));
				if(distanceToBox < getRange()){
					myPrize.add(box);
				}
			}
			
			ruta.clear();
				
			//voy por el paquete si estoy cerca y sino voy por el enemigo
			if(myPrize.size() > 0) {
				ArrayList<FieldCell> partials = new ArrayList<>();
				FieldCell visitedCell = null;
				for(FieldCell prize:myPrize){
					if(visitedCell == null){
						ruta = transformToCell(aStar.buscarRuta(myPosition, prize));
					}else{
						ruta = transformToCell(aStar.buscarRuta(visitedCell, prize));
					}
					
					visitedCell = prize;
					partials.addAll(ruta);
				}
				
				ruta = partials;
				
			}else{
				// no hay paquete por lo tanto voy por el enemigo
				
				ruta = transformToCell(aStar.buscarRuta(myPosition, enemyPosition));

			}
				
		
		//moverse ya sea a un enemigo o a un paquete
		action = new Move() {
			
			@Override
			public ArrayList<FieldCell> move() {
				return ruta;
			}
		};
		
		return action;
	}

	
	@Override
	public void wasAttacked(int damage, FieldCell source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyKilled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		// TODO Auto-generated method stub
		
	}
	
	
	//metodo que transformo un array de nodos a un array de celdas
	public static ArrayList<FieldCell> transformToCell(ArrayList<Nodo> source){
		ArrayList<FieldCell> result = new ArrayList<>();
		BattleField bf = BattleField.getInstance();
		
		for(Nodo n:source){
			result.add(bf.getFieldCell(n.getX(), n.getY()));
		}
		
		return result;
	}

}
