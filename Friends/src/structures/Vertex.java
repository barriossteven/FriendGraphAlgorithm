package structures;

public class Vertex implements Cloneable {
	
	public String name;
	public Neighbor adjList;
	boolean isStudent;
	public String school;
	
	public int dfsNum;
	public int back;
	
	
	public Vertex(String name, Neighbor neighbors, boolean isStudent, String school)
	{
		this.name = name;
		this.adjList = neighbors;
	}
	
	public String toString()
	{
		if(isStudent == false)
		{
			return this.name + "|n";
		}
		
		return this.name + "|y|"+ this.school;

		
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
		
	}
	
}
