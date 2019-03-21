package reuven.java.datastructures;

public class BST {
	private Node root;

	public BST() {
		root = null;
	}

	private class Node{
		private int data;
        private Node left, right;		//left and right subtrees

        public Node(int data) {			//every node has 3 parts: data value, pointer to left child and pointer to right child
            this.data = data;
            left = null;
            right = null;
        }
	}

	public boolean isEmpty() {
    	return root == null;
    }

	public void clear() {
		root = null; 					//changes address of root in memory and loses pointers to other tree leaf nodes
	}

    public void insert(int item) {
    	Node newNode = new Node(item);	//creating a new node passing in it's value

    	if(root == null) {
			root = newNode;
			return;
		}

    	Node current = root;
		Node parent = null;

		while(true){
			parent = current;

			if(item < current.data){
				current = current.left;

				if(current == null){
					parent.left = newNode;
					return;
				}
			}else{
				current = current.right;

				if(current == null){
					parent.right = newNode;
					return;
				}
			}
		}
    }

    public boolean find(int item) {
    	Node current = root;

    	while(current != null){
			if(current.data == item){
				return true;
			}else if(current.data > item){
				current = current.left;
			}else{
				current = current.right;
			}
		}
		return false;
    }

    public boolean delete(int item){
		Node parent = root;
		Node current = root;
		boolean isLeftChild = false;
		
		while(current.data != item){
			parent = current;
			
			if(current.data > item){
				isLeftChild = true;
				current = current.left;
			}else{
				isLeftChild = false;
				current = current.right;
			}
			
			if(current == null){			//if item to delete doesn't exist in tree
				return false;
			}
		}

		/*
		 * Case 1: node to be deleted has no children
		 */
		if(current.left == null && current.right == null){
			if(current == root){
				root = null;
			}
			
			if(isLeftChild == true){
				parent.left = null;
			}else{
				parent.right = null;
			}
		}
		
		/*
		 * Case 2 : node to be deleted has only one child
		 */
		else if(current.right == null){
			if(current == root){
				root = current.left;
			}else if(isLeftChild){
				parent.left = current.left;
			}else{
				parent.right = current.left;
			}
		}else if(current.left == null){	
			if(current == root){
				root = current.right;
			}else if(isLeftChild){
				parent.left = current.right;
			}else{
				parent.right = current.right;
			}
		}else if(current.left != null && current.right != null){
			
			//minimum element in the right sub tree
			Node successor = getSuccessor(current);
			
			if(current == root){
				root = successor;
			}else if(isLeftChild){
				parent.left = successor;
			}else{
				parent.right = successor;
			}			
			
			successor.left = current.left;
		}		
		return true;
	}
	
	public Node getSuccessor(Node deleleNode){
		Node successsor = null;
		Node successsorParent = null;
		Node current = deleleNode.right;

		while(current != null){
			successsorParent = successsor;
			successsor = current;
			current = current.left;
		}
		
		/*
		 * check if successor has the right child, it cannot have left child for sure
		 * if it does have the right child, add it to the left of successorParent.
		 */
		if(successsor != deleleNode.right){
			successsorParent.left = successsor.right;
			successsor.right = deleleNode.right;
		}

		return successsor;
	}

	/*
	 * In-order traversal means to visit the left branch, 
	 * the current node and the right branch.
	 */
    public void inOrderTraversal() {
    	Node root = this.root;
    	_inOrderTraversal(root);
    }

    private void _inOrderTraversal(Node root){			//nodes are visited in ascending order
    	if(root != null){
			_inOrderTraversal(root.left);
			System.out.print(" " + root.data);
			_inOrderTraversal(root.right);
		}
    }

    /*
     * Pre-order traversal visits the current node before 
     * its child nodes.
     */
    public void preOrderTraversal() {
    	Node root = this.root;
    	_preOrderTraversal(root);
    }

    private void _preOrderTraversal(Node root) {		//root is always the first node visited
	    if (root != null) {
	    	System.out.print(" " + root.data);
		    _preOrderTraversal(root.left);
		    _preOrderTraversal(root.right);
	    }
    }

//Post-order traversal visits the current node after its child nodes
    public void postOrderTraversal() {
    	Node root = this.root;
    	_postOrderTraversal(root);
    }

    private void _postOrderTraversal(Node root) {		//root is always the last node visited
	    if (root != null) {
		    _postOrderTraversal(root.left);
		    _postOrderTraversal(root.right);
		    System.out.print(" " + root.data);
	    }
    }
}
