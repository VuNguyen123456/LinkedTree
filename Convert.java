//Note: You have access to all of Java IO and Java Utility here. Make good use of them!
//No additional imports allowed.

import java.io.*;
import java.util.*;

/**************************************************
Random advice and tips:
(1) Don't forget to close your IO streams!
(2) Just because you _can_ implement something in
    the same number of lines as me doesn't mean
	that's the best or only solution. Do you!
(3) The class "TreeNode" is called "LinkedTree.TreeNode"
	from this class
**************************************************/


class Convert {
	// /************************************************************************
	// You may add additional PRIVATE methods. You may NOT add static class
	// variables (this is for your own good... there is no "good" use of class
	// variables here and a very high chance of losing points if you don't
	// know how static "really" works... so don't do it!
	// ************************************************************************/
	// private int lookForRootIndex(int[] arr){
	// 	for(int i = 0; i < arr.length; i++){
	// 		if(arr[i] == -1){
	// 			return i;
	// 		}
	// 	}
	// 	return 0; // a little bit sketchy here.
	// }
	
	// /********************************************************************************/
	// /*                           Parent Pointer Format                              */
	// /********************************************************************************/
	/**
	 * Convert the txt file into a parent pointer array.
	 * @param filename to look
	 * @return array of parent pointer
	 * @throws IOException when exception 
	 */
	public static ParentPointer[] parentPointerFormat(String filename) throws IOException {
		//your code here
		//Big-O requirement: O(n), where n is the largest ID node in the tree
		
		//Hint: My solution is ~12 lines (including two helper functions, not but not
		//		including headers, space, and comments)
		
		//Note: Any values NOT in the tree (x/x in the file) should be null in the
		//		returned array of ParentPointer objects.
		FileReader file = new FileReader(filename);
        ParentPointer[] pplist;
        try (BufferedReader buffer = new BufferedReader(file)) {
									//Convert the 2 line in file into 2 list.
        	String[] storeIds = buffer.readLine().split(" ");
            String[] positionIds = buffer.readLine().split(" ");
            int[] ids = new int[storeIds.length];
												//Make it an array of int.
            for(int i = 0; i < ids.length; i++){
                if(storeIds[i].equals("x")){
                    continue;
                }
                ids[i] = Integer.parseInt(storeIds[i]);
                //System.out.print(ids[i] + " ");
            }
            //have node and it's position as list.
            pplist = new ParentPointer[ids.length];
												//Goes through the 2 list and make them into paprentpointer
            for(int i = 0; i<ids.length; i++){
																//if id is -1 then it's the root 
                if(ids[i] == -1){
                    pplist[i] = new ParentPointer(-1);

                }
																//if it's have L then isleft is true else false
                else if(positionIds[i].equals("L")){
                    pplist[i] = new ParentPointer(ids[i], true);

                }
                else if(positionIds[i].equals("R")){
                    pplist[i] = new ParentPointer(ids[i], false);

                }
                else if(positionIds[i].equals("x") && storeIds[i].equals("x")){
                    pplist[i] = null;

                }
                else{
                    pplist[i] = new ParentPointer(ids[i]);
                }
                    
            }
        }
		return pplist; //replace this!
	}
	
