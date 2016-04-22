package me.binarybench.invisplayerfix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bench on 4/21/2016.
 */
public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this, () -> {

            Set<Player> players = new HashSet<>();

            for (Player otherPlayer : Bukkit.getOnlinePlayers())
            {
                if (!otherPlayer.canSee(player))
                    continue;
                players.add(otherPlayer);
                otherPlayer.hidePlayer(player);
            }

            Bukkit.getScheduler().runTaskLater(this, () -> {
                for (Player otherPlayer: players)
                {
                    if (!player.isValid() || !player.isOnline())
                        return;
                    if (!otherPlayer.isValid() || !otherPlayer.isOnline())
                        continue;
                    otherPlayer.showPlayer(player);
                }
            }, 1);

        }, 2);
    }
}
