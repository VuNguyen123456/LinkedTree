import java.awt.Color;

//no arrays! (this file)
/**
 * Tree.
 * @param <T> para
 */
class LinkedTree<T> {
	// /**************************************************
	// You may add additional PRIVATE instance variables
	// and methods. You may NOT use arrays in this file.
	// **************************************************/
	/**
	 * root of tree.
	 */
	private TreeNode<T> root;

	//Helper
	/**
	 * Get the size from provided root.
	 * @param root of tree
	 * @return size
	 */
	private int getSizeFromRoot(TreeNode<T> root){
		int treeSize = 0;
		if(root == null){
			return  0;
		}
		//Recursively counting all node from left to right
		treeSize += getSizeFromRoot(root.left);
		treeSize += getSizeFromRoot(root.right);
		treeSize += 1;
		return treeSize;
	}
	/**
	 * get Height from provided root.
	 * @param root of tree
	 * @return height
	 */
	private  int getHeightFromRoot(TreeNode<T> root){
		int treeHeight = 0;
		if(root == null){
			return  0;
		}
		//Compare height of left and right tree recursively and get the larger one to get the height of the whole tree
		if(getHeightFromRoot(root.left) <= getHeightFromRoot(root.right)){
			treeHeight += getHeightFromRoot(root.right);
		}
		else{
			treeHeight += getHeightFromRoot(root.left);
		}
		treeHeight += 1;
		return treeHeight;
	}
	//Function
	/**
	 * Contruct.
	 * @param root to add
	 */
	public LinkedTree(TreeNode<T> root) {
		//your code here
		this.root = root;
	}
	/**
	 * Get the root.
	 * @return root
	 */
	public TreeNode<T> getRoot() {
		//your code here
		if(root == null){
			return null;
		}
		else{
			return root;
		}
		//Note: an empty tree should return null
		//return null; //replace this!
	}
	/**
	 * get the size of tree.
	 * @return size
	 */
	public int getSize() {
		//your code here
		//Big-O requirement: O(n), where n is the number of elements in the tree
		//Hint: This can be done with ~2-3 lines in a recursive helper function.
		//Calling helper
		if(root == null){
			return 0;
		}	
		return getSizeFromRoot(this.root);
	}
	/**
	 * get the height of tree.
	 * @return height
	 */
	public int getHeight() {
		//your code here
		//Big-O requirement: O(n), where n is the number of elements in the tree
		//Hint: This can be done with ~2-3 lines in a recursive helper function.
		//Note: the height of a single-node tree (root and nothing else) is 0.
		//		an empty tree should return -1
		if(root == null){
			return  -1;
		}
		else if(root.left == null && root.right == null){
			return  0;
		}
		//Calling helper and adjust the height by one because helper count the node for height but you want to count edge
		return getHeightFromRoot(this.root) - 1; //replace this!
	}
	/**
	 * ToString the element in the tree in level traversal style.
	 * @return string
	 */
	public String toString() {
		//your code here
		//Big-O requirement: O(n) amortized, where n is the number of elements in the tree
		
		//an empty tree should return ""
		//otherwise this should return a level order walk of the tree
		//with space separated elements
		if(root == null){
			return "";
		}
		//My Node class
		/**
		 * MyNode class.
		 * @param <V> i
		 */
		class MyNode<V> {
			/**
			 * value of node.
			 */
			V val;
			/**
			 * next.
			 */
			MyNode<V> next = null;
			/**
			 * constructor.
			 * @param data data
			 */
			private MyNode(V data){
				this.val = data;
			}
		}
		//My Queue class.
		/**
		 * queue.
		 * @param <V> i
		 */
		class SelfQueue<V> {
			/**
			 * head.
			 */
			MyNode<V> head = null;
			//enqueue
			/**
			 * enqueue.
			 * @param node head
			 */
			private void addInQueue(TreeNode<T> node){
				@SuppressWarnings({ "rawtypes", "unchecked" })//idk abt this one lol
				MyNode<V> newNode = new MyNode(node);
				MyNode<V> curr = head;
				if(head == null){
					head = newNode;
				}
				else{
					while(curr.next != null){
						curr = curr.next;
					}
					curr.next = newNode;
				}
			}
			//dequeue
			/**
			 * dequeue.
			 * @return removed
			 */
			private MyNode<V> removeInQueue(){
				//MyNode<T> curr = head;
				if(head == null){
					return null;
				}
				else{
					MyNode<V> retNode = head;
					head = head.next;
					retNode.next = null;
					return retNode;
				}
			}
		}
		//for example, the tree:
		//     0
		//    / \
		//   1   2
		//should return the string "0 1 2"
		
		//and the tree:
		//     0
		//    /
		//   1
		//  /
		// 2
		//should also be the string "0 1 2"
		
		//you should use a StringBuilder to create the string
		//Why? String builders are dynamic array lists of characters,
		//while Strings are fixed arrays. So don't append to the end of a
		//string... append to a string builder for better runtime!
		
		//use a "queue" to create a level-order walk of the tree
		//you don't have access to any of the Java utility classes, and
		//you aren't allowed to use arrays here, so you have two options:
		
		//Option 1: Make a method-local linked-list node class and use that as a queue
		
		//Option 2: Since a degenerate tree is a linked list... use that to make a queue!
		//    Option 2 hint: the head and tail of the queue to be a TreeNode<TreeNode<T>>
		SelfQueue<TreeNode<T>> queue = new SelfQueue<>();
		//Add root in queue
		queue.addInQueue(root);
		StringBuilder result = new StringBuilder();
		while(queue.head != null){
			//assign a value to the removed in queue and build string based on it
			MyNode<TreeNode<T>> newNode = queue.removeInQueue();
			TreeNode<T> curr = newNode.val;
			result.append(curr.data).append(" ");
			//add the removed node children to the queue
			if(curr.left!=null){
				queue.addInQueue(curr.left);
			}
			if(curr.right!=null){
				queue.addInQueue(curr.right);
			}
		}
		//System.err.println("queue: " + result);
		
		return result.toString(); //replace this!
	}
	/**
	 * main.
	 * @param args main
	 */
	public static void main(String[] args) {
		TreeNode<Integer> root6 = new TreeNode<>(8);
		root6.left = new TreeNode<>(3);
		root6.right = new TreeNode<>(10);
		root6.left.left = new TreeNode<>(1);
		root6.left.right = new TreeNode<>(6);
		root6.left.right.left = new TreeNode<>(4);
		root6.left.right.right = new TreeNode<>(7);
		root6.right.right = new TreeNode<>(14);
		root6.right.right.left = new TreeNode<>(13);

		


		// LinkedTree tree = new LinkedTree(root6);
		// root.left = (new TreeNode<>(1));
		// root.right = (new TreeNode<>(3));
		// root.left.left = new TreeNode<>(4);

		// System.out.println("Size: " + tree.getSize());
		// System.err.println("Height " + tree.getHeight());
		// System.out.println(tree.toString());

		// Convert.ParentPointer[] tree1 = Convert.parentPointerFormat(args[0]);
				
		// System.out.println("Parent Pointer Tree:");
		// System.out.println(Arrays.toString(tree1));
	}
	
	
	// /**************************************************
	// You may NOT edit anything below this line except to
	// add JavaDocs. This class must remain as-is for grading.
	// **************************************************/
	/**
	 * class treenode.
	 * @param <T> node
	 */
	public static class TreeNode<T> {
		/**
		 * Data of the node.
		 */
		private T data;
		/**
		 * Left child of the node.
		 */
		private TreeNode<T> left;
		/**
		 * right child of the node.
		 */
		private TreeNode<T> right;
		/**
		 * Node color default to white.
		 */
		private Color color = Color.WHITE;
		/**
		 * Contructor 1.
		 */
		public TreeNode() { }
		/**
		 * Contructor 2.
		 * @param data to set for the node
		 */
		public TreeNode(T data) { setData(data); }
		/**
		 * getter of node data.
		 * @return data
		 */
		public T getData() { return this.data; }
		/**
		 * setter of node data.
		 * @param data data to set
		 * @return the old data previously have
		 */
		public T setData(T data) {
			T oldData = this.data;
			this.data = data;
			return oldData;
		}
		/**
		 * Getter of right child of node.
		 * @return rightchild
		 */
		public TreeNode<T> getRight() { return this.right; }
		/**
		 * Setter of right child of node.
		 * @param right child
		 * @return old right child
		 */
		public TreeNode<T> setRight(TreeNode<T> right) {
			TreeNode<T> oldRight = this.right;
			this.right = right;
			return oldRight;
		}
		/**
		 * Getter of left child of node.
		 * @return the left child
		 */
		public TreeNode<T> getLeft() { return this.left; }
		/**
		 * Setter of left child of node.
		 * @param left child to set
		 * @return the old left child previous in there
		 */
		public TreeNode<T> setLeft(TreeNode<T> left) {
			TreeNode<T> oldLeft = this.left;
			this.left = left;
			return oldLeft;
		}
		/**
		 * Get current color of tree node.
		 * @return color
		 */
		public Color getColor() { return color; }
		/**
		 * Set the color of node to the new one and return old one.
		 * @param color to set new
		 * @return old color.
		 */
		public Color setColor(Color color) {
			Color oldColor = this.color;
			this.color = color;
			return oldColor;
		}
		/**
		 * ToString.
		 * @return the string
		 */
		public String toString() { return this.data.toString(); }
	}
}