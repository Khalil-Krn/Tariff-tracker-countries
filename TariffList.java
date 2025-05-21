package tariffManagementSystem;

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy{

	// Inner class representing a node in the linked list
	public class TariffNode{
		//Private attribute
		private Tariff data;
		private TariffNode next;
		
		//Parameterized Constructor
		public TariffNode(Tariff data, TariffNode next) {
            this.data = new Tariff(data); 
            this.next = next;
        }
		// Default constructor
		public TariffNode() {
			this(null, null);
		}
		//Copy constructor (Not cloning next to avoid recursive deep copy)
		public TariffNode(TariffNode otherTariffNode) {
			this(new Tariff(otherTariffNode.data), null);
		}
		// Returns a deep copy of this node
		public TariffNode clone() {
            return new TariffNode(this);
        }
		// Compares two nodes based on their Tariff data
		@Override
		public boolean equals(Object otherObject) {
			TariffNode otherTariffNode = (TariffNode) otherObject;
            return data.equals(otherTariffNode.data);
        }
		//getters
		public Tariff getData()	{ 
			return data; 
		}
        public TariffNode getNext() { 
        	return next; 
        }
        //setters
        public void setData(Tariff data) { 
        	this.data = data;
        }
        public void setNext(TariffNode next) { 
        	this.next = next;
        }
        
	}
	// Linked List class attributes
    private TariffNode head;
    private int size;

    // Default constructor
    public TariffList() {
        this.head = null;
        this.size = 0;
    }

    // Copy constructor (deep copy that duplicates all the list)
    public TariffList(TariffList other) {
        if (other.head == null) {
            this.head = null;
            this.size = 0;
        } else {
            this.head = new TariffNode(other.head);
            TariffNode current = this.head;
            TariffNode otherCurrent = other.head.next;
            while (otherCurrent != null) {
                current.next = new TariffNode(otherCurrent);
                current = current.next;
                otherCurrent = otherCurrent.next;
            }
            this.size = other.size;
        }
    }
    /* Adds a new TariffNode to the beginning (head) of the list.
    // This method shifts the current head to the second position 
    // and places the new node at the front of the list.
    // It increases the list size by 1.
    */
    public void addToStart(Tariff tariff) {
    	TariffNode newNode = new TariffNode(tariff, head);
        this.head = newNode;
        size++;
    }
    /* Inserts a new TariffNode at a specified index in the list.
    // If index is 0, it uses addToStart(). Otherwise, it traverses
    // to the correct position and adjusts pointers to insert the node.
    // Throws an exception if the index is invalid (out of bounds).
    */
    public void insertAtIndex(Tariff tariff, int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Invalid index: " + index);
        }

        if (index == 0) {
            addToStart(tariff);
        } else {
        	TariffNode currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }
            
            TariffNode newNode = new TariffNode(tariff, currentNode.next);
            currentNode.next = newNode;
            size++;
        }
    }
    /* Removes the first node (head) of the list.
    // If the list is empty, it does nothing.
    // Otherwise, it moves the head pointer to the second node.
    // Decreases the list size by 1.
    */ 
    public void deleteFromStart() {
        if (head == null) {
            return; // List is empty, nothing to delete
        }
        
        head = head.next;
        size--;
    }
    // Deletes a node at the specified index in the list.
    // If index is 0, it removes the head using deleteFromStart().
    // For other indices, it traverses to the node just before the one to delete,
    // and links it to the node after the deleted one.
    public void deleteFromIndex(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Invalid index: " + index);
        }
        
        if (index == 0) {
            deleteFromStart();
            return;
        }
        
        TariffNode currentNode = head;
        for (int i = 0; i < index - 1; i++) {
            currentNode = currentNode.next;
        }
        
        currentNode.next = currentNode.next.next;
        size--;
    }
    // Replaces the Tariff object at the given index with a new one.
    // If the index is invalid, the method does nothing.,
    // Otherwise, it navigates to the node at the index and replaces its data.
    public void replaceAtIndex(Tariff tariff, int index) {
        if (index < 0 || index >= size) {
            return; // Invalid index, do nothing
        }
        
        TariffNode currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        currentNode.setData(tariff);
    }
    // Searches the list for a TariffNode that matches the given origin,
    // destination, and category. It performs a linear search from the head.
    // If `iteration` is true, it prints how many nodes were checked.
    // Returns the matching node if found; otherwise, returns null.
    public TariffNode find(String origin, String destination, String category, boolean iteration) {
        TariffNode current = head;
        int count = 0;

        while (current != null) {
            count++;
            Tariff t = current.getData();
            if (t.getOriginCountry().equals(origin) &&
                t.getDestinationCountry().equals(destination) &&
                t.getProductCategory().equals(category)) {

            	if (iteration) {
            	    System.out.println("Found after " + count + " iterations.");
            	}

                return current;
            }
            current = current.getNext();
        }
        if (iteration) {
            System.out.println("Not found after " + count + " iterations.");
        }
        return null;
    }
    // Checks whether the list contains a TariffNode with the given origin,
    // destination, and category. This is a silent check — it does not print
    // iteration count like `find()` does. Returns true if a match is found.
    public boolean contains(String origin, String destination, String category) {
        return find(origin, destination, category, false) != null;
    }
    // Compares this TariffList with another for deep equality.
    // Returns true only if both lists have the same size and their TariffNodes
    // contain equal Tariff objects in the same order.
    public boolean equals(TariffList otherTariff) {
        if (this.size != otherTariff.size) return false;

        TariffNode curr1 = this.head;
        TariffNode curr2 = otherTariff.head;

        while (curr1 != null) {
            if (!curr1.getData().equals(curr2.getData())) return false;
            curr1 = curr1.next;
            curr2 = curr2.next;
        }

        return true;
    }
    // Evaluates a trade request based on the proposed and minimum tariff.
    // Returns "Accepted" if proposed ≥ minimum, "Conditionally Accepted"
    // if it's within 20% below the minimum, or "Rejected" otherwise.
    @Override
    public String evaluateTrade(double proposedTariff, double minimumTariff) {
        if (proposedTariff >= minimumTariff) {
            return "Accepted";
        } else if (proposedTariff >= minimumTariff * 0.80) {
            return "Conditionally Accepted";
        } else {
            return "Rejected";
        }
    }
    public int getSize() {
        return size;
    }





    
}
