package AStar;
import java.util.ArrayList;
import java.util.Scanner;

class State
{
	int arr[];
	int f=0,g=0;
	State(State s)
	{
		this.arr = new int[9];
		for(int i=0;i<9;i++)
			this.arr[i]=s.arr[i];
	}
	
	State()
	{
		this.arr = new int[9];
	}
}

public class Puzzleastar 
{
	State startstate,currentstate,goalstate;
	ArrayList<State> openlist = new ArrayList<State>();
	ArrayList<State> closelist = new ArrayList<State>();
	int cnt=0;
	int last=0;
	Puzzleastar()
	{
		startstate = new State();
		currentstate = new State();
		goalstate = new State();
	}
	
	public void input()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter start state:");
		for(int i=0;i<9;i++)
			startstate.arr[i]=scan.nextInt();
		System.out.println("Enter goal state:");
		for(int i=0;i<9;i++)
			goalstate.arr[i]=scan.nextInt();
		
		System.out.println("Start States");
		display(startstate);
		System.out.println("Goal State");
		display(goalstate);
	}
	public int lowest()
	{
		int min=openlist.get(0).f;
		int p=0;
		for(int i=1;i<openlist.size();i++)
		{
			if(min>openlist.get(i).f)
			{
				min=openlist.get(i).f;
				p=i;
			}
		}
		return p;	
	}
	
	public boolean inclose(State s)
	{	
		for(int i=0;i<closelist.size();i++)
		{
			if(issame(closelist.get(i),s))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean issame(State r,State s)
	{
		for(int i=0;i<9;i++)
		{
			if(r.arr[i]!=s.arr[i])
			{
				return false;
			}
		}
		return true;
	}

	public void display(State s)
	{
		for(int i=0;i<9;i++)
		{
			if(i==3 || i==6 || i==9)
				System.out.println();
			System.out.print(" "+s.arr[i]);
		}
		System.out.println();
		System.out.println("Heuristic values : g : "+s.g+" h : "+(s.f-s.g));
		System.out.println();
	}
	public void move(State s)
	{
		System.out.println("In Move \n------------------------------------------");
		State ns;
		int p=0;
		for(int i=0;i<9;i++)
			if(s.arr[i]==0)
			{
				p=i;
				break;
			}
		
		if(p>2)
		{
			ns = new State(s);
			ns.arr[p]=ns.arr[p-3];
			ns.arr[p-3]=0;
			ns.g=s.g+1;
			ns.f=ns.g+heuristic(ns);
			display(ns);
			if(!inclose(ns))
				openlist.add(ns);
		}
		
		if(p%3!=0)
		{
			ns = new State(s);
			ns.arr[p]=ns.arr[p-1];
			ns.arr[p-1]=0;
			ns.g=s.g+1;
			ns.f=ns.g+heuristic(ns);
			display(ns);
			if(!inclose(ns))
				openlist.add(ns);
		}
		
		if(p%3!=2)
		{
			ns = new State(s);
			ns.arr[p]=ns.arr[p+1];
			ns.arr[p+1]=0;
			ns.g=s.g+1;
			ns.f=ns.g+heuristic(ns);
			display(ns);
			if(!inclose(ns))
				openlist.add(ns);
		}
		
		if(p<6)
		{
			ns = new State(s);
			ns.arr[p]=ns.arr[p+3];
			ns.arr[p+3]=0;
			ns.g=s.g+1;
			ns.f=ns.g+heuristic(ns);
			display(ns);
			if(!inclose(ns))
				openlist.add(ns);
		}
		System.out.println("------------------------------------------1");
		
	}
	
	public int heuristic(State state1)
	{
		int heu_value=0;
		for(int i=0;i<9;i++)
			if(state1.arr[i]!=goalstate.arr[i] && state1.arr[i]!=0)
				heu_value++;
		return heu_value;
	}
	
	public void solvePuzzle()
	{
		startstate.f = heuristic(startstate);
		// System.out.println("Heuristic value :: "+startstate.f);
		// ADD to list
		last = startstate.f;
		openlist.add(startstate);
		int i=0,low=0;
//		while(i<1)
//		{
//			display(currentstate);
//			System.out.println();
//			move(currentstate);
//			i++;
//		}
		while(i<7)
		{
			low = lowest();
			currentstate = openlist.get(low);
			System.out.println("Printing Current State");
			display(currentstate);
			System.out.println();
			openlist.clear();
			closelist.add(currentstate);
			
			if(heuristic(currentstate)==0)
			{
				System.out.println("Goal state reached!!");
				break;
			}			
			move(currentstate);
			i++;
		}
	}
	
	public static void main(String args[])
	{
		Puzzleastar obj = new Puzzleastar();
		obj.input();
		obj.solvePuzzle();
	}
}
