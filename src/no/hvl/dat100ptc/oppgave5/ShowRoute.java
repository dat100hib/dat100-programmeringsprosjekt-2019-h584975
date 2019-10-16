package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		playRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
		
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		double ystep = MAPYSIZE / (Math.abs(maxlat - minlat)); 
		
		return ystep;
	}

	public void showRouteMap(int ybase) {

		double ystep = ystep() * 0.7;
		double xstep = xstep();
		int xstart = 50;
		int x = 0;
		int y = 0;
		int ystart = (int)(ybase * 0.25);
		for (int i = 0; i < gpspoints.length; i++) {
				if (i == 0) {
					setColor(0,255,0);
					fillCircle(xstart, (int)(ystart), 3);
				} else {
					x += (int)((gpspoints[i].getLongitude() - gpspoints[i-1].getLongitude()) * xstep);
					y += (int)((gpspoints[i].getLatitude() - gpspoints[i-1].getLatitude()) * ystep);
					
					pause(10);
					fillCircle(xstart + x, ystart - y, 3);
					
					
				}
		}
		
		
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		drawString("Total time      :     " + GPSUtils.formatTime(gpscomputer.totalTime()), 10,20);
		pause(200);
		drawString("Total distance  :     " + String.format("%7.2f", gpscomputer.totalDistance()/1000.00) + " km",10,40);
		pause(200);
		drawString("Total elevation :     " + String.format("%7.2f", gpscomputer.totalElevation()) + " m",10,60);
		pause(200);
		drawString("Max speed       :     " + String.format("%7.2f", gpscomputer.maxSpeed()) + " km/t",10,80);
		pause(200);
		drawString("Average speed   :     " + String.format("%7.2f", gpscomputer.averageSpeed()) + " km/t",10,100);
		pause(200);
		drawString("Energy          :     " + String.format("%7.2f", gpscomputer.totalKcal(80)) + " kcal",10,120);
	}

	public void playRoute(int ybase) {


	}

}
