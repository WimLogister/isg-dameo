package dameo.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class DeepestNodeFinder {
    
    MyNode deepestNode;
    MyNode root;

    public DeepestNodeFinder(MyNode root) {
        this.root = root;
    }
    
    public void startSearch() {
        this.deepestNode = root;
        findDeepestNode(root);
    }
    
    public void findDeepestNode(MyNode node) {
        if (node.children.isEmpty()) {
            if (node.depth > deepestNode.depth) {
                deepestNode = node;
            }
        }
        else {
            for (MyNode child : node.children) {
                child.depth = node.depth + 1;
                child.parent = node;
                findDeepestNode(child);
            }
        }
    }

    public MyNode getDeepestNode() {
        return deepestNode;
    }
    
    public static void main(String[] args) {
        MyNode root = new MyNode("root",null, null);
        MyNode c1 = new MyNode("c1", root, null);
        MyNode c2 = new MyNode("c2", root, new ArrayList<MyNode>());
        MyNode c3 = new MyNode("c3",c1, new ArrayList<MyNode>());

        List<MyNode> rootChildren = new ArrayList<>();
        rootChildren.add(c1); rootChildren.add(c2);
        root.setChildren(rootChildren);

        List<MyNode> c1Children = new ArrayList<>();
        c1Children.add(c3);
        c1.setChildren(c1Children);
        
        DeepestNodeFinder finder = new DeepestNodeFinder(root);
        finder.startSearch();
        
        MyNode currentNode = finder.getDeepestNode();
        Stack<MyNode> stack = new Stack<>();
        while (currentNode != null) {
            stack.push(currentNode);
            currentNode = currentNode.parent;
        }
        
        while(!stack.empty()) {
            System.out.println(stack.pop().toString());
        }

    }
    
}
class MyNode {
    String name;
    MyNode parent;
    List<MyNode> children;
    int depth;

    public MyNode(String name, MyNode parent, List<MyNode> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.depth = 0;
    }

    public MyNode getParent() {
        return parent;
    }

    public List<MyNode> getChildren() {
        return children;
    }

    public void setParent(MyNode parent) {
        this.parent = parent;
    }

    public void setChildren(List<MyNode> children) {
        this.children = children;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return String.format("<%s>", this.name);
    }
}