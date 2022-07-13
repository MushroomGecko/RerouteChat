package me.mushroomgecko.reroutechat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
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
        String output = "<" + player.getName() + "> " + message;

        // Reroutes the player's original message to be said by the server instead of the player
        Bukkit.broadcastMessage(output);
    }

    @EventHandler
    public void onTell(PlayerCommandPreprocessEvent playerMessage)
    {
        // Get the command the player sends
        String message = playerMessage.getMessage();
        // Split up the command
        String[] breakMessage = message.split(" ");

        // Check if a valid message is being sent
        if(breakMessage.length > 2)
        {
            // Check if it is the tell command
            if(breakMessage[0].equalsIgnoreCase("/tell") || breakMessage[0].equalsIgnoreCase("/msg") || breakMessage[0].equalsIgnoreCase("/w"))
            {
                // Cancels the player's original message being sent
                playerMessage.setCancelled(true);

                // Gets info about the sender
                Player sender = playerMessage.getPlayer();

                // Gets info about the receiver and gets the Player object of the receiver
                String receiver = breakMessage[1];
                Player recipient = getServer().getPlayerExact(receiver);

                // Make a substring of the message to send to the receiver
                String sendMessage = message.substring(message.indexOf(" ")+1 + message.substring(message.indexOf(" ")+1).indexOf(" ")+1);

                // Check to see if the recipient exists
                if(recipient != null)
                {
                    // Sets up the output of the message originally being sent
                    String receiverOutput = sender.getName() + " whispers to you: " + sendMessage;
                    String senderOutput = "You whisper to " + receiver + ": " + sendMessage;

                    // Reroutes the player's original message to be said by the server instead of the player
                    recipient.sendMessage(receiverOutput);
                    sender.sendMessage(senderOutput);
                }
                else
                {
                    // Send an error message if the receiver is not found
                    sender.sendMessage("Player " + receiver + " not found.");
                }
            }
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RerouteChat has stopped");
    }
}
