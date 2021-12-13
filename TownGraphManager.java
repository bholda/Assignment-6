import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class TownGraphManager implements TownGraphManagerInterface {

	
	Graph graph;
	
	TownGraphManager(){
		graph = new Graph();
	}
	
	@Override
	public boolean addRoad(String townOne, String townTwo, int lbs, String name) {
		
		try {
			Town firstTown = new Town(townOne);
			Town secondtown = new Town(townTwo);
			graph.addEdge(firstTown, secondtown, lbs, name);
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}
	
	@Override
	public String getRoad(String townOne, String townTwo) {
		
		Town firstTown = new Town(townOne);
		Town secondtown = new Town(townTwo);
		
		for(Road r : graph.roadMap.get(firstTown)) {
			if(r.connects(firstTown, secondtown)) {
				return r.toString();
			}
		}
		
		return null;
		
	}

	@Override
	public boolean addTown(String v) {
		
		try {
			graph.addVertex(new Town(v));
		} catch (Exception e) {
			return false;
		}
		
		return true;
		
	}

	@Override
	public Town getTown(String title) {
		
		for(Town t : graph.vertexSet()) {
			if(t.getName().equals(title)) {
				return t;
			}
		}
		
		return null;
		
	}

	
	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}
	
	
	@Override
	public boolean containsRoadConnection(String townOne, String townTwo) {
		
		Town firstTown = new Town(townOne);
		Town secondtown = new Town(townTwo);
		HashSet<Road> smallestSet = (graph.roadMap.get(firstTown).size() < graph.roadMap.get(firstTown).size()) ? graph.roadMap.get(firstTown) : graph.roadMap.get(secondtown);
		
		for(Road r : smallestSet) {
			if(r.connects(firstTown, secondtown)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public ArrayList<String> allRoads() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(Road r : graph.edgeSet()) {
			list.add(r.toString());
		}
		
		Collections.sort(list);
		return list;
		
	}

	@Override
	public boolean deleteRoadConnection(String townOne, String townTwo, String road) {
		
		Town firstTown = new Town(townOne);
		Town secondtown = new Town(townTwo);
		
		if(graph.removeEdge(firstTown, secondtown, 1, road) != null) {
			return true;
		}
		
		return false;
		
	}

	
	@Override
	public boolean deleteTown(String v) {
		Town town = new Town(v);
		return graph.removeVertex(town);
	}

	
	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> list = graph.getTownNames();
		Collections.sort(list);
		return list;
	}
	
	
	@Override
	public ArrayList<String> getPath(String townOne, String townTwo) {
		Town firstTown = new Town(townOne);
		Town secondtown = new Town(townTwo);
		return graph.shortestPath(firstTown, secondtown);
	}

	
	public void clearTownFields() {
		graph.vertexList = null;
		graph.distanceArray = null;
		graph.previousVertexArray = null;
	}

	public ArrayList<String> getPathSets(String first, String second) {
		
		ArrayList<String> path = new ArrayList<String>();
		ArrayList<String> townpath = getPath(first, second);
		Town holder = new Town("");
		
		for(int i = 0; i < townpath.size(); i++) {
			
			if(i != 0) {
				Road road1 = graph.getEdge(holder, new Town(townpath.get(i)));
				path.add(holder + " via " + road1.toString() + " to " + townpath.get(i) + " " + road1.getWeight() + " mi\n");
			}
			holder = new Town(townpath.get(i));
		}
		return path;
	}
}
