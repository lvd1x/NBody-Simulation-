public class Planet {
	//* instance variables */
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, 
				  double yV, double m, String img) {
		//* constructor for planet class */
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	} 

	public Planet(Planet p) { 
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public void draw() { 
		//* draws planet in the coordinates given */
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}

	private static double deltaX(Planet p1, Planet p2) {
		//* returns the difference between X components of p2 and p1 */
		return (p2.xxPos - p1.xxPos); 
	}

	private static double deltaY(Planet p1, Planet p2) {
	 	//* returns the difference between Y components of p2 and p1 */
	 	return (p2.yyPos - p1.yyPos);
	}

	public double calcDistance(Planet p) {
		//* returns distance between two points */
		double deltX;
		double deltY;
		deltX = Planet.deltaX(this, p);
		deltY = Planet.deltaY(this, p);
		return Math.sqrt((deltX * deltX) + (deltY * deltY));
	}

	public double calcForceExertedBy(Planet p) {
		//* returns force exerted on the planet by planet p */
		double gravConst = 6.67e-11;
		return (gravConst * ((this.mass * p.mass)/(this.calcDistance(p) * this.calcDistance(p))));
	}

	public double calcForceExertedByX(Planet p) {
		//* returns force exerted on the planet by planet p in the x component */
		double forceExertedBy;
		double distance;
		double deltX;
		forceExertedBy = this.calcForceExertedBy(p);
		distance = this.calcDistance(p);
		deltX = Planet.deltaX(this, p);
		return ((forceExertedBy * deltX) / distance);
	}

	public double calcForceExertedByY(Planet p) {
		//* returns force exerted on the planet by planet p in the y component */
		double forceExertedBy;
		double distance;
		double deltY;
		forceExertedBy = this.calcForceExertedBy(p);
		distance = this.calcDistance(p);
		deltY = Planet.deltaY(this, p);
		return ((forceExertedBy * deltY) / distance);
	} 

	public double calcNetForceExertedByX(Planet[] p) {
		//* returns sum off all forces in X dierction exerted on a planet by other planets */
		double netForce = 0;
		for (Planet planet : p) {
			if (!this.equals(planet)) {
				netForce += this.calcForceExertedByX(planet);
			}
		}
		return netForce;
	}

	public double calcNetForceExertedByY(Planet[] p) {
		//* returns sum off all forces in Y dierction exerted on a planet by other planets */
		double netForce = 0;
		for (Planet planet : p) {
			if (!this.equals(planet)) {
				netForce += this.calcForceExertedByY(planet);
			}
		}
		return netForce;
	}

	public void update(double dt, double fX, double fY) {
		//* updates position and stuff */
		double accX = (fX / this.mass);
		double accY = (fY / this.mass);
		this.xxVel = (this.xxVel + (dt * accX));
		this.yyVel = (this.yyVel + (dt * accY));
		this.xxPos = (this.xxPos + (dt * this.xxVel));
		this.yyPos = (this.yyPos + (dt * this.yyVel));
	}
}