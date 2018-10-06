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
	public static ArrayList<Node> nodes1=new ArrayList<Node>();

	public static void main(String[] args) 
	{
		String[] all_the_text;
		int line_counter=0;
		String tag_name;
		String closing_tag="";
		String tag_name1;
		String closing_tag1;
		String starting_tag="";
		String starting_tag1="";
		String text;
		String data="";
		Node node=null,n1=null,node2;
		Path file= Paths.get("C:\\Users\\Maria\\Desktop\\2.txt");
		try (InputStream in = Files.newInputStream(file);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			    	line_counter++;
			    }
		} catch (IOException x) {
		    System.err.println(x);
		}
		all_the_text=new String[line_counter];
		line_counter=0;
		try (InputStream in = Files.newInputStream(file);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			    	all_the_text[line_counter]=line.toString();
			    	line_counter++;
			    }
			    
		} catch (IOException x) {
		    System.err.println(x);
		}
		
		
		for(String a : all_the_text) {
			System.out.println(a);
		}
		//System.out.println(all_the_text);
		tag_name=all_the_text[0].substring(all_the_text[0].indexOf("<")+1,all_the_text[0].indexOf(">"));
		System.out.println("Tag name: "+tag_name);
		starting_tag="<"+tag_name+">";
		closing_tag="</"+tag_name+">";
		if(all_the_text[0].equals(starting_tag))
			data="";
		else if(all_the_text[0].contains(closing_tag))
			data=all_the_text[0].substring(all_the_text[0].indexOf(">")+1, all_the_text[0].indexOf("</"));
		else
			data=all_the_text[0].substring(all_the_text[0].indexOf(">")+1);
		node=new Node(tag_name,data);

		nodes1.add(node);
		closing_tag1=closing_tag;
		for(int i=1;i<line_counter;i++) {
			if(!all_the_text[i].contains(closing_tag) && !all_the_text[i].contains(closing_tag1)) {
				tag_name1=all_the_text[i].substring(all_the_text[i].indexOf("<")+1,all_the_text[i].indexOf(">"));
				closing_tag1="</"+tag_name1+">";
				starting_tag1="<"+tag_name1+">";
				if(all_the_text[i].equals(starting_tag1))
					data="";
				else if(all_the_text[i].contains(closing_tag1))
					data=all_the_text[i].substring(all_the_text[i].indexOf(">")+1, all_the_text[i].indexOf("</"));
				else
					data=all_the_text[i].substring(all_the_text[i].indexOf(">")+1);
				n1=new Node(tag_name1,data);
				add_to_list(n1);
				node.Add_node(n1);
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
		
	System.out.println("This is all the nodes: ");	
	for(Node n: nodes1) {
		System.out.println(n);
	}
	
	grid_index index;
	for(Node n: nodes1) {
	//	grid.addNode(n);
	}
	}

	

	public static int add_nodes(String[] s, int i,int size, String closing_tag, Node n) {
			if(!s[i].contains(closing_tag) && !s[i].startsWith("</")) {
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
				Node n2=new Node(tag_name2,data);
				n.Add_node(n2);
				add_to_list(n2);
				return 1+add_nodes(s, i+1,size, closing_tag2,  n2);
			}
			else if(s[i].contains(closing_tag) || (s[i].startsWith("</") && !s[i].contains(closing_tag))) {
				return 0;
			}
			else return 1;

		}
	
	public static void add_to_list(Node n) {
		nodes1.add(n);
	}
}
	





