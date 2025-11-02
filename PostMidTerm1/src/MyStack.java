public class MyStack
{
    Node top;

    private class Node
    {
        int data;
        Node next;

        public Node()
        {

        }

        public Node (Node n)
        {
            this.data = data;
            this.next = n;
        }
    }

    public MyStack()
    {
        this.top = null;
    }

    public MyStack(Node t)
    {
        this.top = t;
    }

    public boolean isEmpty()
    {
        return top == null; // if top == null returns true
    }

    public void addFirst(int e) // push
    {
        Node newNode = new Node();
        newNode.data = e;

        if (this.isEmpty())
        {
            top = newNode;
        }
        else
        {
            newNode.next = top;
            top = newNode;
        }
    }

    public int pop()
    {
        if(isEmpty())
        {
            throw new RuntimeException("Stack is empty, nothing to pop");
        }
        int n = top.data;
        top = top.next;
        return n;
    }

    public void printStack()
    {
        if(this.isEmpty())
        {
            System.out.println("Stack is Empty, nothing to print");
        }
        else
        {
            Node temp = top;
            System.out.println("Top");
            // Code
            System.out.println("Null");
        }
    }
}
