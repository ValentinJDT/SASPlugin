package fr.mrcoq.sASPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.logging.Level;

public final class SASPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if(isShutdown("https://reapers-minecraft.weebly.com/")) {
                Bukkit.getLogger().info("Server is shutting down...");
                Bukkit.shutdown();
            }
        });
    }

    public boolean isShutdown(String link) {
        try {
            Document doc = Jsoup.connect(link).get();
            for(Element element : doc.getElementsByClass("wsite-content-title")) {
                String content = element.html();
                Bukkit.getLogger().info("Content: " + content);
                if(content.contains("shutdown=")) {
                    String shutdownValue = content.split("shutdown=")[1].split("\"")[0];
                    return shutdownValue.equalsIgnoreCase("true");
                }
            }

        } catch(IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while checking shutdown status", e);
        }
        return false;
    }
}
