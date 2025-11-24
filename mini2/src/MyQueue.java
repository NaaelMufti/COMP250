
public class MyQueue // Queue implemented using 2 stacks
{
    private Stack stack1;
    private Stack stack2;

    public MyQueue()
    {
        stack1 = new Stack();
        stack2 = new Stack();
    }

    // O(1)
    public void enqueue(int item) {
        stack1.push(item);
    }

    // O(n) worst case
    public int dequeue()
    {
        if (stack2.isEmpty())
        {
            while (!stack1.isEmpty())  // reverse the list
            {
                stack2.push(stack1.pop());
            }
        }

        if (stack2.isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }

        return stack2.pop(); // pop the top of the reversed list (now FIFO)
    }

    public static void main(String[] args)
    {
        MyQueue queue = new MyQueue();

        System.out.println("Enqueuing elements: 10, 20, 30");
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        System.out.println("Dequeued: " + queue.dequeue()); // 10

        queue.enqueue(40);
        queue.enqueue(50);

        System.out.println("Dequeued: " + queue.dequeue()); // 20
        System.out.println("Dequeued: " + queue.dequeue()); // 30
        System.out.println("Dequeued: " + queue.dequeue()); // 40
        System.out.println("Dequeued: " + queue.dequeue()); // 50

        System.out.println("Dequeued: " + queue.dequeue()); // Queue is empty!
    }
}

