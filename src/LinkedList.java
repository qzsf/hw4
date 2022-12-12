import java.util.NoSuchElementException;

/**
 * A class to represent a linked list of nodes.
 */
public class LinkedList<T> implements Iterable<T> {
  /** the first node of the list, or null if the list is empty */
  private LLNode<T> firstNode;

  /**
   * Creates an initially empty linked list
   */
  public LinkedList() {
    firstNode = null;
  }

  /**
   * Returns the first node.
   */
  protected LLNode<T> getFirstNode() {
    return firstNode;
  }

  /**
   * Changes the front node.
   * 
   * @param node the node that will be the first node of the new linked list
   */
  protected void setFirstNode(LLNode<T> node) {
    this.firstNode = node;
  }

  /**
   * Return whether the list is empty
   * 
   * @return true if the list is empty
   */
  public boolean isEmpty() {
    return (getFirstNode() == null);
  }

  /**
   * Add an element to the front of the linked list
   */
  public void addToFront(T element) {
    setFirstNode(new LLNode<T>(element, getFirstNode()));
  }

  /**
   * Removes and returns the element at the front of the linked list
   * 
   * @return the element removed from the front of the linked list
   * @throws NoSuchElementException if the list is empty
   */
  public T removeFromFront() {
    if (isEmpty())
      throw new NoSuchElementException();
    else {
      T save = getFirstNode().getElement();
      setFirstNode(getFirstNode().getNext());
      return save;
    }
  }

  /**
   * Returns the length of the linked list
   * 
   * @return the number of nodes in the list
   */
  public int length() {
    int count = 0;
    LLNode<T> nodeptr = getFirstNode();
    while (nodeptr != null) {
      count++;
      nodeptr = nodeptr.getNext();
    }
    return count;
  }

  /**
   * Adds an element to the end of the linkd list
   * 
   * @param element the element to insert at the end
   */
  public void addToEnd(T element) {
    if (isEmpty())
      addToFront(element);
    else {
      LLNode<T> nodeptr = getFirstNode();
      while (nodeptr.getNext() != null)
        nodeptr = nodeptr.getNext();
      nodeptr.setNext(new LLNode<T>(element, null));
    }
  }

  /**
   * Return an iterator for this list
   * 
   * @return the iterator for the list
   */
  @Override
  public LinkedListIterator<T> iterator() {
    return new LinkedListIterator<T>(getFirstNode());
  }

  /*
   * Static methods and generics:
   * Generic types only go with instance methods and fields
   * If I want a generic here, I must declare it in the method header,
   * before the return type
   */

  /**
   * Prints the contents of a list to System.out.
   * 
   * @param list the list to print
   */
  public static <S> void printList(LinkedList<S> list) {
    for (S element : list) {
      System.out.print(element);
      System.out.print(" ");
    }
    System.out.println();
  }

  /*
   * Generic types and wildcards:
   * If we don't care what the generic type is because we don't use that type
   * (other than calling Object methods on it)
   * we can use a wildcard that means we don't care what the generic type is
   */

  /**
   * Prints the contents of a linked list to System.out.
   * 
   * @param list the linked list to print
   */
  public static void printList2(LinkedList<?> list) {
    for (Object element : list) {
      System.out.print(element);
      System.out.print(" ");
    }
    System.out.println();
  }

  /**
   * Take a linked list that is already sorted in order and add a new element
   * into the correct location
   * 
   * @param list    the linked list
   * @param element the element to add
   */
  public static <S extends Comparable<? super S>> void insertInOrder(LinkedList<S> list, S element) {
    if (list.isEmpty()) {
      list.addToFront(element);
    } else {
      // case 1: the element goes to front of list
      if (element.compareTo(list.getFirstNode().getElement()) < 0)
        list.addToFront(element);
      // case 2: the element goes elsewhere, create a nodeptr to find where it goes
      else {
        LLNode<S> nodeptr = list.getFirstNode();
        while (nodeptr.getNext() != null && nodeptr.getNext().getElement().compareTo(element) < 0)
          nodeptr = nodeptr.getNext();
        // when the loop ends the element goes after nodeptr
        nodeptr.setNext(new LLNode<>(element, nodeptr.getNext()));
      }
    }
  }

  /**
   * Move the first node of the list back n places leaving
   * the rest of the list nodes in place.
   * 
   * @param n - n places
   */
  public void moveBack(int n) {
    if (isEmpty())
      throw new NoSuchElementException();
    else {
      if (n >= length())
        return;
      T first = removeFromFront();
      LLNode<T> nodeptr = getFirstNode();
      while (n > 1) {
        if (nodeptr == null)
          return;
        nodeptr = nodeptr.getNext();
        n--;
      }
      nodeptr.setNext(new LLNode<>(first, nodeptr.getNext()));
    }
  }

  /**
   * Method - Move the first node to become the last node of the list
   */
  public void moveFirstToLast() {
    if (isEmpty())
      throw new NoSuchElementException();
    else {
      T first = removeFromFront();
      addToEnd(first);
    }
  }

  /**
   * Method - Move the last node to become the first node of the list
   */
  public void moveLastToFirst() {
    if (isEmpty())
      throw new NoSuchElementException();
    else {
      LLNode<T> last = null;
      LLNode<T> nodeptr = getFirstNode();
      while (nodeptr.getNext().getNext() != null)
        nodeptr = nodeptr.getNext();
      last = nodeptr.getNext();
      nodeptr.setNext(null);
      last.setNext(getFirstNode());
      setFirstNode(last);
    }
  }

  /**
   * Reverses all the nodes of the list
   */
  public void reverseList() {
    LLNode<T> prev = null;
    LLNode<T> current = getFirstNode();
    LLNode<T> next = null;
    while (current != null) {
      next = current.getNext();
      current.setNext(prev);
      prev = current;
      current = next;
    }
    setFirstNode(prev);
  }

  /**
   * Reverse the first k nodes of the linked list
   * The rest of the list is unchanged
   * 
   * @param k - the first k nodes of the linked list
   */
  public void reverseFirstK(int k) {
    if (k > length())
      return;
    int i = 0;
    LLNode<T> jointPoint = getFirstNode();
    while (i < k) {
      jointPoint = jointPoint.getNext();
      i++;
    }
    LLNode<T> prev = jointPoint;
    LLNode<T> current = getFirstNode();
    LLNode<T> next = null;
    while (k > 0) {
      next = current.getNext();
      current.setNext(prev);
      prev = current;
      current = next;
      k--;
    }
    setFirstNode(prev);
  }

  /**
   * Shuffle the list
   */
  public void shuffle() {
    for (int i = 0; i < length() * 3; i++) {
      moveBack((int) (Math.random() * length()));
    }
  }

  /**
   * Take n cards from the linked list
   * @param n - number of cards
   */
  public void take(int n) {
    if (n > length())
      return;
    LLNode<T> nodeptr = getFirstNode();
    while(n > 1){
      nodeptr=nodeptr.getNext();
      n--;
    }
    nodeptr.setNext(null);
  }

}
