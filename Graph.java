import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;



public class Graph implements GraphInterface<Town, Road> {

	
	HashMap<Town, LinkedHashSet<Town>> adj;
	HashMap<Town, LinkedHashSet<Road>> roadMap;
	HashSet<Road> roadSet;
	ArrayList<Town> vertexList;
	int[] distanceArray;
	Town[] previousVertexArray;
	
	Graph(){
		adj = new HashMap<Town, LinkedHashSet<Town>>();
		roadMap = new HashMap<Town, LinkedHashSet<Road>>();
		roadSet = new HashSet<Road>();
	}
	
	@Override
	public Road getEdge(Town sv, Town dv) {
		
		if(adj.get(sv).contains(dv)) {
			for(Road road : roadSet) {
				if(road.connects(sv, dv)) {
					return road;
				}
			}
			
		}
		
		return null;
		
	}

	@Override
	public Road addEdge(Town sv, Town dv, int w1, String d2) throws IllegalArgumentException {
		
		Road roads = new Road(sv, dv, w1, d2);
		
		if(!containsVertex(sv) || !containsVertex(sv)) {
			throw new IllegalArgumentException();
		}
		
		if(!roadMap.keySet().contains(sv) || !roadMap.keySet().contains(dv)) {
			throw new IllegalArgumentException();
		}
		
		roadMap.get(sv).add(roads);
		roadMap.get(dv).add(roads);
		
		adj.get(sv).add(dv);
		adj.get(dv).add(sv);
		
		roadSet.add(roads);
		
		return roads;
		
	}

	
	@Override
	public boolean addVertex(Town tt) {
		
		if(!adj.keySet().contains(tt)) {
			adj.put(tt, tt.getAdjacentSet());
			roadMap.put(tt, new LinkedHashSet<Road>());
			return true;
		}
		
		return false;
		
	}

	
	@Override
	public boolean containsEdge(Town sv, Town dv) {
		for(Road road : roadSet) {	
			 if (road.connects(sv, dv)) return true;
		}
		return false;
	}

	
	@Override
	public boolean containsVertex(Town t1) {
		return adj.keySet().contains(t1);
	}

	
	@Override
	public Set<Road> edgeSet() {
		return roadSet;
	}

	
	public ArrayList<Town> getTowns() {
		return vertexList;
	}
	
	
	public ArrayList<String> getTownNames() {
		
		ArrayList<String> nu = new ArrayList<String>();
		
		for(Town tow : adj.keySet()) {
			nu.add(tow.toString());
		}
		
		return nu;
		
	}
	
	
	@Override
	public Set<Road> edgesOf(Town v) throws NullPointerException {

		if(roadMap.keySet().contains(v)) {
			return roadMap.get(v);
		}
		
		throw new NullPointerException();
	
	}

	
	@Override
	public Road removeEdge(Town sv, Town dv, int w1, String d1) {
		
		Road r1 = new Road(sv, dv, w1, d1);
		
		if(roadSet.contains(r1)) {
			roadSet.remove(r1);
			roadMap.remove(sv);
			roadMap.remove(dv);
			return r1;
		}
		
		return null;
		
	}

	
	@Override
	public boolean removeVertex(Town town) {
		if(adj.keySet().contains(town)) {
			LinkedHashSet del = roadMap.get(town);
			
			for(Town k1 : adj.keySet()) {
				adj.get(k1).removeAll(Collections.singleton(town));
				roadMap.get(k1).removeAll(del);
			}
			
			adj.remove(town);
			roadMap.remove(town);
			
			
			return true;
		}
		return false;
	}

	
	@Override
	public Set<Town> vertexSet() {
		return adj.keySet();
	}

	@Override
	public ArrayList<String> shortestPath(Town sv, Town dv) {
		
		dijkstraShortestPath(sv);
		System.out.println();
		Town t = dv;
		ArrayList<String> past = new ArrayList<String>();
		
		if(previousVertexArray[vertexList.indexOf(t)] != null) {
			while(!t.equals(sv)) {
				past.add(t.toString());
				t = previousVertexArray[vertexList.indexOf(t)];
			}
		} else if (!sv.equals(dv)) {
			throw new UnsupportedOperationException();
		}
		
		past.add(sv.toString());
		Collections.reverse(past);
		return past;
	}

	@Override
	public void dijkstraShortestPath(Town sv) {
		
		int s1 = vertexSet().size();
		
		vertexList = new ArrayList<Town>();
		distanceArray = new int[s1];
		previousVertexArray = new Town[s1];
		Arrays.fill(distanceArray, Integer.MAX_VALUE);
		
		ArrayList<Town> v = new ArrayList<Town>();
		ArrayList<Town> v1 = new ArrayList<Town>();
		
		for (Town t : vertexSet()){
			vertexList.add(t);
			v.add(t);
		}
		
		
		distanceArray[vertexList.indexOf(sv)] = 0;
		

		Town source;
		
		while(!v.isEmpty()) {
			
			int smallestDistIndex = vertexList.indexOf(v.get(0));
			
			for(int i = 0; i < s1; i++) {
				if(!v1.contains(vertexList.get(i)) && (distanceArray[i] < distanceArray[smallestDistIndex])) {
					smallestDistIndex = i;
				}
			}
			
			source = vertexList.get(smallestDistIndex);
			Town temp;
			
			for(Town t : adj.get(source)) {
				
				if(v.contains(t)) {
					
					int current = vertexList.indexOf(t);
					Town holdingOld = previousVertexArray[current];
					
					if(vertexList.get(current) != sv) {
						previousVertexArray[current] = source;
					}
					
					int distFromStart = 0;
					temp = t;
					
					while(!temp.equals(sv)) {
						distFromStart += getEdge(previousVertexArray[vertexList.indexOf(temp)], temp).getWeight();
						temp = previousVertexArray[ vertexList.indexOf(temp)];
					}
					
					if(distFromStart < distanceArray[current]) {
						distanceArray[current] = distFromStart;
						previousVertexArray[current] = source;
					} else {
						previousVertexArray[current] = holdingOld;
					}
					
				}
				
			}
			
			v1.add(source);
			v.remove(source);
			
		}		
	}
	
	@Override
	public String toString() {
		
		String stat = "TOWNS\n";
		
		if(adj != null) {
			for(Town t : adj.keySet()) {
				stat += t + ": " + adj.get(t) + "\n";
			}
			
			stat += "\nROADS\n";
		}
		
		if(roadMap != null) {
			for(Town t : roadMap.keySet()) {
				stat += t + ": " + roadMap.get(t) + "\n";
			}
		}
		return stat;
	}
}
