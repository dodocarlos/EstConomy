package me.dodocarlos.esteconomy;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EstEconomyAPI implements Economy{
	
	private static final Logger log = Logger.getLogger("Minecraft");
    private final String name = "EstEconomy";
    private Plugin plugin = null;
    private EstEconomy economy = null;
   
    public EstEconomyAPI(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(this), plugin);
       
        //Load Plugin in case it was loaded before
        if (economy == null) {
            Plugin pe = plugin.getServer().getPluginManager().getPlugin("EstEconomy");
            if (pe != null && pe.isEnabled() &&
            		pe.getClass().getName().equals("me.dodocarlos.esteconomy.EstEconomy")) {
                economy = (EstEconomy)pe;
                log.info(String.format("[%s][Economy] %s hooked.", plugin.getDescription().getName(), name));
            }
        }
    }
    public class EconomyServerListener implements Listener {
    	EstEconomyAPI economy = null;
               
        public EconomyServerListener(EstEconomyAPI economy) {
                this.economy = economy;
        }
       
        @EventHandler(priority=EventPriority.MONITOR)
        public void onPluginEnable(PluginEnableEvent event) {
            if (economy.economy == null) {
                Plugin pe = event.getPlugin();
 
                if (pe.getDescription().getName().equals("EstEconomy") &&
                        pe.getClass().getName().equals("me.dodocarlos.esteconomy.EstEconomy")) {
                    economy.economy = (EstEconomy) pe;
                    log.info(String.format("[%s][Economy] %s hooked.", plugin.getDescription().getName(), economy.name));
                }
            }
        }
   
        @EventHandler(priority=EventPriority.MONITOR)
        public void onPluginDisable(PluginDisableEvent event) {
           if (economy.economy != null) {
                if (event.getPlugin().getDescription().getName().equals("EstEconomy")) {
                    economy.economy = null;
                    log.info(String.format("[%s][Economy] %s unhooked.", plugin.getDescription().getName(),
                            economy.name));
                }
            }
        }
    }
       
    @Override
    public boolean isEnabled() {
        if (this.economy == null) {
            return false;
        }
        return true;
    }
       
    @Override
    public String getName() {
        return "EstEconomy";
    }
       
    @Override
    public EconomyResponse bankBalance(String arg0) {
        throw new UnsupportedOperationException();
    }

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse createBank(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPlayerAccount(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String currencyNamePlural() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String currencyNameSingular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String arg0, double arg1) {
		EstEconomy.db.addMoney(Bukkit.getPlayer(arg0), arg1);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer arg0, double arg1) {
		EstEconomy.db.addMoney(arg0.getPlayer(), arg1);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String format(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fractionalDigits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBalance(String arg0) {		
		return EstEconomy.db.getMoney(Bukkit.getPlayer(arg0));
	}

	@Override
	public double getBalance(OfflinePlayer arg0) {
		return EstEconomy.db.getMoney(arg0.getPlayer());
	}

	@Override
	public double getBalance(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBalance(OfflinePlayer arg0, String arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getBanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean has(String arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(OfflinePlayer arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(String arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(OfflinePlayer arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(String arg0) {
		// TODO Auto-generated method stub
		return EstEconomy.db.hasPlayerData(Bukkit.getPlayer(arg0));
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0) {
		// TODO Auto-generated method stub
		return EstEconomy.db.hasPlayerData(arg0.getPlayer());
	}

	@Override
	public boolean hasAccount(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(OfflinePlayer arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasBankSupport() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String arg0, double arg1) {
		EstEconomy.db.takeMoney(Bukkit.getPlayer(arg0), arg1);
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer arg0, double arg1) {
		EstEconomy.db.takeMoney(arg0.getPlayer(), arg1);
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}
 
    //etc. (The rest are the standard ones, and all work after testing with ChestShop)
	 
}

