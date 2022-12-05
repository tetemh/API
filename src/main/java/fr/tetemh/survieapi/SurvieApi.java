package fr.tetemh.survieapi;

import fr.tetemh.survieapi.database.Loader;
import fr.tetemh.survieapi.player.CPEvent;
import fr.tetemh.survieapi.player.CPManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class SurvieApi extends JavaPlugin {

    private static SurvieApi instance;
    private static Loader storage;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        saveDefaultConfig();

        //TODO Hikari
        try {
            storage = new Loader(this.getConfig());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //TODO Manager
        CPManager cpManager = new CPManager();
        cpManager.init();

        //TODO Event
        getServer().getPluginManager().registerEvents(new CPEvent(), this);

    }

    @Override
    public void onDisable() {

        CPManager cpManager = CPManager.getInstance();
        cpManager.save();

        // Plugin shutdown logic
        storage.getDataSource().close();
    }

    public static SurvieApi getInstance() {
        return instance;
    }
    public static Loader getLoader(){
        return storage;
    }
}
