package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {

		this.gpspoints = new GPSPoint [n];
		this.antall = 0;
		
	}

	public GPSPoint[] getGPSPoints() {
		
		return this.gpspoints;
	}
	
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;
		
		if(antall < gpspoints.length) {
		gpspoints[antall] = gpspoint;
		antall++;
		inserted = true;
		}
		
		return inserted;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint nyttPunkt = GPSDataConverter.convert(time,latitude,longitude,elevation);
		boolean sattInn = insertGPS(nyttPunkt);
		
		return sattInn;
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for(int i = 0; i < gpspoints.length; i++) {
			
			String ut = gpspoints[i].toString();
			System.out.println(ut);
		}
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
