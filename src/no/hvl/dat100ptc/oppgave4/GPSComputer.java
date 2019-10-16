package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	public double totalDistance() {

		double distance = 0;

		for(int i = 1; i < gpspoints.length; i++) {
			
			distance += GPSUtils.distance(gpspoints[i-1], gpspoints[i]);
		}
		return distance;
	}

	public double totalElevation() {

		double elevation = 0;

		for(int i = 1; i < gpspoints.length; i++) {
			
			if(gpspoints[i-1].getElevation() < gpspoints[i].getElevation()) {
			elevation +=  gpspoints[i].getElevation() - gpspoints[i-1].getElevation(); 
					
			}
		}
		return elevation;
	}

	public int totalTime() {

		int tid = gpspoints[gpspoints.length-1].getTime() - gpspoints[0].getTime();
		return tid;

	}
		

	public double[] speeds() {
		
		double [] fart = new double [gpspoints.length-1];
		
		for(int i = 0; i < fart.length; i++) {
			fart[i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
			
		}
		return fart;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		double [] fart = speeds();
		
		for(int i = 0; i < fart.length; i++) {
			
			if(maxspeed < fart[i]) {
				maxspeed = fart[i];
			}
		}
		return maxspeed;
	}

	public double averageSpeed() {
		
		double tid = totalTime();
		double distanse = totalDistance();
		double mps = distanse/tid;
		
		return mps * 3.6;
	}

	public static double MS = 2.236936;

	public double kcal(double weight, int secs, double speed) {

		double met = 0;		
		double speedmph = speed * 0.62;

		if(speedmph < 10) {
			met = 4.0;
			}
			else if((speedmph >= 10)&&(speedmph < 12)) {
			met = 6.0;
			}
			else if((speedmph >= 12)&&(speedmph < 14)) {
			met = 8.0;
			}
			else if((speedmph >= 14)&&(speedmph < 16)) {
			met = 10.0;
			}
			else if((speedmph >= 16)&&(speedmph < 20)) {
			met = 12.0;
			}
			else if(speedmph >= 20) {
			met = 16.0;
			}
		
		double timer = ((double)secs/3600);
		
		return weight * timer * met;
	}

	public double totalKcal(double weight) {
		
		double totalkcal = 0;
		double [] fart = speeds();
		
		for(int i = 0; i < gpspoints.length-2; i++) {
		
			int tid = gpspoints[i+1].getTime()-gpspoints[i].getTime();
			double speed = fart[i];
			
			totalkcal += kcal(weight,tid,speed);
		}
		return totalkcal;
		
		
	
	}
		
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		
		System.out.println("==============================================");
		System.out.println("Total time      :     " + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance  :     " + String.format("%7.2f", (totalDistance()/1000.00)) + " km");
		System.out.println("Total elevation :     " + String.format("%7.2f", totalElevation()) + " m");
		System.out.println("Max speed       :     " + String.format("%7.2f", maxSpeed()) + " km/t");
		System.out.println("Average speed   :     " + String.format("%7.2f", averageSpeed()) + " km/t");
		System.out.println("Energy          :     " + String.format("%7.2f", totalKcal(WEIGHT)) + " kcal");
		System.out.println("==============================================");
	}
	
}
