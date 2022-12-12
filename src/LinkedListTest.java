
/**
 * Test classes LinkedList and LLNode
 *
 * @author Alan Zhang
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test classes LinkedList
 */
class LinkedListTest {
    // setup
    LLNode<Integer> node7 = new LLNode<Integer>(7, null);
    LLNode<Integer> node6 = new LLNode<Integer>(6, node7);
    LLNode<Integer> node5 = new LLNode<Integer>(5, node6);
    LLNode<Integer> node4 = new LLNode<Integer>(4, node5);
    LLNode<Integer> node3 = new LLNode<Integer>(3, node4);
    LLNode<Integer> node2 = new LLNode<Integer>(2, node3);
    LLNode<Integer> node1 = new LLNode<Integer>(1, node2);
    LinkedList<Integer> list = new LinkedList<Integer>();
    LLNode<Integer> node8 = new LLNode<Integer>(8, null);

    /**
     * Test MoveBack method
     */
    @Test
    public void testMoveBack() {
        // initialize list: 1, 2, 3, 4, 5, 6, 7
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.setFirstNode(node1);
        // move the first node 2 places back
        list.moveBack(2);
        // first should be 2
        assertEquals(2, list.getFirstNode().getElement());
        // the third node should be 1
        assertEquals(1, list.getFirstNode().getNext().getNext().getElement());
    }

    /**
     * Test MoveFirstToLast method
     */
    @Test
    public void testMoveFirstToLast() {
        // initialize list: 1, 2, 3, 4, 5, 6, 7
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.setFirstNode(node1);
        // move the first node to the last
        list.moveFirstToLast();
        // first should be 2
        assertEquals(2, list.getFirstNode().getElement());
        // the last node should be 1
        assertEquals(1, list.getFirstNode().getNext().getNext().getNext().getNext().getNext().getNext().getElement());
    }

    /**
     * Test MoveLastToFirst method
     */
    @Test
    public void testMoveLastToFirst() {
        // initialize list: 1, 2, 3, 4, 5, 6, 7
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.setFirstNode(node1);
        // move the last node to first
        list.moveLastToFirst();
        // first should be 7
        assertEquals(7, list.getFirstNode().getElement());
        // the second node should be 1
        assertEquals(1, list.getFirstNode().getNext().getElement());
    }

    /**
     * Test ReverseList method
     */
    @Test
    public void testReverseList() {
        // initialize list: 1, 2, 3, 4, 5, 6, 7
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.setFirstNode(node1);
        // reverse list
        list.reverseList();
        // first should be 7
        assertEquals(7, list.getFirstNode().getElement());
        // the second should be 6
        assertEquals(6, list.getFirstNode().getNext().getElement());
    }

    /**
     * Test ReverseFirstK method
     */
    @Test
    public void testReverseFirstK() {
        // initialize list: 1, 2, 3, 4, 5, 6, 7
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.setFirstNode(node1);
        // reverse first 3 nodes
        list.reverseFirstK(3);
        // list should be 3, 2, 1, 4, 5, 6, 7
        // first node should be 3
        assertEquals(3, list.getFirstNode().getElement());
        // the second should be 2
        assertEquals(2, list.getFirstNode().getNext().getElement());
        // the third should be 1
        assertEquals(1, list.getFirstNode().getNext().getNext().getElement());
        // the second should be 4
        assertEquals(4, list.getFirstNode().getNext().getNext().getNext().getElement());
    }

}