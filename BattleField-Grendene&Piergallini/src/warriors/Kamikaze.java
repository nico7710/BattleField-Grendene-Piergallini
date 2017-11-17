package warriors;
import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorData;
import ia.battle.core.actions.Action;
import ia.battle.core.actions.Skip;
import ia.battle.core.actions.Suicide;
import ia.exceptions.RuleException;


public class Kamikaze extends Warrior  {
	
	private BattleField bf;

	public Kamikaze(String name, int health, int defense, int strength, int speed, int range)
			throws RuleException {
		super(name, health, defense, strength, speed, range);
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
	
		this.
		
		bf = BattleField.getInstance();	
		WarriorData enemyData = bf.getEnemyData();
		Action action = new Skip();

		try{
			if(enemyData.getInRange()) {
				action = new Suicide();
			}else{
				action = new BuscarEnemigo(this.getPosition());
			}
		}catch(Exception e){
			System.out.println(e.toString());
			action = new Skip();
		}
		
		return action;

	}

	
	@Override
	public void wasAttacked(int damage, FieldCell source) {
	}

	@Override
	public void enemyKilled() {
		
	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		
	}

}
