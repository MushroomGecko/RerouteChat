package me.mushroomgecko.reroutechat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;


public final class RerouteChat extends JavaPlugin implements Listener {

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        System.out.println("RerouteChat is running");

        // Register the events
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onKick(PlayerKickEvent kickReason)
    {
        //Microsoft likes to kick people without any signed messages
        if(kickReason.getReason().equals("Received chat packet with missing or invalid signature."))
        {
            kickReason.setCancelled(true);
        }
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
    public void onCommand(PlayerCommandPreprocessEvent playerMessage)
    {
        // Get the command the player sends
        String message = playerMessage.getMessage();
        // Split up the command
        String[] breakMessage = message.split(" ");

        // Check if it is the tell command
        if(breakMessage[0].equalsIgnoreCase("/tell") || breakMessage[0].equalsIgnoreCase("/msg") || breakMessage[0].equalsIgnoreCase("/w"))
        {
            // Cancels the player's original message being sent
            playerMessage.setCancelled(true);

            // Check if a valid message is being sent
            if(breakMessage.length > 2)
            {
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
                else if(receiver.equals("@a"))
                {
                    // Sets up the output of the message originally being sent
                    String receiverOutput = sender.getName() + " whispers to you: " + sendMessage;
                    String senderOutput = "You whisper to everyone: " + sendMessage;

                    // Reroutes the player's original message to be said by the server instead of the player
                    Bukkit.broadcastMessage(receiverOutput);
                    sender.sendMessage(senderOutput);
                }
                else
                {
                    // Send an error message if the receiver is not found
                    sender.sendMessage("Player " + receiver + " not found.");
                }
            }
            else if(breakMessage.length == 2)
            {
                // Tells player that their message to another player can't be blank
                playerMessage.getPlayer().sendMessage("Message to player can't be blank.");
            }
            else
            {
                // Tells player that they need to specify what player they want to send a message to
                playerMessage.getPlayer().sendMessage("Please specify a player you wish to send a message to.");
            }
        }
        else if(breakMessage[0].equalsIgnoreCase("/teammsg") || breakMessage[0].equalsIgnoreCase("/tm"))
        {
            // Cancels the player's original message being sent
            playerMessage.setCancelled(true);

            if(breakMessage.length > 1)
            {
                // Gets info about the sender
                Player sender = playerMessage.getPlayer();

                // Gets the team the player is in
                Team team = sender.getScoreboard().getEntryTeam(sender.getName());

                // Check to see if player is in a team
                if(team != null)
                {
                    // Send every member of the team a message
                    for (String player : team.getEntries())
                    {
                        Player receiver = getServer().getPlayerExact(player);
                        if (receiver != null)
                        {
                            // Make a substring of the message to send to the team
                            String output = "->" + "[" + team.getDisplayName() + "] " + "<" + sender.getName() + "> " + message.substring(message.indexOf(" ")+1);
                            receiver.sendMessage(output);
                        }
                    }
                }
                else
                {
                    // Tells the player that they are not in a team, so they can't send a message
                    sender.sendMessage("Can't send team message. You are not in a team.");
                }
            }
            else
            {
                // Tells the player that their message can't be blank
                playerMessage.getPlayer().sendMessage("Message to team can't be blank.");
            }
        }
        else if(breakMessage[0].equalsIgnoreCase("/say"))
        {
            // Cancels the player's original message being sent
            playerMessage.setCancelled(true);

            if(breakMessage.length > 1)
            {
                // Gets info about the sender
                Player sender = playerMessage.getPlayer();
                // Make a substring of the message to send to the server
                String output = "[" + sender.getName() + "] " + message.substring(message.indexOf(" ")+1);

                // Reroutes the player's original message to be said by the server instead of the player
                Bukkit.broadcastMessage(output);
            }
            else
            {
                // Tells the player that their message can't be blank
                playerMessage.getPlayer().sendMessage("Message can't be blank.");
            }
        }
        else if(breakMessage[0].equalsIgnoreCase("/me"))
        {
            // Cancels the player's original message being sent
            playerMessage.setCancelled(true);

            if(breakMessage.length > 1)
            {
                // Gets info about the sender
                Player sender = playerMessage.getPlayer();
                // Make a substring of the message to send to the server
                String output = "* " + sender.getName() + " " + message.substring(message.indexOf(" ")+1);

                // Reroutes the player's original message to be said by the server instead of the player
                Bukkit.broadcastMessage(output);
            }
            else
            {
                // Tells the player that their message can't be blank
                playerMessage.getPlayer().sendMessage("Message can't be blank.");
            }
        }
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
        System.out.println("RerouteChat has stopped");
    }
}
