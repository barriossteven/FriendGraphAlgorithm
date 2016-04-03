package apps;
//Steven Barrios and Daniel Louie
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
//buffer reader
import java.io.BufferedInputStream;
import java.lang.Object;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
//up





import structures.Neighbor;
import structures.Queue;
import structures.Stack;
import structures.Vertex;
import structures.graph;

public class Friends {

	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException
	{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in).useDelimiter("");
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of friends file.");
		
		graph friendGraph;
		String file = "";
		while(true)
		{
			file = input.next();
			try{
				friendGraph = new graph(file);
				break;
			}
			catch(Exception e){
				System.out.println("File does not exist. Try again.");
			}
		}
		
		
		while(true)
		{
			System.out.println();
			System.out.println("(1) -- Find shortest intro chain");
			System.out.println("(2) -- Find cliques");
			System.out.println("(3) -- Find connectors");
			System.out.println("(4) -- Quit");
			System.out.println();
			System.out.println("Enter 1, 2, 3, or 4");
			//System.out.println(friendGraph.friend.get)
			
			int x = input.nextInt();
			
			while(x<1 || x>4)
			{
				System.out.println("You must enter a number from 1-4");
				x = input.nextInt();
			}
			
			if(x==1)
			{	
				
				System.out.println("Enter the name of friend 1.");
				String p1 = input.next().toLowerCase();
				System.out.println("Enter the name of friend 2.");
				String p2 = input.next().toLowerCase();
				
				friendGraph = BuildMethod(file);
				bfsPath(friendGraph, p1, p2);
				System.out.println();
				continue;
			}
			
			if(x==2)
			{
				System.out.println("Enter the name of school.");
				String s1=sc.nextLine().toLowerCase();
				friendGraph = BuildMethod(file);
				
				/*try {
					s1 = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		
				structures.graph.cliqueWrap(friendGraph, s1);
				
				//filter out clique depending on given school
				
			}
			
			if(x==3)
			{	
				friendGraph = BuildMethod(file);
				boolean[] visited = new boolean[friendGraph.friends.length];
				graph.dfsWrapper(visited);
				System.out.println();
				
			}
			if(x==4)
			{
				System.exit(0);
			}
			
		}
	}
	
	
	

	
	
	public static void bfsPath(graph friendGraph, String vertexOne, String vertexTwo)
	{
		if((indexForName(vertexOne, friendGraph) == -1) || (indexForName(vertexTwo, friendGraph) == -1))
		{
			System.out.println("One name does not exist in friend graph");
			return;
		}
		if(vertexOne.equals(vertexTwo))
		{
			System.out.println("Entered the same names");
			return;
		}
		int friendLength = friendGraph.friends.length;
		Vertex[] friends = friendGraph.friends;
		boolean[] visited = new boolean[friendLength];
		Vertex[] prevs = new Vertex[friendLength];
		initialize(visited, prevs);
		Queue<Integer> tem = new Queue<Integer>();
		
		int vertexNum = indexForName(vertexOne, friendGraph);
		tem.enqueue(vertexNum);
		visited[vertexNum] = true;
		
		
	    
		while(!tem.isEmpty())
		{
			Vertex v = friends[tem.dequeue()];
			Neighbor curr = v.adjList;
			while(curr!=null)
			{
				if(visited[curr.vertexNum]==false)
				{
					visited[curr.vertexNum] = true;
					prevs[curr.vertexNum] = v;
					tem.enqueue(curr.vertexNum);
				}
				curr=curr.next;
			}
		}
		
		System.out.println();
		
		int endNum = indexForName(vertexTwo, friendGraph);
		
		int prev = endNum;
		Stack<String> result = new Stack<String>();
		result.push(friends[endNum].name);

		
		while(true)
		{
			if(prevs[prev]==null)
			{
				break;
			}
			Vertex f = prevs[prev];
			result.push(f.name);
			
			int index = indexForName(f.name,friendGraph);
			prev = index;
		}
		
		
		if(result.size()==1)
		{
			System.out.println("No path between friends");
			return;
		}
		
		System.out.println("Shortest path between " + vertexOne + " and " + vertexTwo + ":");
		while(!result.isEmpty())
		{
			System.out.print(result.pop());
			
			if(!result.isEmpty())
			{
				System.out.print(" - ");
			}
			
		}
		System.out.println();
		
		
	}
	public static graph BuildMethod(String file) throws FileNotFoundException{
		graph temp = new graph(file);
		return temp;
	}
	      

	
	public static void initialize(boolean[] arr1, Vertex[] arr2)
	{
		for(int i=0;i<arr1.length;i++)
		{
			arr1[i] = false;
		}
		for(int i=0;i<arr2.length;i++)
		{
			arr2[i] = null;
		}
	}
	
	public static int indexForName(String name, graph friends) {
		 for (int v=0; v < friends.friends.length; v++) {
			 if (friends.friends[v].name.equals(name)) {
				 return v;
			 }
		 }
		 return -1;
	 }
	
	

	
	
	
	
	
	
}