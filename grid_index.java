import java.util.ArrayList;

public class grid_index {
	private ArrayList<Node> node_list=new ArrayList<Node>();    //list of nodes in the tree
	private int columns_number;
	private int rows_number;
	private String[][] grid;                                    //grid of names of nodes
	private String[] table;										//table of "+" operation off all nodes in same row index in grid variable
	private Node[][] nodes_grid;								//grid of nodes
	private int nodes_count;									//number of counts
	
	
	
	public grid_index(int row,int col,ArrayList<Node> n ) {    //constructor
		rows_number=row;
		columns_number=col;
		grid=new String[row][col];
		nodes_grid=new Node[row][col];
		table=new String[rows_number];
		for(int i=0;i<rows_number;i++)
			table[i]="";
		node_list=n;
		nodes_count=0;
	}
	
	public void Set_node_list(ArrayList<Node> n,int height) {    //set a new node list after insert query
		node_list=n;
		if(height!=columns_number)
			columns_number=height;
		rows_number++;
		grid=new String[rows_number][columns_number];
		nodes_grid=new Node[rows_number][columns_number];
		table=new String[rows_number];
		for(int i=0;i<rows_number;i++)
			table[i]="";
		Builg_grid();
		Build_grid_nodes();
	}
	
	public void Builg_grid() {                                //this method initializing a grid of node strings
		String s[]=new String[columns_number];
		int counter=0;
		for(int i=0;i<rows_number;i++) {
			counter=0;
			for(int j=0;j<columns_number;j++)
				s[j]="";
			s[counter]=node_list.get(i).get_Name();
			Node n=node_list.get(i);
			while(n.get_parent()!=null) {
				counter++;
				s[counter]=n.get_parent_name();
				n=n.get_parent();
			}
			for(int j=0;j<=counter;j++)
				if(s[counter-j]!=null)
					grid[i][j]=s[counter-j];
			for(int j=counter+1;j<columns_number;j++) 
				grid[i][j]=s[j];
		}
		build_table();
	}
	
	
	public void Build_grid_nodes() {                         //this method is initializing grid of nodes
		Node s[]=new Node[columns_number];
		int counter=0;
		for(int i=0;i<rows_number;i++) {
			counter=0;
			for(int j=0;j<columns_number;j++)
				s[j]=null;
			Node n=node_list.get(i);
			s[counter]=n;
			while(n.get_parent()!=null) {
				n=n.get_parent();
				counter++;
				s[counter]=n;
				
			}
			for(int j=0;j<=counter;j++) {
				if(s[counter-j]!=null)
					nodes_grid[i][j]=s[counter-j];
			}
			for(int j=counter+1;j<columns_number;j++) 
				nodes_grid[i][j]=s[j];
		}
	}
	
	public void build_table() {                               //initializing a table variable
		
		for(int i=0;i<rows_number;i++) {
			for(int j=0;j<columns_number;j++) {
				table[i]+=grid[i][j];
			}
		}
	}
	
	public String search_point(String query) {            //query of searching one point when the query is 'search <not>'

		int index=-1;
		for(int i=0;i<rows_number;i++) {
//			if(table[i].startsWith(query+null)) {
			if(table[i].endsWith(query)) {
				index=i;
				print_way_search_point(index);
			}
		}
		return index!=-1 ? "":"Cant find nothing";
	}
	
	public String search_point2(String query) {            //query of searching one point when the query its 'search not<node>'

		int index=-1;
		for(int i=0;i<rows_number;i++) {
//			if(table[i].startsWith(query+null)) {
			if(!table[i].contains(query)) {
				index=i;
				print_way_search_point(index);
			}
		}
		return index!=-1 ? "":"Cant find nothing";
	}
	
	public String search_range(String a,String b) {     //query of searching  range of points when query is 'search <note1>,<note2>'
		int index=-1;
		int startIndex=-1,endIndex=-1;
		for(int i=0;i<rows_number;i++) {   //searching the start and end index of the query nodes
			if(table[i].endsWith(a))
				startIndex=i;
			else if(table[i].endsWith(b))
				endIndex=i;
		}
		if(startIndex==-1 ||endIndex==-1)
			return "Cant find nothing";
		if(startIndex>endIndex) {   //the start index is higher than end index
			int temp=startIndex;
			startIndex=endIndex;
			endIndex=temp;
		}
		for(int i=startIndex;i<=endIndex;i++) {
				index=i;
				print_way_search_point(index);
		}
		return index!=-1 ? "":"Cant find nothing";
	}
	
	public void print_way_search_point(int index) {       //prints the nodes visited when found a point
		for(int i=index;i<index+1;i++) {
			for(int j=0;j<columns_number;j++) {
				if(nodes_grid[i][j]!=null)
					System.out.print(nodes_grid[i][j].get_Name()+" ---> ");
				else {
					System.out.println("null");
					return;
				}
				
			}
		}
	}

	
	public void print_grid() {                     //prints the nodes names grid
		for(int i=0;i<rows_number;i++) {
			for(int j=0;j<columns_number;j++)
				System.out.print(grid[i][j]+" ");
			System.out.println();
		}
		
	}
	
	public void print_numbered_grid() {                     //prints the nodes names grid with number of a row
		for(int i=0;i<rows_number;i++) {
			System.out.print(i+") ");
			for(int j=0;j<columns_number;j++)
				System.out.print(grid[i][j]+" ");
			System.out.println();
		}
		
	}
	
	public Node search_father(int index) {      //searching the node father by get the index of path in the table
		for(int j=0;j<columns_number;j++) {
			if(j+1==columns_number)
				return nodes_grid[index][j];
			else if(nodes_grid[index][j+1]==null)
				return nodes_grid[index][j];
		}
		return null;
	}
	
}
