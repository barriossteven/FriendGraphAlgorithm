package structures;

import java.io.*;
import java.util.*;

public class graph implements Cloneable
{
	public static Vertex[] friends;
	public static int globalDfsNum = 0;
	public static int startPt = 0;
	
	//clone stuff
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
		
	}
	
	public graph(ArrayList<Vertex> subgraph)
	{
		for(int i=0;i<subgraph.size();i++)
		{
			
			friends[i] = subgraph.get(i);
		}
	}
	
	
	
	public graph(String file) throws FileNotFoundException{
		
		Scanner sc = new Scanner(new File(file));
		
		int numFriends = sc.nextInt();
		friends = new Vertex[numFriends];
		System.out.println("Total friends: " + numFriends);
		sc.nextLine();
		
		for(int i=0;i<friends.length;i++)
		{
			String nextFriend = sc.nextLine().toLowerCase();
			sc.useDelimiter("\n");
			
			StringTokenizer st = new StringTokenizer(nextFriend, "|");
			
			friends[i] = new Vertex(st.nextToken(), null, false, null);
			
			if(st.nextToken().compareTo("y")==0)
			{
				friends[i].isStudent = true;
				friends[i].school = st.nextToken();
			}
		}
		
		while(sc.hasNext())
		{
			String connection = sc.nextLine();
			StringTokenizer st = new StringTokenizer(connection, "|");
			
			String friendOne = st.nextToken();
			String friendTwo = st.nextToken();
			
			int p1 = indexForName(friendOne);
			int p2 = indexForName(friendTwo);
			
			friends[p1].adjList = new Neighbor(p2,friends[p1].adjList);
			friends[p2].adjList = new Neighbor(p1,friends[p2].adjList);
		}
		System.out.println();
		
	}


	public static void main(String[] args) throws IOException, CloneNotSupportedException
	{
	
	}
	
	public static ArrayList<graph> cliqueWrap(graph friendGraph, String school ) throws CloneNotSupportedException
	{

		//parameter for name
		//non static to static
		/*graph g1 = (graph)friendGraph.clone();
		g1.friends = (Vertex[])g1.friends.clone();
		
		System.out.println(friendGraph.friends[0].name);
		g1.friends[0].name= "steven";
		System.out.println(g1.friends[0].name);
		System.out.println(friendGraph.friends[0].name);
		Vertex[] tempg1 = new Vertex[15];
		Vertex[] tempfg = new Vertex[15];
		for(int i = 0;i< g1.friends.length; i++){
			tempg1[i] = g1.friends[i];
			tempfg[i]= friendGraph.friends[i];
		}*/
		
		
		boolean[] visited = new boolean[friendGraph.friends.length];
		ArrayList< ArrayList<Vertex> > res = new ArrayList<ArrayList<Vertex>>();
		for(int i=0;i<friendGraph.friends.length;i++)
		{
			if(visited[i]==false)
			{
				ArrayList<Vertex> results = new ArrayList<Vertex>();
				results = cliqueDFS(friendGraph.friends[i],visited, results);
				res.add(results);				
			}
		}
		killBlanks(res);
		int cliqueNum = 1;//clique variable
		
		
		for(int k=0;k<res.size();k++)
		{	
			if(res.get(k).get(0).school.equals(school)){
				System.out.println("clique: " + cliqueNum);
				cliqueNum++;
				for(int j=0;j<res.get(k).size();j++)
				{
					/*if(j == 0 && res.get(k).get(j).school.equals(school)){
						System.out.println("Clique: "+ cliqueNum);
						cliqueNum++;
					}*/
					
					if(res.get(k).get(j).school.equals(school)){
					System.out.println(res.get(k).get(j).name);
					}
				}
				
			}
			
			
		}

		
		return null;
		
	}
	
	public static ArrayList<Vertex> cliqueDFS(Vertex v, boolean[] visited, ArrayList<Vertex> results)
	{
		visited[indexForName(v.name)] = true;
		if(results.contains(v)==false && v.school!=null)
		{
			results.add(v);
		}
		
		while(v.adjList!=null)
		{
			Vertex w = friends[v.adjList.vertexNum];
			
			if((visited[indexForName(w.name)] == false))
			{
				if(w.school!=null)
				{
					if(w.school.equals(v.school))
					{
						results.add(w);
						cliqueDFS(w,visited, results);
					}

				}
			}
			v.adjList=v.adjList.next;
		}
		
		return results;
		
	}
	
	private static void dfs(Vertex v, boolean[] visited, ArrayList<String> results)
	{
		
		globalDfsNum++;
		v.dfsNum = globalDfsNum;
		v.back = globalDfsNum;
		
		visited[indexForName(v.name)] = true;
		
		int neighborCount = neighborCount(v);
		while(v.adjList!=null)
		{
			Vertex w = friends[v.adjList.vertexNum];
			if(visited[indexForName(w.name)] == false)
			{
				dfs(w, visited, results);
				if((indexForName(w.name) != startPt) && (v.dfsNum<=w.back))
				{
					if((results.contains(v.name) == false) && (neighborCount>1))
					{
						results.add(v.name);
					}
				}
				if(v.dfsNum > w.back)
				{
					v.back = Math.min(v.back, w.back);
				}
			}
			else
			{
				v.back = Math.min(v.back, w.dfsNum);
			}
			v.adjList = v.adjList.next;
		}
		
	}
	
	public static void dfsWrapper(boolean[] visited)
	{
		ArrayList<String> results = new ArrayList<String>();
		for(int i=0;i<friends.length;i++)   
		{
			Vertex v = friends[i];
			if(visited[i] == false)
			{
				globalDfsNum = 0;
				startPt = i;
				dfs(v, visited, results);
			}
		
		}
		
		
		System.out.print("Connectors: ");
		for(int i=0;i<results.size();i++)
		{
			System.out.print(results.get(i));
			if(i!=results.size()-1)
			{
				System.out.print(", ");
			}
		}
		System.out.println();
	}
	
	public static int neighborCount(Vertex v)
	{
		Neighbor w = v.adjList;
		int i=0;
		while(w!=null)
		{
			i++;
			w=w.next;
		}
		return i;
	}
	
	
	
	
	public static void toString(Vertex x)
	{
		System.out.println( x.name + " | "+ x.isStudent + " | "+ x.school );
	}
	
	public static int indexForName(String name) {
		 for (int v=0; v < friends.length; v++) {
			 if (friends[v].name.equals(name)) {
				 return v;
			 }
		 }
		 return -1;
	 }

	
	public static void printGraph(graph x)
	{
		System.out.println();
		for(int i=0;i<x.friends.length;i++)
		{
			System.out.print(x.friends[i].name + ": Connected to ");
			
			Neighbor curr = x.friends[i].adjList;
			while(curr!=null)
			{
				System.out.print(x.friends[curr.vertexNum]+ " -- ");
				curr=curr.next;
			}
			System.out.println();
		}
		
	}
	
	public static void killBlanks(ArrayList<ArrayList<Vertex>> rem)
	{
		int i = 0;
		while(!rem.isEmpty() && i < rem.size()){
			if(rem.get(i).size()==0)
			{ 
				rem.remove(i);
			}else {
				i++;
			}
		}
		
		
	}
}


