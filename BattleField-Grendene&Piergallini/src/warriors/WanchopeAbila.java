package warriors;
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
		
		FieldCell myPosition = this.getPosition();	
				
		//dejo registro de todas las cajas que veo
		DataManager.getInstance().addSpecialItems(bf.getSpecialItems());
				
		Action action = new Skip();
		WarriorData enemyData = bf.getEnemyData();

		try{
			if(enemyData.getInRange()) {
				System.out.println("Veo a mi enemigo, ataco!\n");
				action = new Attack(enemyData.getFieldCell());
			}else{
				if(DataManager.getInstance().getSpecialItems().size()>0){
					System.out.println("busco caja\n");						
					action = new BuscarCaja(myPosition);
				}else{
					System.out.println("no tengo cajas, deambulo\n");						
					action = new Deambular(myPosition);
				}			
			}
		}catch(Exception e){
			try {
				System.out.println("deambulo porque las cajas estan cerca del hunter\n");						
				action = new Deambular(myPosition);
			} catch (Exception e2) {
				action = new Skip();
			}
		}
	
		DataManager.getInstance().deleteSpecialItems(myPosition);
		return action;

	}

	
	@Override
	public void wasAttacked(int damage, FieldCell source) {
		// System.out.println("fui atacado");		
	}

	@Override
	public void enemyKilled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		// TODO Auto-generated method stub
		
	}
	

}
