package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	NercIdMap map;
	private List<PowerOutage> soluzione = new ArrayList<PowerOutage>();
	private long totale = 0;
	private List<PowerOutage> fullOutageList;
	private List<PowerOutage> outageList = new ArrayList<PowerOutage>();
	private long oreTotali = 0;
	
	public Model() {
		podao = new PowerOutageDAO();
		map = new NercIdMap();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList(map);
	}
	
	public List<PowerOutage> getOutageList() {
		return podao.getOutageList(map);
	}
	
	public String worstCase(Nerc nerc, int years, long hours) {
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		fullOutageList = new ArrayList<PowerOutage>(podao.getOutageList(map));
		for(PowerOutage p : fullOutageList) {
			if(p.getNerc().equals(nerc)) outageList.add(p);
		}
		cerca(parziale, 0, 0, years, hours);
		String s = "Tot people affected: "+totale+"\nTot hours of outage: "+oreTotali+"\n";
		for(PowerOutage p : soluzione) {
			s = s+p.getDateEventBegan().getYear()+" "+p.getDateEventBegan()+" "+p.getDateEventFinish()+" "+p.getCustomersAffected()+"\n";
		}
		return s;
	}
	
	private boolean checkYear(List<PowerOutage> parziale, int years) {
		if(parziale.size()>1) {
			PowerOutage inizio = parziale.get(0);
			PowerOutage fine = parziale.get(parziale.size()-1);
			if((inizio.getDateEventBegan().getYear()-fine.getDateEventBegan().getYear()+1)>years) return false;
		}
		return true;
	}
	
	private void cerca(List<PowerOutage> parziale, long ore, int persone, int years, long hours) {
		if(ore>hours && parziale.size()>1) {
			PowerOutage p = parziale.get(parziale.size()-1);
			parziale.remove(parziale.size()-1);
			ore = ore-p.getOre();
			persone = persone-p.getCustomersAffected();
			if(persone<totale) return;
			else {
				PowerOutage inizio = parziale.get(0);
				PowerOutage fine = parziale.get(parziale.size()-1);
				if((inizio.getDateEventBegan().getYear()-fine.getDateEventBegan().getYear()+1)>years) return;
				soluzione = new ArrayList<PowerOutage>(parziale);
				totale = persone;
				oreTotali = ore;
			}
		}
		for(PowerOutage p : outageList) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				if(checkYear(parziale, years) && ore+p.getOre()<hours) {
					cerca(parziale, ore+p.getOre(), persone+p.getCustomersAffected(), years, hours);
				}
				parziale.remove(p);
			}
		}
		if(persone<totale) return;
		else {
			PowerOutage inizio = parziale.get(0);
			PowerOutage fine = parziale.get(parziale.size()-1);
			if((inizio.getDateEventBegan().getYear()-fine.getDateEventBegan().getYear())>years) return;
			soluzione = new ArrayList<PowerOutage>(parziale);
			totale = persone;
			oreTotali = ore;
		}
	}

}
