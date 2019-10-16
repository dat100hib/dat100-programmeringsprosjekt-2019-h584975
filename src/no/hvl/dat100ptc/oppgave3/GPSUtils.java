package no.hvl.dat100ptc.oppgave3;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		return max;
	}

	public static double findMin(double[] da) {

		double min = da[0];
		
		for (double d : da) {
			if(d < min) {
				min = d;
			}
		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double [] tab = new double [gpspoints.length];
		
		for(int i = 0; i < tab.length; i++) {
			tab[i] = gpspoints[i].getLatitude();
		}
		return tab;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double [] tab = new double [gpspoints.length];
		
		for(int i = 0; i < tab.length; i++) {
			tab[i] = gpspoints[i].getLongitude();
		}
		return tab;

	}

	private static int R = 6371000;

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = gpspoint1.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		latitude2 = gpspoint2.getLatitude();
		longitude2 = gpspoint2.getLongitude();
		
		double Q1 = Math.toRadians(latitude1);
		double Q2 = Math.toRadians(latitude2);
		double deltaQ = Q2-Q1;
		double deltaH = Math.toRadians(longitude2-longitude1);
		
		double a1 = Math.pow(Math.sin(deltaQ/2), 2);
		double a2 = Math.pow(Math.sin(deltaH/2), 2);
		
		double a = a1 + Math.cos(Q1) * Math.cos(Q2) * a2;
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		d = R * c;
		
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int sekDiff = gpspoint2.getTime() - gpspoint1.getTime();
		double meter = distance(gpspoint1,gpspoint2);
		double mps = meter/sekDiff;
		double kmt = mps * 3.6;
		
		return kmt;
	}


	public static String formatTime(int secs) {
		
		int tim = (secs / 3600);
		int min = (secs % 3600) / 60;
		int sek = (secs % 60);
		
		String tid = String.format("%02d:%02d:%02d", tim, min, sek);
		String s = String.format("%10s", tid);
		return s;

	}
	
	public static String formatDouble(double d) {

		String s = String.format("%10.2f", d);
		s = s.replace(",", ".");
		return s;
		
	}
}
