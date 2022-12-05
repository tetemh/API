package fr.tetemh.survieapi.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CPEvent implements Listener {

    private CPManager cpManager = CPManager.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(cpManager.getPlayers().get(player.getUniqueId()) != null){
            cpManager.addCPlayer(new CPlayer(player.getUniqueId(), player.getName()));
        }
    }
}
