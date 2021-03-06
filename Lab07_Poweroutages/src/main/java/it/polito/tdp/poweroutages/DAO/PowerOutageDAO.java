package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercIdMap;
import it.polito.tdp.poweroutages.model.PowerOutage;
import java.sql.*;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList(NercIdMap map) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(map.get(n));
			}
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutage> getOutageList(NercIdMap map) {

		String sql = "SELECT customers_affected, nerc_id, date_event_began, date_event_finished FROM poweroutages";
		List<PowerOutage> outageList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = map.get(res.getInt("nerc_id"));
				if(n==null) System.err.println("error");
				else {
					PowerOutage p = new PowerOutage(res.getInt("customers_affected"), res.getTimestamp("date_event_began").toLocalDateTime(), res.getTimestamp("date_event_finished").toLocalDateTime(), n);
					outageList.add(p);
				}
			}
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return outageList;
	}

}
