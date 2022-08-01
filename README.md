# RerouteChat
This is a Spigot based plugin for Minecraft 1.19.1 and above. The purpose of this plugin is to reroute a player's chat message to make the server say it so that the message can not be highlighted on and reported to Microsoft. As of current testing on Minecraft 1.19.1 using Spigot API 1.19.1-R0.1-SNAPSHOT. Server messages/announcements are not reportable which gave me the idea for this plugin.

To install and use this plugin, simply download the jar file given and put it into your server's plugins folder. Upon starting your server, you should get a confirmation notification in the console saying "RerouteChat is running". You know this plugin is working properly if player names no longer display their unique IDs when you hover over them.

All testing with this plugin was done on Minecraft 1.19 as of the time of writng this README.
If you encounter any errors or have any suggestions on how to improve this project, please feel free to leave an issue!

Current Features
> Standard server chat is rerouted as a server announcement

> /tell, /msg, and /w messages are rerouted as server announcements. Direct player messaging and @a are supported.

> /teammsg and /tm messages are rerouted as server announcements

> /say command rerouting

> /me command rerouting

In Progress
> @r support for /tell, /msg, and /w messages

> @p support for /tell, /msg, and /w messages

Known Bugs
> After sending a regular chat message than executing a command then attempting to send a regular chat again will kick you for "Chat message valdation failure".
