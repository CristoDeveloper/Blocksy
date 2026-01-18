package it.blocksy.config;

import it.blocksy.Blocksy;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    
    private final Blocksy plugin;
    private String apiKey;
    private int checkInterval;
    
    public ConfigManager(Blocksy plugin) {
        this.plugin = plugin;
    }
    
    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        
        this.apiKey = config.getString("api-key", "");
        this.checkInterval = config.getInt("check-interval", 5);
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public int getCheckInterval() {
        return checkInterval;
    }
    
    /**
     * Ricarica la configurazione dal file config.yml
     */
    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
    }
}