	// /********************************************************************************/
	// /*                                Linked Format                                 */
	// /********************************************************************************/
	/**
	 * Convert file into treeLinked structure.
	 * @param filename to get
	 * @return array
	 * @throws IOException to throw
	 */
	@SuppressWarnings("unchecked")
	public static LinkedTree<Integer> treeLinkedFormat(String filename) throws IOException {
		//Note: you _may not need_ the suppress warnings above, but it is there _if_ you need it
		
		//your code here
		//Big-O requirement: Can be done in O(n), where n is the largest ID node in the tree
		//					 required to be done in no more than O(n^2)
		
		//Hint: My solution is ~12 lines (including one helper function, not but not
		//		including headers, space, and comments)
		
		//Hint 2: 
		ParentPointer[] arr = Convert.parentPointerFormat(filename);
		LinkedTree.TreeNode<Integer> root = new LinkedTree.TreeNode<>();
		LinkedTree.TreeNode<Integer> leftChild = new LinkedTree.TreeNode<>();
		LinkedTree.TreeNode<Integer> rightChild = new LinkedTree.TreeNode<>();
		//Find root
		int checkRoot = 0;
		//find root
		for(int i = 0; i < arr.length; i++){
			if(arr[i] == null){
				continue;
			}
			if(arr[i].parentId == -1){
				root.setData(i);
				checkRoot = 1;
				//System.out.println("rootdata: "+ root.getData());
			}
		}
		if(checkRoot == 0){
			return null;
		}
		//Mkae the rest of the tree
		//Level travelsar
		Queue<LinkedTree.TreeNode<Integer>> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty()){
			LinkedTree.TreeNode<Integer> newnode = queue.remove();
			//System.out.println("newNodedata: "+ newnode.getData());
			//Goes through to find children and setting them up in the linkedtree according to their information of Parentpointer
			for(int i = 0; i < arr.length; i++){
				if(arr[i] == null){
					continue;
				}
				if(newnode.getData() == arr[i].parentId && arr[i].isLeft == true){
					leftChild = new LinkedTree.TreeNode<>(i);
					newnode.setLeft(leftChild);
				}
				else if(newnode.getData() == arr[i].parentId && arr[i].isLeft == false){
					rightChild = new LinkedTree.TreeNode<>(i);
					newnode.setRight(rightChild);
				}
			}
			// Add the children into the queue to later goes through when level travering
			if(newnode.getLeft() != null){
				queue.add(newnode.getLeft());
			}
			if(newnode.getRight() != null){
				queue.add(newnode.getRight());
			}
		}

