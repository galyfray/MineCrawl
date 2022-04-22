package fr.co.events;

import fr.co.MineCrawl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakEventHandler implements Listener {

    private MineCrawl plugin;

    public BlockBreakEventHandler(MineCrawl mc){
        this.plugin = mc;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block b = e.getBlock();

        if (b.getType().equals(Material.PURPLE_WOOL) && e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND)) {
            e.setCancelled(true);
            Location loc = b.getLocation();
            this.plugin.coordinate.add(loc);
        }
    }

}
