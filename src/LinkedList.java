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
   * @param node  the node that will be the first node of the new linked list
   */
  protected void setFirstNode(LLNode<T> node) {
    this.firstNode = node;
  }

  /**
   * Return whether the list is empty
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
   * @return the iterator for the list
   */
  @Override
  public LinkedListIterator<T> iterator() {
    return new LinkedListIterator<T>(getFirstNode());
  }
  
  /* Static methods and generics: 
   *   Generic types only go with instance methods and fields
   *   If I want a generic here, I must declare it in the method header,
   *   before the return type 
   */
  
  /**
   * Prints the contents of a list to System.out.
   * @param list the list to print
   */
  public static <S> void printList(LinkedList<S> list) {
    for (S element : list) {
      System.out.print(element);
      System.out.print(" ");
    }
    System.out.println();
  }
  
  /* Generic types and wildcards:
   *    If we don't care what the generic type is because we don't use that type 
   *     (other than calling Object methods on it)
   *    we can use a wildcard that means we don't care what the generic type is 
   */
  
  /**
   * Prints the contents of a linked list to System.out.
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
   * @param list the linked list
   * @param element the element to add
   */
  public static <S extends Comparable<? super S>> void insertInOrder(LinkedList<S> list, S element) {
    if (list.isEmpty()) {
      list.addToFront(element);
    }
    else {
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
}
