import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Projectv4 {

	// Constants
	// constant for facility states 
	public static int fclose = 0;
	public static int ftentative = 1;
	public static int fopen = 2;

	//constants for client-facility connections
	public static int clientNotConnected = 0 ;
	public static int clientConnected = 1;
	
	
	public static int startTime;

	//input
	public static int numOfClients;
	public static int numOfFacilities;
	
	public static List<String> listFacilities;
	public static List<String> listClients;	

	//states	
	public static String stateClosed;
	public static String stateTentative;
	public static String stateOpen;

	// fixed cost fi
	public static List<Integer> listFixedCost;
	
	// Vj
	public static int[] costPaidByCustomer;
	
	// Cij
	public static int[][] listDistanceFromClient;
	
	// Wij
	public static int[][] wij;
	
	// for user input
	public static String answerScan;
	
	// for the while loop
	public static boolean test;
	
	// for storing frozen values of Vj and Wij 
	public static List<Integer> listFrozenVj;	
	public static int[][] arrayFrozenWij;
	
	// initial results of tentatively open facilities
	public static List<String> tentativelyOpenFacilities; //unique list of tent.openfacilities
	public static List<Integer> listTentativeFacilityNo;
	
	// results after opening facilities from the set of tentatively open facilities
	public static List<String> openFacilities;
	
	// connections after tentatively opening facilities, rows are facilities, columns are clients
	public static int[][] connectedFacilityClient;
	
	// connections after actually opening facilities from maximal stable set
	public static int[][] finalConnections;
	
	public static List<Integer> vjHigherThanCij;
	public static List<Integer> listColumns;
	public static int[] columns;
	public static int[] distinctArray;
	
	
	// for parsing the input
	private static Network network;
	public static int[][] arrayOfCij;
	
	
	
	public static void networkData(String [] graphContent) {

		network = new Network(graphContent);
		arrayOfCij = network.getCijArray();
		numOfFacilities = network.getNumOfFacilities();		
		numOfClients = network.getNumOfClients();
	}
	
	public static void manualData()
	{

		// Manually enter the data here
		// Each element of the array is the Cij all clients pay for that facility
		
		
		// sample datas are commented
		//String[] graphCij = new String[]{"4 5 2 1 0 0", "1 1 2 1 2 4", "1 2 5 1 3 3 ", "2 1 2 1 3 7"};
		
		//String[] graphCij = new String[]{"4 5 2 1 0 0", "1 2 1 3 1 1", "1 2 1 1 2 3", "2 1 2 1 3 7"};
		
		//String[] graphCij = new String[]{"0 0 0 0 0 0", "1 2 1 3 1 0", "1 2 1 1 2 3", "2 1 2 1 3 7"};
		
		//String[] graphCij = new String[]{"10 10 10 10 10 10", "1 2 1 3 1 0", "1 2 1 1 2 3", "1 2 1 1 2 4"};
		
		
		//will tentatively open 3, actually open 1 facility, with fixed cost at 6 
		//String[] graphCij = new String[]{"10 10 10 10 10 10", "1 2 1 3 1 0", "1 2 1 1 2 3", "1 1 2 3 1 1"};
		
		//will tentatively open 3, actually open 1 facility, with fixed cost at 6 
		//String[] graphCij = new String[]{"10 10 10 10 10 10", "1 2 1 3 1 0", "1 2 1 1 2 3", "1 1 2 3 1 1"};
		
		//9 clients, 5 facilities, will tentatively open 2, actually open 1 facility, with fixed cost at 6 
		String[] graphCij = new String[]{"10 10 10 10 10 10 10 10 10", "1 2 1 3 1 0 1 1 1", "1 2 1 1 2 3 1 1 1", "1 1 2 3 1 1 3 1 1"
				, "1 1 2 3 1 1 1 1 1"};
		
		//will open 2 tentatively, and open 1 facility, fixed cost 6
		//String[] graphCij = new String[]{"10 10 10 10 10 10 10 10 10", "1 2 1 3 1 4 1 2 1", "1 2 1 3 2 3 1 4 2", "1 1 2 3 3 1 5 1 1"
		//				, "1 1 2 3 5 1 7 7 1"};
		
		//will tentatively open 2 facilities, open 1 facility, at fixed cost 5 
		//String[] graphCij = new String[]{"10 10 10 10 10", "1 2 1 6 1", "1 2 1 3 2", "1 1 2 3 3"};
		
		//fixed cost 7
		//String[] graphCij = new String[]{"10 10 10 10 10 10 10", "1 2 1 6 1 2 4", "1 2 1 3 2 6 5"};
		
		//fixed cost 7, tentatively open 2, open 1
		//String[] graphCij = new String[]{"10 10 10 10 10 10 10", "9 8 9 9 9 9 8", "9 8 9 9 7 8 9"};
		
		//will tentatively open 4 facilities, at fixed cost at 8, ??  check if it's in a conflicting pair before??
		//String[] graphCij = new String[]{"10 10 10 10 10 10 10 10", "9 8 9 9 9 9 8 9", "9 8 9 9 7 8 9 9", "9 8 9 9 7 8 9 9", "9 8 9 9 7 8 9 9"};
		
		networkData(graphCij);
			
		// Fixed cost fi
		listFixedCost = new ArrayList<Integer>();
		for ( int i = 0 ; i < numOfClients ; i ++ ) {
			listFixedCost.add(6);
		}
		
		
		// States of facilities
		stateTentative = "tentatively open";
		stateOpen = "open state";
		stateClosed = "closed state";
		
		
		listFacilities = new ArrayList<String>();
		
		for (int i=0; i< numOfFacilities; i++){
			listFacilities.add("f" + i);
		}
		System.out.println("Amount of facilities: " + listFacilities.size());
		
		
		listClients = new ArrayList<String>();
		for (int i=0; i< numOfClients; i++){
			listClients.add("client" + i);
		}
		System.out.println("Amount of clients: " + listClients.size());
			
		
		
		// Amount the customer pays for being served, Vj 
		costPaidByCustomer = new int[listClients.size()];

		
		// Set all dual variables to zero: Wij
		// Wij = amount that j offers to contribute to the opening of facility i 
		wij = new int[listFacilities.size()][listClients.size()];
		
		for ( int i = 0 ; i < listFacilities.size(); i++) {
			
			for(int j = 0 ; j < listClients.size(); j++ ) {
	
				wij[i][j] = 0;			
			}	
		}
		
		System.out.println("list cij print:");
		listDistanceFromClient = new int[listFacilities.size()][listClients.size()];
		listDistanceFromClient = arrayOfCij;
	   
	}
	
	public static void deployAlgorithm()
	{	
		
		// If the user input is yes, let the user enter data manually (this option isn't implemented yet)
		// If no, use the data input from the manualData method
		switch (answerScan)
	    {
	    
	    	case "y": enterInput();
	             break;
	    
	    	case "n": manualData();
	             break;

	    	default :
	             System.out.println("invalid choice")   ;
	             break;
	    }

		
		// Initialize facility state
		// All facilities are closed at first
		int[] arrayFacilityState = new int[numOfFacilities];
		for (int aa = 0 ; aa < listFacilities.size(); aa++) {
			
			arrayFacilityState[aa] = fclose;
			
		}	
		
		
		tentativelyOpenFacilities = new ArrayList<String>();
		listTentativeFacilityNo = new ArrayList<Integer>();
		
		connectedFacilityClient = new int[listFacilities.size()][listClients.size()];
		openFacilities = new ArrayList<String>();
		
		// Set all connections to none first
		for ( int i = 0 ; i < listFacilities.size(); i++) {
			for(int j = 0 ; j < listClients.size(); j++ ) {
				connectedFacilityClient[i][j] = clientNotConnected;			
			}	
		}
		
		// Set the boolean variable test to true for the do-while loop to execute
		// It will set to false when all customers are connected to at least one facility
		test = true;
		
		columns = new int[(listFacilities.size()-1)*(listClients.size()-1)];
		distinctArray = new int[(listFacilities.size()-1)*(listClients.size()-1)];
		listColumns = new ArrayList<Integer>();
		
		// Event 2 requires the sum of all Wij for the facility
		int[] sumOfWijForFacility = new int [listFacilities.size()];
		
		// List that stores all facilities that yields the condition Vj > Cij
		vjHigherThanCij = new ArrayList<Integer>();
		int[] vjHigherThanCijArray = new int [listClients.size()];
		int[] distinctvjHigherThanCijArray = new int[listClients.size()];
		
		// Storing frozen values 
		listFrozenVj = new ArrayList<Integer>();
		arrayFrozenWij = new int[listFacilities.size()][listClients.size()];
		
		
		
		// DO-WHILE LOOP STARTS HERE 
		
		// let t =0
		startTime = 0;
		 
		do {
			
			System.out.println("time = " + startTime);
			
			// With proceeding time t, all Vj are increased simultaneously
			// i.e. they are all equal to t until they are frozen and then remain constant until the end
			increaseVjIfNotFrozen();
			
			for (int i = 0 ; i < listFacilities.size() ; i++) {
				
				sumOfWijForFacility[i] = 0;
				vjHigherThanCij.clear();
				
				for(int j = 0 ; j < listClients.size() ; j++ ) {
		
					System.out.println("==== i , j: " + i + " , " + j + " : " + listDistanceFromClient[i][j]);
					System.out.println("v" + j + ":" + costPaidByCustomer[j] + " for facility" + i );
									
					// EVENT TYPE 1 : Vj = Cij and facility i not tentatively open 
					if (costPaidByCustomer[j] == listDistanceFromClient[i][j]) {
						if(arrayFacilityState[i] != ftentative ) {
							System.out.println("event vj = cij and state: not tentatively open");
							System.out.println("vj: " + costPaidByCustomer[j] + " cij: " + listDistanceFromClient[i][j]);
							System.out.println("---increase wij");
							
							//increasing wij after if not frozen check
							increaseWijIfNotFrozen(i,j);	
							
							System.out.println("the amount client " + j + " offers to pays for the opening of facility i " 
																	+ i + ": w" + i + j + "=="+ wij[i][j]);			
						}
					}	
					
					
					// EVENT TYPE 3: facility i is already tentatively open and Vj = Cij
					if (costPaidByCustomer[j] == listDistanceFromClient[i][j]) {
						if (arrayFacilityState[i] == ftentative ) {
							
							System.out.println("event vj = cij and state: tentatively open");
							System.out.println("vj: " + costPaidByCustomer[j] + " cij: " + listDistanceFromClient[i][j]);
							System.out.println("---connect i to j");
							connectedFacilityClient[i][j] = clientConnected;
							System.out.println("freezing v" + j + " at " + costPaidByCustomer[j]);
							if (listFrozenVj.contains(j)) {
								System.out.println("do not add to the list of frozen vj");
							}
							else {
								listFrozenVj.add(j);
							}
							
							System.out.println("connected facility " + i + " to client "+ j  + " :" + connectedFacilityClient[i][j] );
						}		
					}			
					
					
					sumOfWijForFacility[i] = sumOfWijForFacility[i] + wij[i][j];
					//System.out.println("sum of w" + i + "j : " + sumOfWijForFacility[i]);
					
				}
				
	
				// EVENT TYPE 2 sum of Wij = fi , 
				if (listFixedCost.get(i) == sumOfWijForFacility[i]) {
					// Tentatively open facility i, 	
					// For all connected customers with Vj > Cij, connect j to i
					// Freeze Vj and all Wij
					
					
					System.out.println("event fi  = sum of wij for facility" + i);
					System.out.println(listFixedCost.get(i)  + " = " + sumOfWijForFacility[i]);
					System.out.println("---tentatively open facility " + listFacilities.get(i));
					String facilityNoToOpen = listFacilities.get(i).substring(1);
					
				
					if (!tentativelyOpenFacilities.contains(facilityNoToOpen)) {
							tentativelyOpenFacilities.add(facilityNoToOpen);
							listTentativeFacilityNo.add(Integer.parseInt(facilityNoToOpen));
					}
					arrayFacilityState[i] = ftentative;
				
					System.out.println("connect facility " 
											+ listFacilities.get(i)
											+ " to all unconnected clients that yield vj > cij "
											+ " and at sum w" + i + "j= "
											+ sumOfWijForFacility[i]);
					
					// find clients who yield Vj > Cij condition
					for(int j = 0 ; j < listClients.size() ; j++) {
						if (costPaidByCustomer[j] >= listDistanceFromClient[i][j] ) {
							vjHigherThanCij.add(j);
							System.out.println("the customer " + j + " is added to the vjHigherThanCij list" );
						}
					}
					
					
					vjHigherThanCijArray = vjHigherThanCij.stream().mapToInt(Integer::intValue).toArray();
					System.out.println("print all customers that yield vj > cij " + Arrays.toString(vjHigherThanCijArray));
					distinctvjHigherThanCijArray = Arrays.stream(vjHigherThanCijArray).sorted().distinct().toArray();
					
					
					// Connect clients who yield vj > cij to the facility after tentatively opening facility
					for (int index = 0 ; index < distinctvjHigherThanCijArray.length ; index++) {
						
						
						// Connect unconnected customers to facility 
						if (connectedFacilityClient[i][distinctvjHigherThanCijArray[index]] == clientNotConnected) {
							connectedFacilityClient[i][distinctvjHigherThanCijArray[index]] = clientConnected;
						}
						
						else if (connectedFacilityClient[i][distinctvjHigherThanCijArray[index]] == clientConnected) {
							System.out.println("client already connected to a facility");
						}
						
						
						System.out.println("connectedFacilityClient state for facility" 
												+ i + " for client"  
												+ distinctvjHigherThanCijArray[index] + " = "
												+ connectedFacilityClient[i][distinctvjHigherThanCijArray[index]]);
						// Freeze vj
						if (listFrozenVj.contains(distinctvjHigherThanCijArray[index])) {
							System.out.println("v" + distinctvjHigherThanCijArray[index] + "is already frozen");
						}
						else {
							listFrozenVj.add(distinctvjHigherThanCijArray[index]);
							System.out.println("freezing v" 
									+ distinctvjHigherThanCijArray[index] 
									+ " at " 
									+ costPaidByCustomer[distinctvjHigherThanCijArray[index]]);
						}

						// Freeze Wij				
						if (arrayFrozenWij[i][distinctvjHigherThanCijArray[index]] == 1) {
							System.out.println("w" + i + distinctvjHigherThanCijArray[index] + " is already frozen");
						}
						else {
							arrayFrozenWij[i][distinctvjHigherThanCijArray[index]] = 1;
							System.out.println("freezing w" + i + distinctvjHigherThanCijArray[index] + " at " 
												+ wij[i][distinctvjHigherThanCijArray[index]]);
						}
			
					}					
					
				}
				
			}
			
			// Are all customers connected? 
			// For all j values connectedFacility should
			// be connected at least once
			
			keepConnectedClients();
			
			if (checkIfAllClientsConnected(distinctArray) == true) {
				System.out.println("equal!");
				System.out.println("All clients are connected");
				test = false;
				// all clients are connected, 
				// no more loops needed
			}
			System.out.println("connections at time " + startTime);
			getAllConnections();
			
			//
			startTime++;
			
		} while(test);
		
		System.out.println("final connections when all clients are connected");
		
		getAllConnections();	
		
		getTentativelyOpenFacilities();
		
		printWij();
		
		openFacilitiesFromTentativeSet();
		
		getTentativelyOpenFacilities();
		
		getOpenFacilities();

		assignmentOfConnections();
		
	}
	
	
	// For a client j, if there exists a facility i in openFacilities (maximal independent set I)  such that
	// Facility i was the connecting witness of j
	// Assign client j to facility i and declare client j DIRECTLY CONNECTED
	// Otherwise, if facility i is not in openFacilities be the connecting witness of j 
	//
	public static void assignmentOfConnections() {
		
		List<Integer> indirectlyConnectedClients = new ArrayList<Integer>();
		List<Integer> directlyConnectedClients = new ArrayList<Integer>();
		
		List<Integer> integerListOpenFacilities = new ArrayList<Integer>(); 
		
		System.out.println("assignment of connections: ");
		for (int n = 0 ; n < openFacilities.size(); n++) {
			integerListOpenFacilities.add(Integer.parseInt((openFacilities.get(n).substring(1))));
		}
		
		// Find the disjunction between  tentatively open facilities and open facilities 
		List<Integer> listDisjunction = new ArrayList(listTentativeFacilityNo);
		listDisjunction.removeAll(integerListOpenFacilities);

		/*
		for (int n = 0 ; n < listDisjunction.size(); n++) {
			System.out.println("disjunction: ");
			System.out.println(listDisjunction.get(n));
			
		}
		*/
		
		
		// For each customer j that is connected to a facility not element of maximal stable set,
		// Connect j to an open neighbor of i in the graph (V,E)
		// Declare client j indirectly connected
		//
		for (int i = 0 ; i < listDisjunction.size(); i++) {
			for (int k = 0 ; k < integerListOpenFacilities.size(); k++) {
				
				for (int j  = 0 ; j < listClients.size() ; j++) {
					
					if (connectedFacilityClient[listDisjunction.get(i)][j] == 1 
							&& connectedFacilityClient[integerListOpenFacilities.get(k)][j] == 0) {
								
								connectedFacilityClient[listDisjunction.get(i)][j] = 0;
								connectedFacilityClient[integerListOpenFacilities.get(k)][j] = 1;
								indirectlyConnectedClients.add(j);
					}
					else {
						directlyConnectedClients.add(j);
					}
										
				}
			}	
		}
		
		System.out.println("indirectly connected clients: ");
		System.out.println(indirectlyConnectedClients);
		
		System.out.println("directly connected clients: ");
		System.out.println(directlyConnectedClients);
		
	}
	
	// Pick a maximal independent set I of facilities from the graph by choosing facilities
	// in the order in which they were tentatively opened if they are not conflicting with a facility...
	// already chosen, and declare the set I of facilities open
	public static void openFacilitiesFromTentativeSet() {
		
		//int yieldsWijCondition  = 1;
		Set<Integer> setTentative = new HashSet<Integer>();
		//List<Integer> listFacilityNo = new ArrayList<Integer>();
		//int[] arrayFacilityNo = new int[tentativelyOpenFacilities.size()];
		
		
		setTentative.addAll(listTentativeFacilityNo);
		System.out.println("tentatively open facilities from setTentative: " + setTentative);
		System.out.println("size of setTentative: " + setTentative.size());
		int[] arrayUnique = new int[setTentative.size()];
		Iterator<Integer> itTentative = setTentative.iterator();
		int t=0;
		
		//turn the set of tentatively open facility ids to an array of int
		while(itTentative.hasNext()){
			arrayUnique[t] = itTentative.next();
			System.out.println("unique tentatively open facilities list : f" + arrayUnique[t]);
			t++;
		}
		
		// create a list of all possible combinations of tentatively open facilities: listE
		// create pairE for the pairs
		// check if they are conflicting
		// store them in conflictingPairs array
		// conflictingPairs assignment done
		// calculate which element of the conflicting pairs should be opened
		// store them in openFacilities
	
		System.out.println("unique array length: " + arrayUnique.length);
	    List<Integer> listE = new ArrayList<Integer>(); 
	    
	    //if there is only one facility that is tentatively open, dont do permutations, 
	    //open that one facility 
	    if (arrayUnique.length == 1) {
	    	listE.add(arrayUnique[0]);
	    	openFacilities.add("f"+arrayUnique[0]);
	    }
	    
	    //if there is more than one facility that is tentatively open, find all pairs
	    //store them in listE
	    else if (arrayUnique.length > 1) {
	        for(int j = 0; j < arrayUnique.length; j++) {
	        	for(int i = j+1; i < arrayUnique.length; i++) {
	        		
	        		listE.add(arrayUnique[j]);
	        		listE.add(arrayUnique[i]);
	        	}        	
	        }
	        System.out.println("list E " + listE);
  
	        // created a 2d array for conflicting pairs
	        int [][] conflictingPairs = new int[listE.size()][2];
	        

	        
	        // going through all pairs in listE to see if they conflict
	        for (int i = 0; i < listE.size(); i=i+2) {
	        	List<Integer> pairE = new ArrayList<Integer>();
	        	if(i%2 == 0) {
	        		pairE.add(listE.get(i));
	        		pairE.add(listE.get(i+1));
	        	}
	        	else if(i%2 == 1) {
	        		System.out.println("do not update pairE");
	        	}
	        	
	        	//all combinations of pairs from tentatively open facilities 
	        	System.out.println("new pair E: " + pairE);
	        	
	        	
		        //sample value to check if pairs conflict
		        int wijConditions = 1;
	        	
	        	for (int j = 0; j < listClients.size(); j++) {		
	        		if (wij[pairE.get(0)][j] > 0 && wij[pairE.get(1)][j] > 0) {
	        			
	        			//will equal to 0 if there's at least one j that satisfies the condition
	        			wijConditions = wijConditions*0;
	        		}
	        		
	        		else wijConditions = wijConditions*1;	
	        	}
    	
	        	if (wijConditions == 0) {
	        		System.out.println("conflicting pair:  " + pairE);   
	        		conflictingPairs[i][0] = pairE.get(0);
	        		conflictingPairs[i][1] = pairE.get(1);
	        		System.out.println("conflicting pair at" + i+ ": "+ conflictingPairs[i][0]);
	        		System.out.println("conflicting pair at" + i+ ": "+ conflictingPairs[i][1]);
	        	}
	        	else if (wijConditions == 1) {
	        		System.out.println("not a conflicting pair: " + pairE);
	        	}
	          }
	        
	          System.out.println("conflicting pairs assignment done");
	        	
	          
	        	//find maximal independent set I from the graph V,E
	          	// V for tentativelyOpenFacilities
	           	// E is the set of conflictingPairs
	          	// will choose facilities to be opened in the order in which they were temporarily opened
	          	// IF they are not conflicting with a facility already chosen
	          	// thus, open only one facility of a conflicting pairs
	        	for (int e = 0; e < listE.size(); e = e + 2) {	
	        		System.out.println("f" + conflictingPairs[e][0]);
	        		System.out.println("f" + conflictingPairs[e][1]);
	        		
	        		// Getting indexes of a conflicting pair to find the order in which they were temporarily opened
	        		int retval = listTentativeFacilityNo.indexOf(conflictingPairs[e][0]);
	        		int retval2 = listTentativeFacilityNo.indexOf(conflictingPairs[e][1]);
	    
	        		
	        		// Creating variables to check if facilities are already chosen or not
	        		int alreadyChosen = 1; //for when openFacilities is empty
	        		System.out.println("size of openFacilities: " + openFacilities.size());
        			int[] alreadyOpen = new int[openFacilities.size()];		
        			for(int i = 0 ; i < openFacilities.size(); i++) {
        				alreadyOpen[i] = 1;
        			}
        			
        			//fixing this value before doing additions to openFacilities, because it changes while going through the pairs
        			//the size of open facilities should change only when going through a pair is finished
        			int sizeOfOpenFacilities = openFacilities.size();
        			
	        		//opening facilities in the order facilities are tentatively opened
        			
        			//the case where the 1st element of the pair is tentatively opened before the 2nd is
	        		if (retval < retval2) {	
	        			System.out.println("size of open facilities: " + openFacilities.size());
	        			System.out.println("pair e : " + conflictingPairs[e][0] +","+conflictingPairs[e][1]);
	        			if (sizeOfOpenFacilities == 0) {
	        				System.out.println("no open facilities yet");
    						alreadyChosen = alreadyChosen*1;
    						System.out.println("alreadyChosen value: " + alreadyChosen);	
	        			}
	        			else if (sizeOfOpenFacilities > 0) {
	        				//check if it's conflicting with a facility already chosen
	        				
	        				
	        				//check if it's already in open facilities
	        				for (int o = 0 ; o < openFacilities.size(); o++) {
	        					String noOfFacility = openFacilities.get(o).substring(1);
	        					int facilityInt = Integer.parseInt(noOfFacility);
	        					if (facilityInt == conflictingPairs[e][0]) {
	        						System.out.println("this facility is already open : f" + conflictingPairs[e][0]);
	        						// this means that the next element of the pair will be conflicting with a facility already chosen
	        						// so cannot open the next element of this pair either
	        						System.out.println("so cannot open the next element: f" + conflictingPairs[e][1]);
	        						System.out.println("---skip this pair");
	        						alreadyChosen = alreadyChosen*0;
	 						
	        					}	
	        					else if (facilityInt != conflictingPairs[e][0]) {
	        						// if it's not in open facilities, 
	        						System.out.println("not already open: f" + conflictingPairs[e][0]);
	        						
	        						//checking all pairs to see if element is conflicting with an already chosen facility
	        						for (int mm = 0; mm < listE.size(); mm = mm + 2) {
	        							if ((conflictingPairs[mm][1] == conflictingPairs[e][0]) && (mm != e)) {
	        								
	        								if (facilityInt == conflictingPairs[mm][0]) {
	        										
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][0]);
	        									System.out.println("---skipping element " + conflictingPairs[e][0]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							
	        							else if ((conflictingPairs[mm][0] == conflictingPairs[e][0]) && (mm !=e)) {
	        								if (facilityInt == conflictingPairs[mm][1]) {
	        									
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][1]);
	        									System.out.println("---skipping element " + conflictingPairs[e][0]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							else {
	        								System.out.println(conflictingPairs[e][0] + "not conflicting with pair " +conflictingPairs[mm][0]+","+conflictingPairs[mm][1]);
	        								alreadyChosen = alreadyChosen*1; 
	        							}	
	        						}
	        					}
	        						
	        					else if (facilityInt == conflictingPairs[e][1]) {
	        						System.out.println("facility already open");
	        						System.out.println("---skipping element " + conflictingPairs[e][1] );
	        						alreadyChosen = alreadyChosen*0;
	        					}
	        					else if (facilityInt != conflictingPairs[e][1]) {
	        							
	        						System.out.println("not already open: f" + conflictingPairs[e][1]);
	        						for (int mm = 0; mm < listE.size(); mm = mm + 2) {
	        							if ((conflictingPairs[mm][1] == conflictingPairs[e][1]) && (mm != e)) {
	        								
	        								if (facilityInt == conflictingPairs[mm][0]) {
	        										
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][0]);
	        									System.out.println("---skipping element " + conflictingPairs[e][1]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							
	        							else if ((conflictingPairs[mm][0] == conflictingPairs[e][1]) && (mm !=e)) {
	        								if (facilityInt == conflictingPairs[mm][1]) {
	        									
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][1]);
	        									System.out.println("---skipping element " + conflictingPairs[e][1]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							else {
	        								System.out.println("not conflicting with an already chosen facility");
	        								alreadyChosen = alreadyChosen*2;
	        							}
	        						}
	        						alreadyOpen[o] = alreadyOpen[o]*1;
	        						System.out.println("alreadyChosen value: " + alreadyOpen[o]);
	        						System.out.println("alr chosen value: " + alreadyChosen);
	        					}
	        						
	        					
	        					
	        					else System.out.println("smth wrong between elements of conflicting pair and open facilities");
	        			 	}
	        			}
	        			
	        			System.out.println("size of open facilities --" + sizeOfOpenFacilities);
	        			if(sizeOfOpenFacilities != 0) {
	        				
	        				for (int i=0; i < sizeOfOpenFacilities; i++) {
	        					if (alreadyChosen == 0) {
	        						System.out.println("not opening a new facility");
	        					}
	        					else if (alreadyChosen == 1) {	
	        						if (openFacilities.contains("f" + conflictingPairs[e][0])) {
	        							System.out.println("facility already opened");
	        						}
	        						else {
	        							openFacilities.add("f" + conflictingPairs[e][0]);
	        							System.out.println("1st element of the pair added to open facilities: " + "f" + conflictingPairs[e][0]);
	        						}
	        					}
		        			
	        					else if (alreadyOpen[i] == 2) {
	        						if (openFacilities.contains("f" + conflictingPairs[e][0])) {
	        							System.out.println("facility already opened");
	        						}
	        						else {
	        							openFacilities.add("f" + conflictingPairs[e][0]);
	        							System.out.println("2nd element of the pair added to open facilities: " + "f" + conflictingPairs[e][0]);
	        						}
	        					}
	        				}
	        			}
	        			else if (sizeOfOpenFacilities == 0) {
	        				if (alreadyChosen == 1) {
	        					openFacilities.add("f" + conflictingPairs[e][0]);
	        					System.out.println("1st element of the pair added to open facilities, no facility was open before: " 
	        										+ "f" + conflictingPairs[e][0]);
	        				}
	        			}
	        		}
	        			
	        		else if (retval > retval2) {
	        			
	        			System.out.println("size of open facilities: " + openFacilities.size());
	        			System.out.println("pair e : " + conflictingPairs[e][0] +","+conflictingPairs[e][1]);
	        			if (sizeOfOpenFacilities == 0) {
	        				System.out.println("no open facilities yet");
    						alreadyChosen = alreadyChosen*1;
    						System.out.println("alreadyChosen value: " + alreadyChosen);	
	        			}
	        			else if (sizeOfOpenFacilities > 0) {
	        				//check if it's conflicting with a facility already chosen
	        				
	        				
	        				//check if it's already in open facilities
	        				for (int o = 0 ; o < openFacilities.size(); o++) {
	        					String noOfFacility = openFacilities.get(o).substring(1);
	        					int facilityInt = Integer.parseInt(noOfFacility);
	        					if (facilityInt == conflictingPairs[e][0]) {
	        						System.out.println("this facility is already open : f" + conflictingPairs[e][0]);
	        						// this means that the next element of the pair will be conflicting with a facility already chosen
	        						// so cannot open the next element of this pair either
	        						System.out.println("so cannot open the next element: f" + conflictingPairs[e][1]);
	        						System.out.println("---skip this pair");
	        						alreadyChosen = alreadyChosen*0;
	 						
	        					}	
	        					else if (facilityInt != conflictingPairs[e][0]) {
	        						// if it's not in open facilities, 
	        						System.out.println("not already open: f" + conflictingPairs[e][0]);
	        						
	        						//checking all pairs to see if element is conflicting with an already chosen facility
	        						for (int mm = 0; mm < listE.size(); mm = mm + 2) {
	        							if ((conflictingPairs[mm][1] == conflictingPairs[e][0]) && (mm != e)) {
	        								
	        								if (facilityInt == conflictingPairs[mm][0]) {
	        										
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][0]);
	        									System.out.println("---skipping element " + conflictingPairs[e][0]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							
	        							else if ((conflictingPairs[mm][0] == conflictingPairs[e][0]) && (mm !=e)) {
	        								if (facilityInt == conflictingPairs[mm][1]) {
	        									
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][1]);
	        									System.out.println("---skipping element " + conflictingPairs[e][0]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							else {
	        								System.out.println(conflictingPairs[e][0] + "not conflicting with pair " +conflictingPairs[mm][0]+","+conflictingPairs[mm][1]);
	        								alreadyChosen = alreadyChosen*1; 
	        							}	
	        						}
	        					}
	        						
	        					else if (facilityInt == conflictingPairs[e][1]) {
	        						System.out.println("facility already open");
	        						System.out.println("---skipping element " + conflictingPairs[e][1] );
	        						alreadyChosen = alreadyChosen*0;
	        					}
	        					else if (facilityInt != conflictingPairs[e][1]) {
	        							
	        						System.out.println("not already open: f" + conflictingPairs[e][1]);
	        						for (int mm = 0; mm < listE.size(); mm = mm + 2) {
	        							if ((conflictingPairs[mm][1] == conflictingPairs[e][1]) && (mm != e)) {
	        								
	        								if (facilityInt == conflictingPairs[mm][0]) {
	        										
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][0]);
	        									System.out.println("---skipping element " + conflictingPairs[e][1]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							
	        							else if ((conflictingPairs[mm][0] == conflictingPairs[e][1]) && (mm !=e)) {
	        								if (facilityInt == conflictingPairs[mm][1]) {
	        									
	        									System.out.println("conflicting with an already chosen facility " + conflictingPairs[mm][1]);
	        									System.out.println("---skipping element " + conflictingPairs[e][1]);
	        									alreadyChosen = alreadyChosen*0;
	        									
	        								}
	        							}
	        							else {
	        								System.out.println("not conflicting with an already chosen facility");
	        								alreadyChosen = alreadyChosen*2;
	        							}
	        						}
	        						alreadyOpen[o] = alreadyOpen[o]*1;
	        						System.out.println("alreadyChosen value: " + alreadyOpen[o]);
	        						System.out.println("alr chosen value: " + alreadyChosen);
	        					}
	        						
	        					
	        					
	        					else System.out.println("smth wrong between elements of conflicting pair and open facilities");
	        			 	}
	        			}
	        			
	        			System.out.println("size of open facilities --" + sizeOfOpenFacilities);
	        			if(sizeOfOpenFacilities != 0) {
	        				
	        				for (int i=0; i < sizeOfOpenFacilities; i++) {
	        					if (alreadyChosen == 0) {
	        						System.out.println("not opening a new facility");
	        					}
	        					else if (alreadyChosen == 1) {	
	        						if (openFacilities.contains("f" + conflictingPairs[e][0])) {
	        							System.out.println("facility already opened");
	        						}
	        						else {
	        							openFacilities.add("f" + conflictingPairs[e][0]);
	        							System.out.println("1st element of the pair added to open facilities: " + "f" + conflictingPairs[e][0]);
	        						}
	        					}
		        			
	        					else if (alreadyOpen[i] == 2) {
	        						if (openFacilities.contains("f" + conflictingPairs[e][0])) {
	        							System.out.println("facility already opened");
	        						}
	        						else {
	        							openFacilities.add("f" + conflictingPairs[e][0]);
	        							System.out.println("2nd element of the pair added to open facilities: " + "f" + conflictingPairs[e][0]);
	        						}
	        					}
	        				}
	        			}
	        			else if (sizeOfOpenFacilities == 0) {
	        				if (alreadyChosen == 1) {
	        					openFacilities.add("f" + conflictingPairs[e][0]);
	        					System.out.println("1st element of the pair added to open facilities, no facility was open before: " 
	        										+ "f" + conflictingPairs[e][0]);
	        				}
	        			}
	        		}	
	        		
	        		else System.out.println("smth wrong about index of pairs");
	        		
	        		System.out.println("additions to openFacilities is over for the pair at " + e +": " + conflictingPairs[e][0] + "," + conflictingPairs[e][1]);
	        		System.out.println("openFacilities at step " + e + " : " + openFacilities);
	        	}
	       }
	}	

	
 	public static void printWij() {
		
		System.out.println("Print final Wij:");
		String matrixWijArray = Arrays.deepToString(wij).replace("], ", "]\n").replace("[[", "[").replace("]]", "]");
		System.out.println(matrixWijArray.replace(",", " "));
	}
 	

	public static void increaseWijIfNotFrozen(int x, int y) {

		if (arrayFrozenWij[x][y] == 1 ) {
			System.out.println("do not increase w" + x + y);
		}
		else {
			wij[x][y]++;
			System.out.println("increased w" + x + y + "=" + wij[x][y]);
		}
	}
	
 	public static boolean checkIfAllClientsConnected(int[] array2) {
		
		int[] arrayOfColumns = new int[listClients.size()];
		for (int ll = 0; ll  < listClients.size(); ll++)	
		{	
			arrayOfColumns[ll] = ll;			
		}
		
		return Arrays.equals(arrayOfColumns, array2);
	}
	
	public static void keepConnectedClients() {
		
		for (int i = 0 ; i < listFacilities.size() ; i++) {		
			for (int j = 0 ; j < listClients.size() ; j++) {
			
				if (connectedFacilityClient[i][j] == clientConnected)
				{
					//System.out.println("connected at " + i + "-" + j + "");
			
					listColumns.add(j);						
				} 
		
			}
		}
		//columns, listColumns : store the column numbers that are connected to a facility,
		//thus have at least one value on the column that is equal to 1
		columns = listColumns.stream().mapToInt(Integer::intValue).toArray();
		System.out.println(Arrays.toString(columns));
		distinctArray = Arrays.stream(columns).sorted().distinct().toArray();
		
		/*
		System.out.println("keeping a distinct array of clients that are connected:");
		
		for (int index = 0 ; index < distinctArray.length ; index++) {
			
			System.out.println(index + ":" + distinctArray[index]);
			
		} 
		*/
	}
	
	
	// didnt call this method 
	public static void conditionVjHigherThanCij() {
		
		for (int i = 0 ; i < listFacilities.size() ; i++) {
			
			vjHigherThanCij.clear();
			for (int j = 0 ; j < listClients.size() ; j++) {
				if(costPaidByCustomer[j] >= listDistanceFromClient[i][j] ) {
			
					vjHigherThanCij.add(j);
					System.out.println("the customer " + j + " is added to the vjHigherThanCij list" );
			
				}
			}
		}
	}
	
	public static void increaseVjIfNotFrozen() {
		
		// going through all clients to check if they are frozen
		for (int j = 0 ; j < listClients.size() ; j++) {
			
			// integer list listFrozenVj stores the client no if they were frozen
			if(listFrozenVj.contains(j)) {
				System.out.println("already frozen v" + j + ":" + costPaidByCustomer[j]) ;
			}
			
			// if vj is not frozen, then increase its value with time
			else {
				
				costPaidByCustomer[j] = startTime;
				System.out.println("v" + j + ":" + costPaidByCustomer[j] + " is increased with time");
			}	
		}
	}
	
	public static void getAllConnections() {
		
		//System.out.println("final connections when all clients are connected");
		String matrixArray = Arrays.deepToString(connectedFacilityClient).replace("], ", "]\n").replace("[[", "[").replace("]]", "]");
		System.out.println(matrixArray.replace(",", " "));
		
	}
	public static void getTentativelyOpenFacilities() {
		
		//let V be the set of tentatively open facilities
		System.out.println("list of tentatively open facilities");		
		System.out.println(listTentativeFacilityNo);
	}
	
	public static void getOpenFacilities() {
		System.out.println("list of open facilities::");
		System.out.println(openFacilities);
	}
	

	//prompt the user to enter input
	// THIS METHOD NOT COMPLETED 
	public static void enterInput(){
		
		System.out.println("How many clients?");
		Scanner scanClient = new Scanner(System.in);

		int answerClient = scanClient.nextInt();
		numOfClients = answerClient;
		System.out.println("How many facilities?");
		Scanner scanFacility = new Scanner(System.in);
		int answerFacility = scanFacility.nextInt();
		numOfFacilities = answerFacility;
		
		listFixedCost = new ArrayList<Integer>();
		Scanner sn = new Scanner(System.in);
		for(int i=1; i == answerFacility ; i++) {
			System.out.println("Enter fixed cost for facility " + i + " : "  );
			int in = sn.nextInt();
			listFixedCost.add(in);
		}
		
		for(int fi=0; fi == listFixedCost.size() ; fi++) {
			
			System.out.println(listFixedCost.get(fi));
		}
			
		
	 }
	
	
	
	 public static void main(String[] args) {
		 
		 Scanner scan = new Scanner(System.in);
		 System.out.println("Would you like to enter input? y/n ");
		 
		 answerScan = new String();
		 answerScan= scan.nextLine();
		 
		 System.out.println("You entered: " + answerScan); 
		 deployAlgorithm();
	 
	 }
}




