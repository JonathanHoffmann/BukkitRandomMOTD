package me.JonathanHoffmann.randommotd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RandomMOTD extends JavaPlugin {
    public final String FILE_NAME = "MOTD_List.yml";
    private final String CFG_KEY_ENABLE = "enable";
    private final boolean CFG_DEFAULT_ENABLE = true;
    public final String CFG_KEY_LIST = "MOTDList";
    private final ArrayList<String> EXAMPLE_MOTD_LIST = new ArrayList<>(
            Arrays.asList("§9MINECRAFT\n§6pᴉuuǝɹqouǝ",
                    "A Minecraft Server",
                    "§9Hypixel\n§6Han shot first!",
                    "2b2t\n§1C§2o§3l§4o§5r§6m§7a§8t§9i§ac"));

    @Override
    public void onEnable() {
        initConfig();
        if (getConfig().getBoolean(CFG_KEY_ENABLE))
            getServer().getPluginManager().registerEvents(new ServerPingListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();
        switch (commandName.toLowerCase()) {
            case "motdlist":
                return motdList(sender);
            case "motdadd":
                return motdAdd(sender, args);
            case "motddelete":
                return motdDelete(sender, args);
            default:
                return false;
        }
    }

    public boolean motdList(CommandSender sender) {
        for (String s : getConfigList()) {
            sender.sendMessage(s);
        }
        return true;
    }

    public boolean motdAdd(CommandSender sender, String[] args) {
        List<String> motds = getConfigList();
        String newMOTD = "";
        for (String s : args)
            newMOTD = s + " ";
        motds.add(newMOTD);
        return writeConfigList(motds);
    }

    public boolean motdDelete(CommandSender sender, String[] args) {
        List<String> motds = getConfigList();
        String newMOTD = "";
        for (String s : args)
            newMOTD = s + " ";
        motds.remove(newMOTD);
        return writeConfigList(motds);
    }

    public List<String> getConfigList() {
        File f = new File(getDataFolder(), FILE_NAME);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        List<?> motds = yaml.getList(CFG_KEY_LIST);
        return (List<String>) motds;
    }

    public boolean writeConfigList(List<String> l) {
        File f = new File(getDataFolder(), FILE_NAME);
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        yaml.addDefault(CFG_KEY_LIST, l);
        yaml.options().copyDefaults(true);
        try {
            yaml.save(f);
            return true;
        } catch (IOException e) {
            Bukkit.broadcastMessage("RANDOM MOTD Error: Could not create example list");
            return false;
        }
    }

    public void initConfig() {
        getConfig().addDefault(CFG_KEY_ENABLE, CFG_DEFAULT_ENABLE);
        getConfig().options().copyDefaults(true);
        saveConfig();
        writeConfigList(EXAMPLE_MOTD_LIST);
    }
}
