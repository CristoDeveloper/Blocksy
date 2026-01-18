package it.blocksy.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.blocksy.Blocksy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final String apiUrl = "https://www.blocksy.it/api/plugin_check.php";
    private final Gson gson = new Gson();

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Controlla se esiste una versione più recente del plugin
     */
    public void check() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                // Recupera la versione attuale dal plugin.yml
                String currentVersion = plugin.getDescription().getVersion();
                
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("User-Agent", "Blocksy-Plugin-Updater");

                if (connection.getResponseCode() != 200) return;

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                JsonObject json = gson.fromJson(response.toString(), JsonObject.class);
                
                if (json.has("success") && json.get("success").getAsBoolean()) {
                    String latestVersion = json.get("latest_version").getAsString();

                    // Compara le versioni: se quella dell'API è diversa, c'è un update
                    if (!latestVersion.equalsIgnoreCase(currentVersion)) {
                        String jarName = json.get("jar_name").getAsString();
                        String changelog = json.has("changelog") ? json.get("changelog").getAsString() : "Miglioramenti generali";
                        String downloadUrl = json.get("download_url").getAsString();

                        plugin.getLogger().info("====================================================");
                        plugin.getLogger().info("NUOVO AGGIORNAMENTO DISPONIBILE!");
                        plugin.getLogger().info("Attuale: " + currentVersion + " | Disponibile: " + latestVersion);
                        plugin.getLogger().info("Scarica: " + downloadUrl);
                        plugin.getLogger().info("====================================================");

                        // Registra il listener per avvisare gli admin quando loggano
                        plugin.getServer().getScheduler().runTask(plugin, () -> {
                            Bukkit.getPluginManager().registerEvents(new UpdateNotifyListener(latestVersion, changelog, downloadUrl), plugin);
                        });
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().info("Impossibile contattare il server per il controllo aggiornamenti.");
            }
        });
    }

    /**
     * Listener interno per notificare i giocatori con permessi
     */
    private static class UpdateNotifyListener implements org.bukkit.event.Listener {
        private final String version;
        private final String changelog;
        private final String url;

        public UpdateNotifyListener(String version, String changelog, String url) {
            this.version = version;
            this.changelog = changelog;
            this.url = url;
        }

        @org.bukkit.event.EventHandler
        public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
            Player player = event.getPlayer();
            if (player.isOp() || player.hasPermission("blocksy.admin")) {
                // Invia il messaggio dopo 3 secondi (60 ticks)
                Bukkit.getScheduler().runTaskLater(Blocksy.getInstance(), () -> {
                    player.sendMessage("");
                    player.sendMessage("§b§lBlocksy §8» §eÈ disponibile una nuova versione! §7(§f" + version + "§7)");
                    player.sendMessage("§b§lBlocksy §8» §7Changelog: §f" + changelog);
                    player.sendMessage("§b§lBlocksy §8» §7Scarica qui: §b§n" + url);
                    player.sendMessage("");
                }, 60L);
            }
        }
    }
}

