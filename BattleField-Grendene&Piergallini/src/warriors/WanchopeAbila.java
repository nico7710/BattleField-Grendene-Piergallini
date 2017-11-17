package warriors;
import java.util.ArrayList;
import Astar.Nodo;
import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorData;
import ia.battle.core.actions.Action;
import ia.battle.core.actions.Attack;
import ia.battle.core.actions.Skip;
import ia.exceptions.RuleException;
import manager.DataManager;

public class WanchopeAbila extends Warrior  {
	
	private BattleField bf;
	
	public WanchopeAbila(String name, int health, int defense, int strength, int speed, int range)
			throws RuleException {
		super(name, health, defense, strength, speed, range);
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
	
		bf = BattleField.getInstance();	
		
		// mi posicion
		FieldCell myPosition = this.getPosition();	
		
		System.out.println("Paquetes que vi: "+DataManager.getInstance().getSpecialItems().size());
		
		if(bf.getSpecialItems().size()>0)
		for (FieldCell specialItem : bf.getSpecialItems()) {
			DataManager.getInstance().addSpecialItems(specialItem);
		}
		
		DataManager.getInstance().deleteSpecialItems(myPosition);


		
		//me actualizo los datos de la partida

		//por defecto
		Action action = new Skip();
		
	
		
		//ataco si esta dentro del rango
		WarriorData enemyData = bf.getEnemyData();
		

		
		if(enemyData.getInRange()) {
			action = new Attack(enemyData.getFieldCell());
			System.out.println("Veo a mi enemigo, ataco!\n");
		}else{
			if(DataManager.getInstance().getSpecialItems().size()>0){
				System.out.println("Veo una caja y voy por ella\n");
				action = new BuscarCaja(myPosition);
			}else{
				System.out.println("No veo cajas, deambulo un rato\n");						
				action = new Deambular(myPosition);
			}			
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
