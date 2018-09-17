package me.dodocarlos.esteconomy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

public class DB {
	
	private boolean uuid = Vars.useUUIDSQL;
	
	private static Connection conn;
	private static Statement stm;
	
	public DB(){
		try {
			String url = "jdbc:mysql://" + Vars.server + "/" + Vars.db + "?autoReconnect=true";
			
			Class.forName("com.mysql.jdbc.Driver");
			
		    conn = DriverManager.getConnection(url, Vars.user, Vars.pass);

			stm = conn.createStatement();

			if(uuid){
				stm.executeUpdate("CREATE TABLE IF NOT EXISTS status(`id` integer primary key auto_increment, uuid varchar(500), kills integer, deaths integer, money integer);");
				stm.executeUpdate("CREATE TABLE IF NOT EXISTS bans(`id` integer primary key auto_increment, `banner` varchar(500) NOT NULL, `banned` varchar(500) NOT NULL, `uuid` varchar(500) NOT NULL, `motivo` varchar(5000) NOT NULL, `time` varchar(500) NOT NULL);");
			}else{
				stm.executeUpdate("CREATE TABLE IF NOT EXISTS status(`id` integer primary key auto_increment, nick varchar(500), kills integer, deaths integer, money integer);");
				stm.executeUpdate("CREATE TABLE IF NOT EXISTS bans(`id` integer primary key auto_increment, `banner` varchar(500) NOT NULL, `banned` varchar(500) NOT NULL, `uuid` varchar(500) NOT NULL, `motivo` varchar(5000) NOT NULL, `time` varchar(500) NOT NULL);");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean hasPlayerData(Player p){
		try {
			String uuid = p.getUniqueId().toString();
			if(this.uuid){
				PreparedStatement query = conn.prepareStatement("SELECT * FROM status where uuid = ?");
				query.setString(1, uuid);
				ResultSet rs = query.executeQuery();
				return rs.next();
			}else{
				PreparedStatement query = conn.prepareStatement("SELECT * FROM status where nick = ?");
				query.setString(1, p.getName());
				ResultSet rs = query.executeQuery();
				return rs.next();
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean hasPlayerbanData(Player p){
		try {
			String uuid = p.getUniqueId().toString();
			if(this.uuid){
				PreparedStatement query = conn.prepareStatement("SELECT * FROM bans where uuid = ?");
				query.setString(1, uuid);
				ResultSet rs = query.executeQuery();
				return rs.next();
			}else{
				PreparedStatement query = conn.prepareStatement("SELECT * FROM bans where banned = ?");
				query.setString(1, p.getName());
				ResultSet rs = query.executeQuery();
				return rs.next();
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean hasPlayerbanData(String nick){
		try {
				PreparedStatement query = conn.prepareStatement("SELECT * FROM bans where banned = ?");
				query.setString(1, nick);
				ResultSet rs = query.executeQuery();
				return rs.next();
						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
		
	}
	
	public int getkills(Player p){
		String uuid = p.getUniqueId().toString();
		int kills = 0;		
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				ResultSet rs = stm.executeQuery("SELECT kills from status where uuid = '" + uuid + "'");
				
				while(rs.next()){
					kills = rs.getInt(1);
				}
			}else{
				ResultSet rs = stm.executeQuery("SELECT kills from status where nick = '" + p.getName() + "'");
				
				while(rs.next()){
					kills = rs.getInt(1);
				}
			}
		}catch(SQLException e){
		}
		
		return kills;
	}
	
	public int getDeaths(Player p){
		String uuid = p.getUniqueId().toString();
		int deaths = 0;		
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				ResultSet rs = stm.executeQuery("SELECT deaths from status where uuid = '" + uuid + "'");
				
				while(rs.next()){
					deaths = rs.getInt(1);
				}
			}else{
				ResultSet rs = stm.executeQuery("SELECT deaths from status where nick = '" + p.getName() + "'");
				
				while(rs.next()){
					deaths = rs.getInt(1);
				}
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return deaths;
	}
		
	public double getMoney(Player p){
		String uuid = p.getUniqueId().toString();
		double money = 0;		
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				ResultSet rs = stm.executeQuery("SELECT money from status where uuid = '" + uuid + "'");			
				while(rs.next()){
					money = rs.getInt(1);
				}
			}else{
				ResultSet rs = stm.executeQuery("SELECT money from status where nick = '" + p.getName() + "'");			
				while(rs.next()){
					money = rs.getInt(1);
				}
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return money;
		
	}
		
	public int getKD(Player p){
		String uuid = p.getUniqueId().toString();
		int kills = 0;		
		int deaths = 0;	
		int kd = 0;
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				ResultSet rs = stm.executeQuery("SELECT kills, deaths from status where uuid = '" + uuid + "'");
				while(rs.next()){
					kills = rs.getInt(1);
					deaths = rs.getInt(2);
				}
			}else{
				ResultSet rs = stm.executeQuery("SELECT kills, deaths from status where nick = '" + p.getName() + "'");
				while(rs.next()){
					kills = rs.getInt(1);
					deaths = rs.getInt(2);
				}
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(deaths == 0){
			if(kills > 0){
				kd = 1;
			}else{
				kd = 0;
			}
		}else{
			kd = kills/deaths;
		}
		
		return kd;
	}
	
	public void addKill(Player p){
		String uuid = p.getUniqueId().toString();
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				stm.executeUpdate("UPDATE status set kills = kills+1 where uuid = '" + uuid + "'");

			}else{
				stm.executeUpdate("UPDATE status set kills = kills+1 where nick = '" + p.getName() + "'");
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public void addDeath(Player p){
		String uuid = p.getUniqueId().toString();
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				stm.executeUpdate("UPDATE status set deaths = deaths+1 where uuid = '" + uuid + "'");
			}else{
				stm.executeUpdate("UPDATE status set deaths = deaths+1 where nick = '" + p.getName() + "'");
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	public void addMoney(Player p, double arg1){
		String uuid = p.getUniqueId().toString();
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				stm.executeUpdate("UPDATE status set money = money + " + arg1 + " where uuid = '" + uuid + "'");
			}else{
				stm.executeUpdate("UPDATE status set money = money + " + arg1 + " where nick = '" + p.getName() + "'");
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}

	public void takeMoney(Player p, double arg1){
		String uuid = p.getUniqueId().toString();
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				stm.executeUpdate("UPDATE status set money = money - " + arg1 + " where uuid = '" + uuid + "'");
			}else{
				stm.executeUpdate("UPDATE status set money = money - " + arg1 + " where nick = '" + p.getName() + "'");
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void setMoney(Player p, int value){
		String uuid = p.getUniqueId().toString();
		try{
			if(!hasPlayerData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
				}else{
					stm.executeUpdate("INSERT INTO status(nick, kills, deaths, money) values('" + p.getName() + "', 0, 0, 0)");
				}
			}
			if(this.uuid){
				stm.executeUpdate("UPDATE status set money = " + value + " where uuid = '" + uuid + "'");
			}else{
				stm.executeUpdate("UPDATE status set money = " + value + " where nick = '" + p.getName() + "'");
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO status(uuid, kills, deaths, money) values('" + uuid + "', 0, 0, 0)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//Bans
	public void banPlayer(Player p, String banner, String reason, String time){
		String uuid = p.getUniqueId().toString();
		try{
			if(!hasPlayerbanData(p)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO bans(id, banner, banned, uuid, motivo, time) values(0, '" + banner + "', '" + p.getName() + "', '" + uuid + "', '" + reason + "', '" + time + "')");
				}else{
					stm.executeUpdate("INSERT INTO bans(id, banner, banned, uuid, motivo, time) values(0, '" + banner + "', '" + p.getName() + "', '" + uuid + "', '" + reason + "', '" + time + "')");
				}
			}
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO bans(id, banner, banned, uuid, motivo, time) values(0, '" + banner + "', '" + p.getName() + "', '" + uuid + "', '" + reason + "', '" + time + "')");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void unbanPlayer(Player p, String banner, String reason, String time){
		String uuid = p.getUniqueId().toString();
		try{
			if(this.uuid){
				stm.executeUpdate("DELETE FROM bans WHERE uuid = '" + uuid + "'");
			}else{
				stm.executeUpdate("DELETE FROM bans WHERE banned = '" + p.getName() + "'");
			}
		}catch(SQLException e){
	
		}
	}
	
	public void banPlayer(String nick, String banner, String reason, String time){
		try{
			if(!hasPlayerbanData(nick)){
				if(this.uuid){
					stm.executeUpdate("INSERT INTO bans(id, banner, banned, uuid, motivo, time) values(0, '" + banner + "', '" + nick + "', '" + uuid + "', '" + reason + "', '" + time + "')");
				}else{
					stm.executeUpdate("INSERT INTO bans(id, banner, banned, uuid, motivo, time) values(0, '" + banner + "', '" + nick + "', '" + uuid + "', '" + reason + "', '" + time + "')");
				}
			}
			
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO bans(nick, banner, reason, time, banned) values('" + nick + "', '" + banner + "', '" + reason + "', '" + time + "', 'true')");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void unbanPlayer(String nick){
		try{
			stm.executeUpdate("DELETE FROM bans WHERE banned = '" + nick + "'");
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO bans(nick, banner, reason, time, banned) values('" + nick + "', '', '', '', 'false')");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public boolean hasBanned(Player p){
		try {
			String uuid = p.getUniqueId().toString();
			if(this.uuid){
				PreparedStatement query = conn.prepareStatement("SELECT * FROM bans where uuid = ?");
				query.setString(1, uuid);
				ResultSet rs = query.executeQuery();
				return rs.next();
			}else{
				PreparedStatement query = conn.prepareStatement("SELECT * FROM bans where banned = ?");
				query.setString(1, p.getName());
				ResultSet rs = query.executeQuery();
				return rs.next();
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean hasBanned(String nick){
		try {
				PreparedStatement query = conn.prepareStatement("SELECT * FROM bans where banned = ?");
				query.setString(1, nick);
				ResultSet rs = query.executeQuery();
				return rs.next();
					
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
		
	}
	
	public String getBanReason(Player p){
		String uuid = p.getUniqueId().toString();
		String reason = "";		
		try{
			if(this.uuid){
				ResultSet rs = stm.executeQuery("SELECT motivo from bans where uuid = '" + uuid + "'");			
				while(rs.next()){
					reason = rs.getString(1);
				}
			}else{
				ResultSet rs = stm.executeQuery("SELECT motivo from bans where banned = '" + p.getName() + "'");			
				while(rs.next()){
					reason = rs.getString(1);
				}
			}
		}catch(SQLException e){
			
		}
		
		return reason;
	}
	
	public String getBanReason(String nick){
		String reason = "";		
		try{

				ResultSet rs = stm.executeQuery("SELECT motivo from bans where banned = '" + nick + "'");			
				while(rs.next()){
					reason = rs.getString(1);
				
			}
		}catch(SQLException e){
			
		}
		
		return reason;
	}
	
	public void addReport(String target, String sender, String reason, String servidor){
		try{
			stm.executeUpdate("INSERT INTO reports(id, sender, target, motivo, servidor, status) values(0, '" + sender + "', '" + target + "', '" + reason + "', '" + servidor + "', 'aberto')");
		}catch(SQLException e){
			try {
				stm.executeUpdate("INSERT INTO reports(id, sender, target, motivo, servidor, status) values(0, '" + sender + "', '" + target + "', '" + reason + "', '" + servidor + "', 'aberto')");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
