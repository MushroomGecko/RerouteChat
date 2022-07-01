package me.mushroomgecko.reroutechat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RerouteChat extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("RerouteChat is running");

        // Register the events
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSpeaks(AsyncPlayerChatEvent playerMessage)
    {
        // Cancels the player's original message being sent
        playerMessage.setCancelled(true);

        // Gets info about the player and message
        Player player = playerMessage.getPlayer();
        String message = playerMessage.getMessage();

        // Sets up the output of the message originally being sent
        String output = "[" + player.getName() + "]: " + message;

        // Reroutes the player's original message to be said by the server instead of the player
        Bukkit.broadcastMessage(output);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RerouteChat has stopped");
    }
}
