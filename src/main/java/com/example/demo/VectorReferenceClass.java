package com.example.demo;

public class VectorReferenceClass {

	

	//So if I read ALL records in MySQL I could add each of them to a list which I could send over to the view (list.html) via a model container
	
	
	//Retrieve as an iteration a set of Jobs from jobrepository - which returns an iterable lst of objects
	// Creating an array list of String items or <Job> like in the example then adding to container prior to sending to view
	//I could define another method in JobsTableModel getAllRecords which returns an ArrayList of all the 
	// job recs in MyQSL JobsTable and populates an ArrayList of job records (or a vector for that matter) 
	//which then I can add to the model to populate the list.html view BUT in this case FROM the MySQL DB
	
	
	/*
	 * About Vectors
	 * The Vector class implements a growable array of objects. Vectors basically fall in LEGACY classes but now it is fully compatible
	 *  with COLLECTIONS. It is found in the java.util package and IMPLEMENTS the List interface, so we can use all the methods of List interface here.
	 * 
	 * Vector implements a dynamic array that means it can grow or shrink as required. Like an array, it contains components that can be accessed 
	 * using an integer index. They are very similar to ArrayList but Vector is synchronized and has some legacy method that the collection framework 
	 * does not contain. It also maintains an insertion order like an ArrayList but it is rarely used in a non-thread environment as it
	 *  is synchronized and due to which it gives a poor performance in adding, searching, delete and update of its elements.
	 *	The Iterators returned by the Vector class are fail-fast. In the case of concurrent modification, it fails and throws 
	 *	the ConcurrentModificationException.
	 * 
	 * Declaration: public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable 
	 * Here, E is the type of element.
	 * 
	 * 1) Vector(): Creates a default vector of the initial capacity is 10.Vector<E> v = new Vector<E>();
	 * 
	 * 2) Vector(int size): Creates a vector whose initial capacity is specified by size. Vector<E> v = new Vector<E>(int size);
	 * 
	 * 3) Vector(int size, int incr): Creates a vector whose initial capacity is specified by size and increment is specified by incr.
	 * It specifies the number of elements to allocate each time that a vector is resized upward. Vector<E> v = new Vector<E>(int size, int incr);
	 * 
	 * 4) Vector(Collection c): Creates a vector that contains the elements of collection c. Vector<E> v = new Vector<E>(Collection c);
	 * 
	 * 
	 */
	
	// jobVector = jobsTableModel.findAllJobs(); which return the vector
			//Iterable <Job> jobs = jobVector;//For each Job in Jobs (a list or Array list of objects)
			//for(Job job:jobs) 
			//{
				//for processing in the view since model will contain the vector object
				//System.out.println(job.getEmployer());
				//System.out.println(job.getTitle());
				//model.addAttribute("alist", aList);
				
			//}
			
			//Examples vector processing - note notation <>
			//int x = 20;
			//Vector<Integer> v = new Vector<Integer>(x); 
			  
	        // Appending new elements at 
	        // the end of the vector 
	        //for (int i = 1; i <= x; i++) 
	         //   v.add(i); 
	  
	        // Printing elements 
	        //System.out.println(v); 
	        
	        //Vector<Integer> v2 = new Vector<Integer>(x); 
	  
	        // Appending new elements at 
	        // the end of the vector 
	        //for (int i = 1; i <= x; i++) 
	         //   v2.add(i); 
	  
	        // Printing elements 
	        //System.out.println(v2); 
	        
	        /*
	         * The following section provides example of using arrayList with the model.addattribute		
	         */
			
			/*
			  Iterable <Job> jobs = jobRepository.findAll();//For each Job in Jobs (a list or Array list of objects)
			 
			int n =  10;
			List<Job> aList = new ArrayList<Job>(n); //populate????
			
			for(Job job:jobs) 
			{
				System.out.println(job.getEmployer());
				System.out.println(job.getTitle());
				aList.add(job);
			    model.addAttribute("alist", aList);
				
			}
			//ANOTHER EXAMPLE
			 // create an empty array list with an initial capacity
		      ArrayList<Integer> arrlist = new ArrayList<Integer>(5);//REMEMBER first array index=0

		      // use add() method to add elements in the list
		      arrlist.add(15);
		      arrlist.add(22);
		      arrlist.add(30);
		      arrlist.add(40);

		      // adding element 25 at third position
		      arrlist.add(2,25);
		        
		      // let us print all the elements available in list
		      for (Integer number : arrlist) {
		         System.out.println("Number = " + number);
		      }   
			 */ 
	
	
	
	
	
	
}
