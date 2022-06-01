/**
 *
 * @author Kola Ladipo
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Stream;



class Flyer{
	public String name;
	public int priority;
	      
	// A parameterized Flyer constructor
	public Flyer(String name, int priority) {
		this.name = name;
		this.priority = priority;
		}
	}

class Flyercompare implements Comparator<Flyer>{
    
    // Comparator for Flyer object
    public int compare(Flyer o1, Flyer o2) {
    	return o2.priority - o1.priority;
        }
    }


public class NUA_Upgrade_System {

	/* Use default constructor */

	PriorityQueue<Flyer> flyer = new PriorityQueue<>(new Flyercompare());
	HashMap<Integer, Flyer> waitlist = new HashMap<>();
	ArrayList <String> name = new ArrayList<>();
	HashMap<Integer, String> getName = new HashMap<>();
	
	private int locator;
	boolean transferred = false;
	boolean getNamed = false;
	
	void addFlyer(String name, int priority) {
		flyer.add(new Flyer(name, priority));
		transferred = false;
	}

	
	// Copies Priority to HashMap
	void dumpListToMap() {
		// to iterate over the content of the priority queue
		Iterator<Flyer> iterator = flyer.iterator();
		while(iterator.hasNext()) {
			name.add(flyer.peek().name);
			waitlist.put(locator++, flyer.poll());

		}
		transferred = true;
	}
	
	// Unused Method to print current reservation
	void printFlyerList() {
		getNameUtil();
		for(Map.Entry<Integer, String> t: getName.entrySet()) {
			print(t.getKey() + " " + t.getValue());
		}
	}
	
	// removes flyer from reservation
	void removeFlyer(String str) {
		if(waitlist.isEmpty()) return;
		else if(!waitlist.containsKey(removeUtil(str))) {
			System.out.println(str + " is not on waitlist");
			}
		else {
			int index = removeUtil(str);
			waitlist.remove(index);
			getName.remove(index);
		}
	}
	
	// utility used by removeFlyer method
	private int removeUtil(String str) {
		int counter = -1;
		for (int i = 0; i < name.size(); i++) {
			if(str.equals(name.get(i)))
				counter = i;	
		} 
		return counter;
	}

	
	private void getNameUtil() {
		for(Map.Entry<Integer, Flyer> s: waitlist.entrySet()) {
			getName.put(s.getValue().priority, s.getValue().name);
		}
		getNamed = true;
	}
	
	// method used to print the k-highest flyers
	public void KHighest( int k){
		getNameUtil();
		
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
		for(Map.Entry<Integer, Flyer> j: waitlist.entrySet()) {
			maxHeap.add(j.getValue().priority);
		}
		
		for (int i = 0; i < k; i++) {
            System.out.println(maxHeap.peek() + " " + getName.get(maxHeap.peek()));
            maxHeap.poll();
            }
        }
	
	
	// returns size of waitlist
	int size() {
		return waitlist.size();
	}
	
	
	
	// Main function
	public static void main(String[] args) {
		NUA_Upgrade_System hp = new NUA_Upgrade_System();
		
		hp.addFlyer("Alexander", 70);
		hp.addFlyer("Kim Scott", 95);
		hp.addFlyer("Michael Young", 13);
		hp.addFlyer("James Costa", 38);
		hp.addFlyer("Mary", 75);
		hp.addFlyer("Brianna", 8);
		hp.dumpListToMap();
		printMenu2();
		Menu(hp);
		
	}
	
	public static char Menu(NUA_Upgrade_System hp) {
		Scanner scnr = new Scanner(System.in);
		String name;
		int priority;
		
		
		while(true) {
			print("Enter option:");
			char ch = scnr.next().charAt(0);
			scnr.nextLine();
			
			// Request Upgrade
			if(ch == 'r' || ch == 'R'){
				print("Enter name");
				name = scnr.nextLine();
				print("Enter Priority");
				priority = scnr.nextInt();
				hp.addFlyer(name, priority);
				printMenu2();
				}

			// Cancel Upgrade
			else if(ch == 'c' || ch == 'C') {
				if(!hp.transferred) hp.dumpListToMap();
				if(hp.waitlist.isEmpty()) print("List is empty");
				else {
					print("Enter Flyer Name for Cancellation: ");
					String str = scnr.nextLine();
					hp.removeFlyer(str);
					print("");
					printMenu2();
					}
				}
				
			// Displays k-highest priority flyer
			else if(ch == 'd' || ch == 'D') {
				if(!hp.transferred) { hp.dumpListToMap();}
				print("Enter value for k (between 1 and " +hp.size() + "):");
				int k = scnr.nextInt();
				if(k > hp.size() || k < 1) {
					print("Incorrect value!");
					}
				else {
					print("** Top " + k + " Highest Priority Flyers **");
					hp.KHighest(k);
					}			
				print("");
				printMenu2();
				}
				
			else if(ch == 'q' || ch == 'Q') {
				System.exit(0);
				}
			}
		}

	
	static void printMenu2() {
		print("\t\t---- MENU ----");
		print("r - Request Upgrade \t\t d - Display Options");
		print("c - Cancel Upgrade \t\t q - Quit");
	}
	
	
	static void print(String str) {
		System.out.println(str);
	}
	
}
