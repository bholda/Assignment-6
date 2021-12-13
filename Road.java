public class Road implements Comparable<Road>{

	String n1;
	Town o1, o2;
	int dist;
	
	public Road(Town location, Town dest, int lbs, String p1) {
		dist = lbs;
		o1 = location;
		o2 = dest;
		this.n1 = p1;
	}

	public Road(Town source, Town destination, String name) {
		dist = 1;
		o1 = source;
		o2 = destination;
		this.n1 = name;
	}
	
	
	public boolean contains(Town town) {
		return (town.equals(o1) || town.equals(o2));
	}
	
	public boolean connects(Town town1, Town town2) {
		return (town1.equals(o1) || town1.equals(o2)) && (town2.equals(o1) || town2.equals(o2));
	}
	
	public String getName() {
		return n1;
	}
	
	public Town getSource() {
		return o1;
	}
	
	public Town getDestination() {
		return o2;
	}
	
	public int getWeight() {
		return dist;
	}
	
	@Override
	public boolean equals(Object r) {
		
		if (!(r instanceof Road)) {
			return false;
		}
		
		Road road = (Road) r;
		
		return road.getSource().equals(this.getSource()) ||
                road.getSource().equals(this.getDestination()) ||
                road.getDestination().equals(this.getSource()) ||
                road.getDestination().equals(this.getDestination());
		
	}
	

	@Override
	public int compareTo(Road o) {
		return n1.compareTo(o.getName());
	}

	@Override
	public String toString() {
		return n1;//one + " via " + name + " to " + two + " " + distance + " mi";
	}
	
	public boolean equals(Road o) {
		return n1.equals(o.getName());
	}
}
