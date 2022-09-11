import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author AmaN Patel
 * @version 1.0
 * @userid apatel734
 * @GTID 903379254
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor..

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Specified Index is out of bounds!");
        }
        if (data == null) {
            throw new IllegalArgumentException("Specified Index is Null!");
        }

        SinglyLinkedListNode<T> curr = new SinglyLinkedListNode<>(data);

        if (head == null) {
            head = curr;
            tail = curr;
        } else if (index == 0) {
            curr.setNext(head);
            head = curr;
        } else if (index == size) {
            tail.setNext(curr);
            curr.setNext(null);
            tail = curr;
        } else {
            SinglyLinkedListNode<T> current = head;
            int previousIndex = 0;
            while (previousIndex != index - 1) {
                current = current.getNext();
                previousIndex++;
            }
            curr.setNext(current.getNext());
            current.setNext(curr);
        }

        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Specified Index is Null!");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Specified Index is Null!");
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Specified Index is out of bounds!");
        }
        T data;
        if (index == 0) {
            data = head.getData();
            head = head.getNext();
        } else {
            SinglyLinkedListNode<T> current = head;
            int previousIndex = 0;
            while (previousIndex != index - 1) {
                current = current.getNext();
                previousIndex++;
            }
            if (index == size - 1) {
                data = tail.getData();
                current.setNext(null);
                tail = current;
                size--;
                return data;
            }
            data = current.getNext().getData();
            current.setNext(current.getNext().getNext());
        }
        size--;
        return data;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty!");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty!");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Specified Index is out of bounds!");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {  // nmight be size - 1
            return tail.getData();
        } else {
            SinglyLinkedListNode<T> current = head;
            int curr = 0;
            while (curr != index) {
                current = current.getNext();
                curr++;
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        if (size == 0) {
            throw new NoSuchElementException("Data not found!");
        } else {
            SinglyLinkedListNode<T> current = head;
            SinglyLinkedListNode<T> lastCopy = null;
            if (head.getData().equals(data)) {
                lastCopy = current;
            }
            current = current.getNext();
            while (current != head && current.getNext() != null) {
                if (current.getNext().getData().equals(data)) {
                    lastCopy = current;
                }
                current = current.getNext();
            }
            if (lastCopy != null) {
                current = lastCopy.getNext();
                lastCopy.setNext(lastCopy.getNext().getNext());
                if (--size == 0) {
                    head = null;
                }
            }
            return current.getData();
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] dataArray = (T[]) new Object[size];
        SinglyLinkedListNode<T> current = head;
        int index = 0;
        while (current != null) {
            dataArray[index] = current.getData();
            current = current.getNext();
            index++;
        }
        return dataArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}