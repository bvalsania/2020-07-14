package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Coppia;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void listAllTeams(Map<Integer, Team> idMap){
		String sql = "SELECT * FROM Teams";
		//List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("TeamID"))) {
				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				idMap.put(res.getInt("TeamID"),team);
				}
			}
			conn.close();
		//	return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Team> getvertici(Map<Integer,Team> idMap){
		String sql = "SELECT t.TeamID AS t "
				+ "FROM teams t ";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(idMap.get(res.getInt("t")));

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	
	public void getArchi(Map<Integer, Team> idMap){
	
		String sql ="SELECT DISTINCT  m.TeamHomeID AS t1, m.TeamAwayID AS t2, m.ResultOfTeamHome AS puntiHome "
				+ "FROM matches m "
				+ "GROUP BY t1,t2 ";
		//List<Coppia> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(res.getInt("puntiHome")>0) {
					idMap.get(res.getInt("t1")).setPoints(3);
				}
				else if(res.getInt("puntiHome")==0) {
					idMap.get(res.getInt("t1")).setPoints(1);
					idMap.get(res.getInt("t2")).setPoints(1);
				}
				else {
					idMap.get(res.getInt("t2")).setPoints(3);
				}

			}
			conn.close();
			//return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	
	public List<Team> getvertici(Map<Integer, Team> idMap){
		String sql ="SELECT TeamID AS t "
				+ "FROM teams  ";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(idMap.get(res.getInt("t")));

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Coppia> getArchi(Map<Integer, Team> idMap){
		String sql ="SELECT a1.TeamID AS s1, a2.TeamID AS s2, m.ResultOfTeamHome AS peso "
				+ "FROM actions a1, actions a2, matches m "
				+ "WHERE a1.TeamID>a2.TeamID "
				+ "		AND a2.MatchID=a1.MatchID "
				+ "		AND m.MatchID=a2.MatchID "
				+ "		AND m.TeamHomeID=a1.TeamID "
				+ "		AND m.TeamAwayID=a2.TeamID "
				+ "GROUP BY s1,s2";
		List<Coppia> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team s1 = idMap.get(res.getInt("s1"));
				Team s2 = idMap.get(res.getInt("s2"));
				int punti = res.getInt("peso");
				
				result.add(new Coppia(s1,s2,punti));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	*/
}
