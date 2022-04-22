package fr.co.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagedEventHandlers implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerKillEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity().isDead()) {
                event.getDamager().sendMessage("You killed " + event.getEntity().getName());
            }
        }
    }

}
