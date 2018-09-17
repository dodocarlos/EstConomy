package me.dodocarlos.esteconomy;

public class Vars {	
	
	public static EstEconomy main;
	
	public static boolean useUUIDSQL = true;
		
	public static String server;
	public static String db;
	public static String user;
	public static String pass;
	
	public static void setup(){
		server = main.getConfig().getString("Mysql.host");
		db = main.getConfig().getString("Mysql.db");
		user = main.getConfig().getString("Mysql.user");
		pass = main.getConfig().getString("Mysql.pass");
		
	}
	
}
