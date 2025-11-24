
// Stack implemented using linked nodes
public class Stack
{
    class Node
    {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node top;

    public Stack()
    {
        top = null;
    }

    public void push(int value) {
        Node newNode = new Node(value);
        newNode.next = top;
        top = newNode;
    }

    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return -1;
        }
        int val = top.value;
        top = top.next;
        return val;
    }

    public boolean isEmpty()
    {
        return top == null;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return -1;
        }
        return top.value;
    }
}

