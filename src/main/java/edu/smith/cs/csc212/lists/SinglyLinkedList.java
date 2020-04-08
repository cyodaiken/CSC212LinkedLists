package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.EmptyListError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Singly-Linked List is a list that has only knowledge of its very first
 * element. Elements after that are chained, ending with a null node.
 * 
 * @author jfoley
 *
 * @param <T> - the type of the item stored in this list.
 */
public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		T value = this.start.value;
		this.start = this.start.next;
		return value;
	}

	@Override
	public T removeBack() {
		this.checkNotEmpty();

		if (this.size() == 1) { 
			T value = removeFront(); 
			return value;
		}

		Node<T> beforelast = null; 
		for (Node<T> current = this.start; current.next != null;
				current = current.next) { 
			beforelast = current;

		}
		assert(beforelast.next.next == null);
		T value = beforelast.next.value;
		beforelast.next = null;

		return value; 
	}

	@Override 
	public T removeIndex(int index) { 
		this.checkNotEmpty();

		if (index == 0) { 
			T value = removeFront(); 
			return value;
		}

		T remove = null;
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {

			if (at++ == index -1 ) {

				remove = n.next.value; 

				n.next = n.next.next ;

				return remove; 
			} 

		}

		throw new BadIndexError(index);
	}


	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		if (this.start == null) {
			addFront(item);
			return;
		}
		Node<T> last = null;
		for (Node<T> current = this.start;
				current != null; current = current.next) {
			last = current;
		}
		assert(last.next == null);
		last.next = new Node<T>(item, null);
	}

	@Override
	public void addIndex(int index, T item) {

		if (index == 0) { 
			addFront(item); 
			return; 
		}

		int at = 0; 
		for (Node<T> n = this.start; n != null; n = n.next) {  

			if (at++ == index - 1) {
				n.next = new Node<T>(item, n.next);
				return; 
			} 
		}

		throw new BadIndexError(index);
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return this.start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		Node<T> last = null;
		for (Node<T> current = this.start;
				current != null; current = current.next) {
			last = current;
		}
		return last.value;

	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;

		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {

				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();

		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {

			if (at++ == index) {
				n.value = value;
				return;
			} 
		}
		throw new BadIndexError(index);
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with a friend.
		 * @param value - the value to put in it.
		 * @param next - the friend of this node.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Alternate constructor; create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.next = null;
		}
	}

}
