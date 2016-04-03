package structures;

public class Neighbor implements Cloneable{
	
	public int vertexNum;
	public Neighbor next;
	
	
	public Neighbor(int num, Neighbor n)
	{
		this.vertexNum = num;
		this.next = n;
	}

	public Object clone() throws CloneNotSupportedException{
		return super.clone();
		
	}
}
