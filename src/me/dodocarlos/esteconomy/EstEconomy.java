package me.dodocarlos.esteconomy;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class EstEconomy extends JavaPlugin implements Listener{
	
	public static DB db;
	
	@Override
	public void onEnable() {
		Vars.main = this;
		Vars.setup();
		db = new DB();
		
		//Hook into Vault
	    if ((getServer().getPluginManager().getPlugin("Vault") != null) &&
	            (getServer().getPluginManager().getPlugin("Vault").isEnabled())) {
	        try {
	            Economy econ = (Economy)EstEconomyAPI.class.getConstructor(new Class[] {
	                    Plugin.class }).newInstance(new Object[] { this });
	            getServer().getServicesManager().register(Economy.class, econ, this, ServicePriority.Highest);
	        }
	        catch (Exception localException) {
	        }
	    }
	    
	    getServer().getPluginManager().registerEvents(this, this);    
	}
	
}
