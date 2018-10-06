
public class Node {
	private String name;				 //name of the node
	private Node nodes[];				 //all the children's of this node
	private int node_counter;            //number of children for this node
	private String data;                 //text
	
	
	public Node(String n) {  //constructor
		node_counter=0;
		name=n;
	
		System.out.println("Node "+name+" created ");
	}
	
	public Node(String n,String d) {  //constructor
		node_counter=0;
		name=n;
		data=d;
		System.out.println("Node "+"'"+name+"'"+" inserted. his data is:  "+ data);
	}
	
	public int getNode_counter() {
		return node_counter;
	}
	
	public void Add_node(Node n) {    //adding new child to node
		node_counter++;
		if(node_counter==1) {           //if its first children
			nodes=new Node[node_counter];
			nodes[node_counter-1]=n;
		}
		else {
			Node temp[]=new Node[node_counter];
			for(int i=0;i<node_counter-1;i++) {
				temp[i]=nodes[i];
			}
			temp[node_counter-1]=n;
			nodes=new Node[node_counter];
			for(int i=0;i<node_counter;i++) {
				nodes[i]=temp[i];
			}
		}
		System.out.println("Node "+"'"+n.name+"'"+ " added to: "+"'"+name+"'"+ " node.");
	}
	
	public String toString() {
		return "Node name: "+name+printArray();
	}
	
	public String printArray() {
		String s="";
		for(int i=0;i<node_counter;i++) {

			s+=" "+(i+1)+") "+nodes[i].name+ ".";
		}
		return s.length()!=0 ? ", leafes- "+s : ", no leafes in this node";

	}
}
