package com.fx.srp.listeners;

import com.fx.srp.SpeedRunPlus;
import com.fx.srp.commands.Commands;
import com.fx.srp.commands.Subcommands;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.logging.Logger;

@NoArgsConstructor
public class CommandListener implements Listener {

    private final Logger logger = Bukkit.getLogger();

    SpeedRunPlus plugin = SpeedRunPlus.getPlugin(SpeedRunPlus.class);

    // Prevent commands during a run
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        // Block commands if player is frozen (i.e. during countdown)
        if (plugin.getFrozenPlayers().contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You cannot use commands during the countdown!");
            return;
        }

        // Allow all commands if OP
        if (player.isOp()) return;

        // Only restrict if player is in a run world
        if (player.getWorld().getName().contains(player.getUniqueId().toString())) {
            String message = event.getMessage().toLowerCase();

            // Allow only "reset" and "stop"
            boolean isReset = Commands.SRP.with(Subcommands.RESET).equals(message);
            boolean isStop = Commands.SRP.with(Subcommands.STOP).equals(message);
            if (isReset || isStop) return;

            player.sendMessage(ChatColor.RED + "You cannot use commands during a run! Use "
                    + ChatColor.GRAY + "/srp stop "
                    + ChatColor.RED + "to quit."
            );
            event.setCancelled(true);
        }
    }
}

