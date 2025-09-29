package assignment1;
// Naael Mufti, McGill ID: 261279652

public class SwarmOfHornets
{
    private Hornet[] hornets; // array of hornets
    private int numOfHornets; // size of LinkedList

    private Node head;
    private Node tail;

    private class Node // linked list needs Nodes
    {
        Hornet hornet;
        Node next;
    }

    public SwarmOfHornets() // creating empty swarm (size = 0)
    {
        head = null;
        tail = null;
        numOfHornets = 0;
    }

    public int sizeOfSwarm()
    {
        return numOfHornets;
    }

    public Hornet[] getHornets()
    {
        Hornet[] temp = new Hornet[numOfHornets]; // new temp copy

        if (numOfHornets == 0) // check not empty
        {
            throw new IllegalArgumentException("No hornets in the array");
        }

        Node cur = head;
        int i = 0;

        while (cur != null) // make shallow copy of array
        {
            temp[i] = cur.hornet;
            cur = cur.next;
            i = i + 1;
        }
        return temp;
    }

    public Hornet getFirstHornet()
    {
        if (head == null)
        {
            return null;
        } else
            return head.hornet;
    }

    public void addHornet(Hornet n)
    {
        Node newNode = new Node();
        newNode.hornet = n;

        if (tail == null) // if empty assign h to both head and tail
        {
            tail = newNode;
            head = newNode;
        } else
        {
            tail.next = newNode;
            tail = tail.next;
        }
        numOfHornets = numOfHornets + 1;
    }

    public boolean removeHornet(Hornet n)
    {

        if (head.hornet == n) // if n is the head
        {
            head = head.next; // no prev node to compare so remove head
            if (head == null) // only 1 element inside Swarm
            {
                tail = null; // so if empty will make tail not point to old element
            }
            numOfHornets = numOfHornets - 1;
            return true;
        } else if (numOfHornets == 0) // if empty return false
        {
            return false;
        } else
        {
            Node cur = head;
            while(cur.next != null)
            {
                if (cur.next.hornet == n) // reference matching, not value matching
                {
                    cur.next = cur.next.next; // point prev node to node after n (removing n)

                    numOfHornets = numOfHornets - 1;
                    return true;
                }
                cur = cur.next; // iterate through
            }
            return false;
        }
    }


}
