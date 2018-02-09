package autofighter.src.nodes;

import java.util.List;

import autofighter.src.core.AutoFighter;
import dependencies.net.novakscripts.framework.Priority;
import dependencies.net.novakscripts.framework.regular.Node;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;


public class FightNode extends Node {

	private AutoFighter script;
	private List<String> lootList;
	private Filter<NPC> npcFilter = new Filter<NPC>() {

		@Override
		public boolean match(NPC npc) {
			if(npc.getName().equals(script.npcName) && !npc.isUnderAttack() && npc.isAttackable() && script.fightArea.contains(npc) && npc.getHealth() > 0)
				return true;
			return false;
		}
		
	};
	
	public FightNode(AutoFighter script, List<String> lootList) {
		this.script = script;
		this.lootList = lootList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean validate() {
		NPC npc = script.npcs.closest(npcFilter);
		return npc != null && !inCombat() && !lootExists() && !script.myPlayer().isMoving() && !script.inventory.isFull();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws InterruptedException {
		final NPC npc = script.npcs.closest(npcFilter);
		if(script.map.canReach(npc) && npc.interact("Attack"))
			MethodProvider.sleep(MethodProvider.random(1000, 3000));
	}

	@Override
	public Priority priority() {
		return Priority.HIGH;
	}

	@Override
	public String getName() {
		return "Fighting...";
	}
	
	private boolean inCombat() {
		return script.myPlayer().isUnderAttack() || script.myPlayer().getInteracting() != null;
	}
	
	private boolean lootExists() {
		for(String s : lootList) {
			GroundItem loot = script.groundItems.closest(s);
			if(loot != null && script.map.canReach(loot))
				return true;
		}
		return false;
	}

}
