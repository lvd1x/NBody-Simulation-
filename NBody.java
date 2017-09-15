public class NBody {

	public static double readRadius(String filename) {
		//* returns the radius of the universe */
		In in = new In(filename);
		int numOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();
		return radiusOfUniverse;
	}

	public static Planet[] readPlanets(String filename) {
		//* returns array of planets with original position and velocity */
		In in = new In(filename);
		int nPlanets = in.readInt();
		in.readDouble();
		Planet[] planets = new Planet[nPlanets];
		int i = 0;
		while (i < nPlanets) {
			double xPos = in.readDouble();
			double yPos = in.readDouble();
			double xVel = in.readDouble();
			double yVel = in.readDouble();
			double mass = in.readDouble();
			String name = in.readString();
			planets[i] = new Planet(xPos, yPos, xVel, yVel, mass, name);
			i += 1;
		}
		return planets;
	}

	public static void main(String[] args) {
		//* begins simulation */
		double timeVariable = 0;
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double universeRadius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		double[] xForces = new double[planets.length];
		double[] yForces = new double[planets.length];
		StdAudio.play("audio/2001.mid");
		while (timeVariable < T) {

			for (int i = 0; i < planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			//* updates status of each planet */
			for (int i = 0; i < planets.length; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}

			//* draws universe and planets */
			StdDraw.setScale(-universeRadius, universeRadius);
			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet planet : planets) {
				planet.draw();
			}

			StdDraw.show(10);
			timeVariable += dt;
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", universeRadius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   				planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);	
		}
	}
}
