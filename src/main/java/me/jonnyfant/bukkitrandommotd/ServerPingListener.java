package me.jonnyfant.bukkitrandommotd;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;
import java.util.List;
import java.util.Random;

public class ServerPingListener implements Listener {
    private BukkitRandomMOTD plugin;

    public ServerPingListener(BukkitRandomMOTD plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onListPing(ServerListPingEvent event) {
        event.setMotd(getRandomMOTDFromFile());
    }

    public String getRandomMOTDFromFile() {
        Random r = new Random();
        File f = new File(plugin.getDataFolder(), plugin.FILE_NAME);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        List<?> motds = yaml.getList(plugin.CFG_KEY_LIST);
        return (String) motds.get(r.nextInt(motds.size()));
    }
}
