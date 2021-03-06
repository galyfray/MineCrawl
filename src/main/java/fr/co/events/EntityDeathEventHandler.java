package fr.co.events;

import fr.co.economy.Balance;
import fr.co.economy.KillMap;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathEventHandler implements Listener {

    /**
     * Event handler for entity death event. This handler aims to pay the player when he kills an entity.
     * @param event EntityDeathEvent
     */
    @EventHandler(ignoreCancelled = true)
    public void onKillByPlayer(EntityDeathEvent event) {
        // TODO: improve the feedback to the player
        if (event.getEntity().getKiller() != null) {
            double payment = KillMap.getInstance().getPayment(event.getEntityType());
            event.getEntity().getKiller().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("" + payment));
            Balance.getInstance().credit(event.getEntity().getKiller(), payment);
        }
    }
}
