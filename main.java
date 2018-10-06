import java.util.*;
import java.util.List;
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class main
{
	public static ArrayList<Node> nodes1=new ArrayList<Node>();   //list of all nodes

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		String[] all_the_text;
		int line_counter=0,counter=0;
		String tag_name;
		String closing_tag="";
		String tag_name1;
		String closing_tag1;
		String starting_tag="";
		String starting_tag1="";
		String text;
		String data="";
		Node node=null,n1=null,node2;
		
		
		JFileChooser chooser = new JFileChooser();
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	                "xml files", "xml");
	        chooser.setFileFilter(filter);
	        int returnVal = chooser.showOpenDialog(null);
	        if(returnVal == JFileChooser.APPROVE_OPTION) {
	            System.out.println("You chose to open this file: " +
	                    chooser.getSelectedFile().getName());
	        
	    Path file= Paths.get(chooser.getSelectedFile().getAbsolutePath());
		//Path file= Paths.get("C:\\Users\\Maria\\Desktop\\2.txt");
		try (InputStream in = Files.newInputStream(file);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			    	line_counter++;
			    }
		} catch (IOException x) {
		    System.err.println(x);
		}
		all_the_text=new String[line_counter];   //allocate memory for the data
		line_counter=0;
		try (InputStream in = Files.newInputStream(file);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			    	if(counter>=2) {
			    		all_the_text[line_counter]=line.toString();    //copy the line 
			    		line_counter++;
			    	}
			    	else {
			    		counter++;
			    	}
			    }
			    
		} catch (IOException x) {
		    System.err.println(x);
		}
		
	
		
		
		System.out.println("The data from the file: ");
		for(String a : all_the_text) {     //printing the xml file data
			System.out.println(a);
		}

		tag_name=all_the_text[0].substring(all_the_text[0].indexOf("<")+1,all_the_text[0].indexOf(">")); //creating a tree
		System.out.println("Tag name: "+tag_name);
		starting_tag="<"+tag_name+">";
		closing_tag="</"+tag_name+">";
		if(all_the_text[0].equals(starting_tag))
			data="";
		else if(all_the_text[0].contains(closing_tag))
			data=all_the_text[0].substring(all_the_text[0].indexOf(">")+1, all_the_text[0].indexOf("</"));
		else
			data=all_the_text[0].substring(all_the_text[0].indexOf(">")+1);
		node=new Node("<"+tag_name+">",data);
		//node=new Node(tag_name,data);
		node.set_parent(null);
		nodes1.add(node);
		closing_tag1=closing_tag;
		for(int i=1;i<line_counter;i++) {
			if(!all_the_text[i].contains(closing_tag) && !all_the_text[i].contains(closing_tag1) && !all_the_text[i].startsWith("</")) {
				tag_name1=all_the_text[i].substring(all_the_text[i].indexOf("<")+1,all_the_text[i].indexOf(">"));
				closing_tag1="</"+tag_name1+">";
				starting_tag1="<"+tag_name1+">";
				if(all_the_text[i].equals(starting_tag1))
					data="";
				else if(all_the_text[i].contains(closing_tag1))
					data=all_the_text[i].substring(all_the_text[i].indexOf(">")+1, all_the_text[i].indexOf("</"));
				else
					data=all_the_text[i].substring(all_the_text[i].indexOf(">")+1);
				n1=new Node("<"+tag_name1+">",data);
				//n1=new Node(tag_name1,data);
				add_to_list(n1);
				node.Add_node(n1);
				n1.set_parent(node);
				if(!all_the_text[i].contains(closing_tag1)) {
					i+=1;
					i+=add_nodes(all_the_text, i,line_counter, closing_tag1, n1);
				}
			}
			else {
				tag_name1=all_the_text[i].substring(all_the_text[i].indexOf("<")+1,all_the_text[i].indexOf(">"));
				closing_tag1="</"+tag_name1+">";
			}
			
		}
		
		System.out.println("This is all the nodes in thee first structure: ");	
		for(Node n: nodes1) {
			System.out.println(n+ "|| " + " Node "+n.get_Name()+" have childrens? "+n.haveChildrens()+"  ||" + n.get_Name() +" its parent: "+n.get_parent_name());
		}
	

		int height=getTreeHeight(node);    //check the tree height for the first time
		System.out.println("The tree height is: "+height);
		grid_index index=new grid_index(nodes1.size(),height,nodes1);   //create grid index
		index.Builg_grid();
		index.Build_grid_nodes();
		index.print_grid();
		
		
		System.out.println("Please enter search/insert,'exit' to end ");   //start running queues
		String string=sc.nextLine();
		while(!string.equals("exit")){
			if(string.equals("search") || string.equals("Search") || string.equals("SEARCH")) {   //if search
				System.out.println("Please enter one of the words: point/range");
				String search_type=sc.nextLine();
				if(search_type.equals("point")) {
					System.out.println("Please enter you search query- for example '<node>'(for all 'node' nodes) , or '!<node>'(for all not 'node' nodes)");
					String str=sc.nextLine();
					if(!str.contains("!"))
						System.out.println(index.search_point(str));
					else
						System.out.println(index.search_point2(str.substring(str.indexOf("!")+1)));
				}
				else if(search_type.equals("range")) {
					System.out.println("Please enter your query in this format: '<node1>,<node2>' - it will find all the nodes that equals to node1 and node2 and all the nodes between them.");
					String range_query=sc.nextLine();
					System.out.println(index.search_range(range_query.substring(range_query.indexOf("<"),range_query.indexOf(",")), range_query.substring(range_query.indexOf(",")+1)));
					
				}

			}  //end search if
			else if(string.equals("insert") || string.equals("Insert") || string.equals("INSERT")) {    //if insert 
				System.out.println("Please enter your insert query in this format: <tag name> data(data is not required)");
				String str=sc.nextLine();
				tag_name=str.substring(str.indexOf("<")+1, str.indexOf(">"));
				starting_tag="<"+tag_name+">";
				if(str.equals(starting_tag))
					data="";
				else
					data=str.substring(str.indexOf("<")+1);
				//Node newNode=new Node(tag_name,data);
				Node newNode=new Node("<"+tag_name+">",data);
				
//				System.out.println("This is the list of exsisting nodes,choose the father for your node-and write its name below, null to set new root");
//				for(Node n: nodes1) {
//					System.out.println(n.get_Name());
//				}
				add_to_list(newNode);
				System.out.println(("This is all the paths, choose the number of the path in which bottom  you want to place your node, null to set a new root"));
				index.print_numbered_grid();
				str=sc.nextLine();
				if(str.equals("null")){             //if the user want to set new node
					newNode.set_parent(null);
					newNode.Add_node(node);
					node.set_parent(newNode);
					node=newNode;
					height=getTreeHeight(node);
					System.out.println("The tree height is: "+height);
					index.Set_node_list(nodes1, height);
				}
				else {     //if user choose the path where insert new node
					Node father=index.search_father(Integer.parseInt(str));
					father.Add_node(newNode);
					newNode.set_parent(father);
					height=getTreeHeight(node);
					System.out.println("The tree height is: "+height);
					index.Set_node_list(nodes1, height);
					}

				
				System.out.println("The new Structure of the tree: ");
				for(Node n: nodes1) {
					System.out.println(n+ "|| " + " Node "+n.get_Name()+" have childrens? "+n.haveChildrens()+"  ||" + n.get_Name() +" its parent: "+n.get_parent_name());
				}
			} //end insert else if
			
			System.out.println("Please enter search/insert,'exit' to end ");
			string=sc.nextLine();
		} //end while
		System.out.println("Have a nice day");
		
	    }     //end file chooser
}


	public static int add_nodes(String[] s, int i,int size, String closing_tag, Node n) {   //this function helping to add recursively childrens
			if(!s[i].contains(closing_tag) && !s[i].startsWith("</")) {  //need to create a new inner node
				String tag_name2=s[i].substring(s[i].indexOf("<")+1,s[i].indexOf(">"));
				String closing_tag2="</"+tag_name2+">";
				String starting_tag2="<"+tag_name2+">";
				String data="";
				if(s[i].equals(starting_tag2))
					data="";
				else if(s[i].contains(closing_tag2))
					data=s[i].substring(s[i].indexOf(">")+1, s[i].indexOf("</"));
				else
					data=s[i].substring(s[i].indexOf(">")+1);
				Node n2=new Node("<"+tag_name2+">",data);
				//Node n2=new Node(tag_name2,data);
				n.Add_node(n2);
				add_to_list(n2);
				n2.set_parent(n);
				return 1+add_nodes(s, i+1,size, closing_tag2,  n2);  //recursive call and promotion index by 1
			}
			else if(s[i].contains(closing_tag) || (s[i].startsWith("</") && !s[i].contains(closing_tag))) { 
				return 0;
			}
			else return 1; //it checked the line and need to return to main and promote index by 1

		}
	
	public static void add_to_list(Node n) {    //adding to nodes list
		nodes1.add(n);
	}
	
	
	
	public static int getTreeHeight(Node root) {    //this function calculates and returns a height of a tree
		int height=0;
		if(root==null)
			return height;
		if(!root.haveChildrens())
			return 1;
		for(Node n: root.get_nodes())
			height=Math.max(height, getTreeHeight(n));
		return height+1;
	}
	
	public static void Insert_nodes(String str) {
		
	}
	
}
	
