package me.ohvalsgod.ehub;

import lombok.Getter;
import me.ohvalsgod.bklib.BukkitLib;
import me.ohvalsgod.bklib.board.Assemble;
import me.ohvalsgod.ehub.board.HubAssembleAdapter;
import me.ohvalsgod.ehub.interactable.InteractableItem;
import me.ohvalsgod.ehub.inventory.HubInventory;
import me.ohvalsgod.ehub.listener.BungeeHandler;
import me.ohvalsgod.ehub.listener.HubHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HubPlugin extends JavaPlugin {

    @Getter private static HubPlugin instance;

    @Getter private Assemble assemble;
    @Getter private HubInventory hubInventory;

    @Override
    public void onEnable() {
        instance = this;

        new InteractableItem(instance);
        hubInventory = new HubInventory(instance);

        new HubHandler(instance);

        assemble = new Assemble(instance, new HubAssembleAdapter());

        BukkitLib.getLibrary().setAssemble(assemble);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeHandler());

        Bukkit.getOnlinePlayers().forEach(o -> hubInventory.update(o));
    }

    @Override
    public void onDisable() {

    }
}