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
import manager.Mapa;

public class WanchopeAbila extends Warrior  {
	
	private Astar aStar;
	private ArrayList<FieldCell> ruta;
	private Mapa mapa;
	private BattleField bf;
	
	public WanchopeAbila(String name, int health, int defense, int strength, int speed, int range)
			throws RuleException {
		super(name, health, defense, strength, speed, range);

		this.aStar = new Astar();
		this.ruta = new ArrayList<>();	
		
		mapa = Mapa.getInstance();
		bf = BattleField.getInstance();	

	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		

		Action action = null;
		
			//enemigo
			WarriorData enemyData = bf.getEnemyData();
			FieldCell enemyPosition = enemyData.getFieldCell();
			
			//hunter   
			WarriorData hunterData = bf.getHunterData();
			FieldCell hunterPosition = hunterData.getFieldCell(); 
			
			// mi posicion
			FieldCell myPosition = this.getPosition();		
			

			
			//ataco si esta dentro del rango
			if(enemyData.getInRange()) {
				action = new Attack(enemyData.getFieldCell());
			}

						
					
			
			//si veo algun paquete voy por el
			if(bf.getSpecialItems().size()>0){
				//for (FieldCell fieldCell : bf.getSpecialItems())
					//mapa.addCeldaPaquete(fieldCell);
				
				action = new BuscarCaja(myPosition);

			}

		
	
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
