package manager;
import Factory.WarriorFactory;
import ia.battle.core.ConfigurationManager;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorManager;
import ia.exceptions.RuleException;

public class ManagerExample extends WarriorManager {
	
	private static String name = "Piergallini&Grendene";
	//int health; defense, strength, speed, range = 10;
	
	int maxPointsPerWarrior, maxRangeForWarrior, maxWarriorPerBattle;
	
	public ManagerExample() {

		maxPointsPerWarrior = ConfigurationManager.getInstance().getMaxPointsPerWarrior();
		System.out.println("Max points per warrior "+ maxPointsPerWarrior);
		
		maxRangeForWarrior = ConfigurationManager.getInstance().getMaxRangeForWarrior();	
		System.out.println("Max range for warrior "+ maxRangeForWarrior);

		maxWarriorPerBattle = ConfigurationManager.getInstance().getMaxWarriorPerBattle();
		System.out.println("Max Warrior per battle "+ maxWarriorPerBattle);

		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Warrior getNextWarrior() throws RuleException {
				
		return WarriorFactory.crearWanchopeAbila(20, 20, 20, 20, 20);
		
	}
	
}