		LinkedTree<Integer> tree = new LinkedTree<>(root);
		return tree;
	}
	
	// /********************************************************************************/
	// /*                               Array Format                                   */
	// /********************************************************************************/
	/**
	 * Storing tree from file into array.
	 * @param filename to get
	 * @return Array of integer
	 * @throws IOException exception
	 */
	@SuppressWarnings("unchecked")
	public static Integer[] treeArrayFormat(String filename) throws IOException {
		//Note: you _may not need_ the suppress warnings above, but it is there _if_ you need 
		
		//your code here
		//Big-O requirement: Can be done in O(2^n), where n is the height of the tree + 1,
		//		no required big-O for this method, just make it work!
		
		//Hint: My solution is ~12 lines (including one helper function, not but not
		//		including headers, space, and comments)... Yes, they really did all
		//		come out as 12 lines, that's not a copy-and-paste error
		
		ParentPointer[] arr = Convert.parentPointerFormat(filename);
		//LinkedTree.TreeNode<Integer> root = new LinkedTree.TreeNode<>();
		LinkedTree.TreeNode<Integer> leftChild = new LinkedTree.TreeNode<>();
		LinkedTree.TreeNode<Integer> rightChild = new LinkedTree.TreeNode<>();
		//Make 2 queue, 1 for the node and one to store the index of the node
		Queue<LinkedTree.TreeNode<Integer>> queue = new LinkedList<>();
		Queue<Integer> indexQueue = new LinkedList<>();
		LinkedTree<Integer> tree = treeLinkedFormat(filename);
		//Make sure the array have right amount of length
		int size = (int) Math.pow(2, tree.getHeight()+1) - 1;
		Integer[] result = new Integer[size];
		//Add root and it's index in queue
		LinkedTree.TreeNode<Integer> root = tree.getRoot();
		queue.add(root);
		indexQueue.add(0);
		result[0] = root.getData();
		//Level order traversal
		while(!queue.isEmpty()){
			//Assigned the remove node and index in the 2 queue to perform on the arrayTree
			LinkedTree.TreeNode<Integer> newNode = queue.remove();
			Integer retIndex = indexQueue.remove();
			//Adding them in the arrayTree and also adding in it's children and their corresponding index in queues
			result[retIndex] = newNode.getData();
			if(newNode.getLeft()!=null){
				queue.add(newNode.getLeft());
				indexQueue.add(2*retIndex+1);
			}
			if(newNode.getRight()!=null){
				queue.add(newNode.getRight());
				indexQueue.add(2*retIndex+2);
			}
		}
		//System.err.println("arr.lenght " + arr.length);
		return result; //replace this!
	}
	/**
	 * Get the load of the tree.
	 * @param <T> type
	 * @param tree to array
	 * @return type
	 */
	public static <T> double arrayLoad(T[] tree) {
		//Note: This is a generic static method if you've never seen one before!
		//		What's special? This _class_ is not generic, only this one _method_.
		
		//This method will be used to calculate the load (percentage of use) for
		//both ParentPointer[] and Integer[] trees, do NOT consider "extra" nulls
		//after the last "real" item when performing this calculation.
		
		//your code here
		//Big-O requirement: O(n) where n is the length of the input array "tree"
		
		//Hint: My solution is ~6 lines (not including headers and comments)
		double numberOfElement = 0;
		double totalUsed = 0;
		//find total element and total used space
		for(int i = 0; i < tree.length; i++){
			if(tree[i] == null){
				continue;
			}
			else{
				numberOfElement++;//find all element
				totalUsed = i;//find final index of element
			}
		}
		totalUsed += 1;//Add 1 because index start at 0
		return numberOfElement/totalUsed;
	}
	
	// /********************************************************************************/
	// /*                             Merge Operations                                 */
	// /* You must implement ONE of these options (each option contains three methods) */
	// /* IMPORTANT: You may NOT edit/destroy any of the input trees in these methods! */
	// /********************************************************************************/
	
	
	//Option 1: Merging using linked structures
	/**
	 * megre.
	 * @param tree1 1
	 * @param tree2 2
	 * @return 3
	 */
	@SuppressWarnings("unchecked")
	public static LinkedTree<Integer> merge(LinkedTree<Integer> tree1, LinkedTree<Integer> tree2) {
		//Note: you _may not need_ the suppress warnings above, but it is there _if_ you need 
		
		//your code here
		//Big-O requirement: Can be done in O(n), where n is the size of the biggest tree
		//		no required big-O for this method, just make it work!
		
		throw new UnsupportedOperationException(); //replace this if you choose option 1
	}
	/**
	 * dup.
	 * @param tree 1
	 * @return 2
	 */
	public static boolean containsDuplicates(LinkedTree<Integer> tree) {
		//your code here
		//Big-O requirement: Can be done in O(n), where n is the size of the biggest tree
		//		no required big-O for this method, just make it work!
		
		throw new UnsupportedOperationException(); //replace this if you choose option 1
	}
	/**
	 * pp.
	 * @param tree 1 
	 * @return 2
	 */
	@SuppressWarnings("unchecked")
	public static ParentPointer[] toParentPointer(LinkedTree<Integer> tree) {
		//Note: you _may not need_ the suppress warnings above, but it is there _if_ you need 
		
		//your code here
		//Big-O requirement: Can be done in O(n), where n is the largest ID node in the tree
		//		no required big-O for this method, just make it work!
		
		throw new UnsupportedOperationException(); //replace this if you choose option 1
	}
	
	
	//Option 2: Merging using array structures
	/**
	 * Mege 2 input tree.
	 * @param tree1 to merge
	 * @param tree2 to merge
	 * @return merged Tree
	 */
	//you may NOT edit/destroy either of the input trees
	public static Integer[] merge(Integer[] tree1, Integer[] tree2) {
		//your code here
		//Big-O requirement: Can be done in O(n), where n is biggest input array
		//		no required big-O for this method, just make it work!
		int biggerLength = 0;
		int smallerLength = 0;
		Integer[] biggerTree;
		Integer[] smallerTree;
		//Find which tree is bigger and which one is smaller and their lengtth 
		if(tree1.length > tree2.length){
			biggerLength = tree1.length;
			smallerLength = tree2.length;
			biggerTree = tree1;
			smallerTree = tree2;
		}
		else{
			biggerLength = tree2.length;
			smallerLength = tree1.length;
			biggerTree = tree2;
			smallerTree = tree1;
		}
		Integer[] mergedTree = new Integer[biggerLength];
		//Copy the Bigg tree
		for(int i = 0; i < biggerTree.length; i++){
			mergedTree[i] = biggerTree[i];
		}
		//merging the small tree into big tree unless they are incompatable
		for(int i = 0; i < smallerLength; i++){
			if(mergedTree[i] == null){
				mergedTree[i] = smallerTree[i];
			}
			else if(smallerTree[i]!= null && mergedTree[i].equals(smallerTree[i]) == false){
				//can't merge
				return null;
			}
		}
		return mergedTree;
		//Hint: My solution is ~7 lines (not including headers, space, and comments)
		//throw new UnsupportedOperationException(); //replace this if you choose option 2
	}
	/**
	 * If tree contain dup.
	 * @param tree to check
	 * @return t/f
	 */
	public static boolean containsDuplicates(Integer[] tree) {
		//your code here
		//Big-O requirement: Can be done in O(n), where n is the number of nodes in the tree
		//		no required big-O for this method, just make it work!
		ArrayList<Integer> list = new ArrayList<>();
		//Loop thorugh and find if the element is duplicated or not expect the null elements
		for(int i = 0; i < tree.length; i++){
			if(list.contains(tree[i]) == false){
				if(tree[i] == null){
					continue;
				}
				else{
					list.add(tree[i]);
				}
			}
			else{
				return true;
			}
		}
		return  false;
		//Hint: My solution is ~6 lines (not including headers, space, and comments)
		//throw new UnsupportedOperationException(); //replace this if you choose option 2
	}
	/**
	 * From Array Integer into parentPointer.
	 * @param tree inputed to get ppointer
	 * @return parentpointer list
	 */
	public static ParentPointer[] toParentPointer(Integer[] tree) {
		//your code here
		//Big-O requirement: Can be done in O(n), where n is the largest ID node in the tree
		//		no required big-O for this method, just make it work!
		
		//Hint: My solution is ~9 lines (not including headers, space, and comments)
		//throw new UnsupportedOperationException(); //replace this if you choose option 2
		if(containsDuplicates(tree)){
			return null;
		}
		int max = tree[0];
		//Find length of paprent pointer list
		for(int i = 0; i < tree.length; i++){
			if(tree[i] == null){
				continue;
			}
			if(tree[i] > max){
				max = tree[i];
			}
		}
		ParentPointer[] pplist = new ParentPointer[max+1];
		//Loop thorugh arrayTree and create parentpointer
		//index of parent pointer should be value of arrayTree
		//Could find the parent of the arrayTree elemnt through odd or even index and the the undo of the formula of (2i +1 or 21 + 2)
		pplist[tree[0]] = new ParentPointer(-1);
		for(int i = 0; i < tree.length; i++){
			if(i == 0 || tree[i] == null){
				continue;
			}
			else{
				//Left
				if(i % 2 == 1){
					pplist[tree[i]] = new ParentPointer(tree[(i-1)/2], true);
				}
				else{
					pplist[tree[i]] = new ParentPointer(tree[(i-2)/2], false);
				}
			}
		}
		
		return pplist;
	}
	/**
	 * Main.
	 * @param args inp
	 * @throws IOException except
	 */
	public static void main(String[] args) throws IOException {
		// Convert.ParentPointer[] tree1 = Convert.parentPointerFormat("..\\sample-inputs\\tree5.txt");
		// LinkedTree<Integer> tree2 = Convert.treeLinkedFormat("..\\sample-inputs\\tree1.txt");
		// System.out.println("");
		// System.out.println(tree2.toString());
		//System.out.println("Parent Pointer Tree:");
		//System.out.println(Arrays.toString(tree1));
		// Integer[] tree2 = Convert.treeArrayFormat("..\\sample-inputs\\tree5.txt");
		// System.out.println("");
		// for(int i = 0; i < tree2.length; i++){
		// 	System.out.print(tree2[i] + " ");
		// }

	}
	
	// /********************************************************************************/
	// /*                              Support Classes                                 */
	// /*    You may NOT edit anything below this line except to add JavaDocs.         */
	// /*    This class must remain as-is for grading.                                 */
	// /********************************************************************************/
	/**
	 * Class parent pointer.
	 */
	public static class ParentPointer {
		/**
		 * id of parent.
		 */
		int parentId;
		/**
		 * is it the left child or not.
		 */
		boolean isLeft; //note: it doesn't matter what you set this to for root, it will be ignored
		/**
		 * Contructor 1.
		 */
		public ParentPointer() {  }
		/**
		 * Contructor 2.
		 * @param parentId id
		 */
		public ParentPointer(int parentId) { this.parentId = parentId; }
		/**
		 * Constructor3.
		 * @param parentId id
		 * @param isLeft left or right
		 */
		public ParentPointer(int parentId, boolean isLeft) { this(parentId); this.isLeft = isLeft; }
		/**
		 * toString of parentpointer.
		 * @return string 
		 */
		public String toString() { return parentId + ((parentId == -1) ? "" : (isLeft ? "L" : "R")); }
	}
}