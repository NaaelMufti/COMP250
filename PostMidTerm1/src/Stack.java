public class Stack {
	private SNode top;

    private class SNode
    {
        private Integer element;
        private	SNode next;

        SNode(Integer element) {
            this.element = element;
            this.next = null;
        }
    }

	public Stack()
    {
		this.top = null;
	}
	
	public Stack(SNode h)
    {
		this.top = h;
	}

    public boolean isEmpty()
    {
        return top == null; // if top == null returns true
    }
	
	// adds an element to the front of the list
	public void addFirst(Integer element) {	
		SNode newNode = new SNode(element); 
		
		if (this.isEmpty())
			top = newNode;
		else {
			newNode.next = top;
			top = newNode;
		}
	}

    /*
	public void displayNNodes(int n) {
		SNode cur = this.top;
		int i = 0;
		
		while (i<n && cur != null) {
			//System.out.print("Node: " + cur);
			System.out.println(" Element: " + cur.element);
			cur = cur.next;
			i++;
		}
	}
	

	

	public static void main(String[] args) {
		SLinkedList list = new SLinkedList();
		Random gen = new Random();
		int n;
		
		// First list
		SNode head1 = list.new SNode(10);
		SNode cur = head1;
		n = gen.nextInt(8);
		for (int i=0; i<n; i++) {
			SNode newNode = list.new SNode(i);
			cur.next = newNode;
			cur = newNode;
		}
		SLinkedList listOne = new SLinkedList(head1);
		
		// Second list
		SNode head2 = list.new SNode(10);
		cur = head2;
		n = gen.nextInt(8);
		for (int i=0; i<n; i++) {
			SNode newNode = list.new SNode(i);
			cur.next = newNode;
			cur = newNode;
		}
		cur.next = head2;
		SLinkedList listTwo = new SLinkedList(head2);
		
		// Third list
		SNode head3 = list.new SNode(10);
		cur = head3;
		n = gen.nextInt(8);
		for (int i=0; i<n; i++) {
			SNode newNode = list.new SNode(i);
			cur.next = newNode;
			cur = newNode;
		}
		cur.next = head3;
		SLinkedList listThree = new SLinkedList(head3);
		n = gen.nextInt(3) + 1;
		for (int i=0; i<n; i++) {
			listThree.addFirst(i);
		}
		
		
		System.out.println("List One");
		listOne.displayNNodes(12);
	
		System.out.println("\n\nList Two");
		listTwo.displayNNodes(12);
		
		System.out.println("\n\nList Three");
		listThree.displayNNodes(12);
		
	}

     */
	

}
