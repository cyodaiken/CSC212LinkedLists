package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.*;

/**
 * A Doubly-Linked List is a list based on nodes that know of their successor and predecessor.
 * @author jfoley
 *
 * @param <T>
 */
public class DoublyLinkedList<T> extends ListADT<T> {
	/**
	 * This is a reference to the first node in this list.
	 */
	Node<T> start;
	/**
	 * This is a reference to the last node in this list.
	 */
	Node<T> end;

	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}

	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}

	@Override
	public T removeFront() {
		checkNotEmpty();
		T value = this.start.value;

		if (this.start.after == null) {
			start = end = null;
			return value;
		}

		Node<T> beforeStart = this.start.before;

		this.start = this.start.after;
		this.start.before = beforeStart;
		beforeStart = this.start;

		return value;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();

		T value = this.end.value;

		if (this.end.before == null) {
			start = end = null;
			return value;
		}

		Node<T> afterEnd = this.end.after;

		this.end = this.end.before;
		this.end.after = afterEnd;
		afterEnd = this.end;

		return value;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();

		if(index == 0) {
			T remove = removeFront();
			return remove;
		}
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (at++ == index - 1) {

				Node<T> subsequent = n.after;
				Node<T> current = n;

				if(subsequent.after == null) {	
					removeBack();

				} else {
					n.after = n.after.after;
					n.after.before = current;
				}
				return subsequent.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (start == null) {
			start = end = new Node<T>(item);	
		} else {
			Node<T> second = start;
			start = new Node<T>(item);
			start.after = second;
			second.before = start;
		}
	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {

		if(index == 0) {

			addFront(item);
			return;
		}

		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (at++ == index - 1) {

				Node<T> subsequent = n.after;
				Node<T> current = n;

				if(subsequent == null) {

					addBack(item);

				} else {

					n.after = new Node<T>(item); 
					n.after.before = current;
					n.after.after = subsequent; 
					subsequent.before = n.after;
				}
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
		return this.end.value;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	public void setIndex(int index, T value) {
		checkNotEmpty();

		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
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
		for (Node<T> n = this.end; n != null; n = n.before) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
}
