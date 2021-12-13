import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Town implements Comparable<Town>{
	
	String person;
	LinkedHashSet<Town> closeTowns;
	
	Town(Town template){
		person = template.getName();
		closeTowns = template.closeTowns;
	}

	Town(String name){
		this.person = name;
		closeTowns = new LinkedHashSet<Town>();
	}
	
	public String getName() { 
		return person; 
		}

	public LinkedHashSet getAdjacentSet() {
		return closeTowns; 
		}
	
	@Override
	public int compareTo(Town o) {
		return person.compareTo(o.getName()); 
		}
	
	public boolean equals(Object o) { 
		
		if (!(o instanceof Town)) {
			return false;
		}
		
		return (this.compareTo((Town) o) == 0) ? true : false; 
	}
	
	public int hashCode() { 
		return person.hashCode(); 
		}
	
	public String toString() { 
		return person; 
		}
}