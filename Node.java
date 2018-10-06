public class Node {
	private String name;				 //name of the node
	private Node nodes[]=null;				 //all the children's of this node
	private int node_counter;            //number of children for this node
	private String data;                 //text
	private Node parent=null;
	
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
	
	public int getNode_counter() { //getters
		return node_counter;
	}
	
	public Node[] get_nodes() {
		return nodes;
	}
	
	public String get_Name() {
		return name;
	}
	
	public Node get_parent() {
		return parent;
	}
	
	public String get_parent_name() {
		return parent!=null ? parent.get_Name() : "no parent";
	}
	
	public void set_parent(Node n) {  //set a node father
		parent=n;
	}
	
	public void Add_node(Node n) {    //adding new child to node
		node_counter++;
		if(node_counter==1) {           //if its first children
			nodes=new Node[node_counter];
			nodes[node_counter-1]=n;
		}
		else {
			Node temp[]=new Node[node_counter];    //need to save old data before allocate new array with new size
			for(int i=0;i<node_counter-1;i++) {    //copying old data
				temp[i]=nodes[i];
			}
			temp[node_counter-1]=n;               //adding new node to temp array
			nodes=new Node[node_counter];         
			for(int i=0;i<node_counter;i++) {     //copying back to nodes array
				nodes[i]=temp[i];
			}
		}
		System.out.println("Node "+"'"+n.name+"'"+ " added to: "+"'"+name+"'"+ " node.");
	}

	public String toString() {                   //to string method override
		return "Node name: "+name+printArray();
	}
	
	public boolean haveChildrens() {               //boolean function checks if a node have children's or no. if nodes array not null->return true
		return nodes!=null;
	}
	
	public String printArray() {                   //method which helps to tostring to print details about childrens
		String s="";
		for(int i=0;i<node_counter;i++) {

			s+=" "+(i+1)+") "+nodes[i].name+ ".";
		}
		return s.length()!=0 ? ", childrens- "+s : ", no childrens in this node";

	}

}
