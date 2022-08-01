package com.carsongames.cgfeatherdetecter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public final class CGFeatherDetecter extends JavaPlugin {
//Yes I know its misspelled

    public String group = "group.feather";
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "minecraft:brand", new BrandPluginMessageListener());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private static void addChannel(Player p, String channel) {
        try {
            p.getClass().getMethod("addChannel", String.class).invoke(p, channel);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                 | SecurityException e) {
            e.printStackTrace();
        }
    }



    public class BrandPluginMessageListener implements PluginMessageListener {
        @Override
        public void onPluginMessageReceived(String channel, Player p, byte[] msg) {

            try {
                String client =   new String(msg, "UTF-8").substring(1);
    
                if(client.toString().contains("Feather")){
if(!p.hasPermission(group))
AddRank(p.getName());
p.sendMessage(ChatColor.GREEN + "You have received the Feather Rank! This rank is rewarded for players who play the server on the Feather Client. " + ChatColor.WHITE);
                }
                else{
                    if(p.hasPermission(group)){
                        p.sendMessage(ChatColor.RED + "Your Feather Rank has been removed This rank is rewarded for players who play the server on the Feather Client. Rejoin using the Feather Client to get this rank back." + ChatColor.WHITE);

                        RemoveRank(p.getName());

                    }
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }}
            public void AddRank(String player){

             Bukkit.dispatchCommand(getServer().getConsoleSender(),"lp user " + player + " permission set "+group+" true")  ;
            }
            public void RemoveRank(String player){
                Bukkit.dispatchCommand(getServer().getConsoleSender(),"lp user " + player + " permission set "+group+" false")  ;

            }

        }


        public class PlayerJoin implements Listener {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {
                addChannel(event.getPlayer(),"minecraft:brand");
            }
        }
        }


