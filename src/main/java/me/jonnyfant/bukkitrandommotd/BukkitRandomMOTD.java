package me.jonnyfant.bukkitrandommotd;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public final class BukkitRandomMOTD extends JavaPlugin {
    public final String FILE_NAME = "MOTD_List.yml";
    private final String CFG_KEY_ENABLE = "enable";
    private final boolean CFG_DEFAULT_ENABLE = true;
    public final String CFG_KEY_LIST = "MOTDList";
    private final ArrayList<String> EXAMPLE_MOTD_LIST = new ArrayList<>(
            Arrays.asList("§9MINECRAFT\n§6pᴉuuǝɹqouǝ",
                    "A Minecraft Server",
                    "§9Hypixel\n§6Han shot first!",
                    "2b2t\n§1C§2o§3l§4o§5r§6m§7a§8t§9i§ac")
    );

    @Override
    public void onEnable() {
        initConfig();
        if (getConfig().getBoolean(CFG_KEY_ENABLE))
            getServer().getPluginManager().registerEvents(new ServerPingListener(this), this);
    }

    public void initConfig() {
        getConfig().addDefault(CFG_KEY_ENABLE, CFG_DEFAULT_ENABLE);
        getConfig().options().copyDefaults(true);
        saveConfig();
        File f = new File(getDataFolder(), FILE_NAME);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        yaml.addDefault(CFG_KEY_LIST, EXAMPLE_MOTD_LIST);
        yaml.options().copyDefaults(true);
        try {
            yaml.save(f);
        } catch (IOException e) {
            Bukkit.broadcastMessage("RANDOM MOTD Error: Could not create example list");
        }
    }
}
