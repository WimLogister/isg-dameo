
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Wim
 */
public class MaxList extends ArrayList<MyNode> implements Comparable<List<MyNode>>{
    
    public
    
    public static void main(String[] args) {
        MyNode root = new MyNode("root",null, null);
        MyNode c1 = new MyNode("c1", root, null);
        MyNode c2 = new MyNode("c2", root, new ArrayList<MyNode>());
        MyNode c3 = new MyNode("c3",c1, null);
        
        List<MyNode> rootChildren = new ArrayList<>();
        rootChildren.add(c1); rootChildren.add(c2);
        root.setChildren(rootChildren);
        
        List<MyNode> c1Children = new ArrayList<>();
        c1Children.add(c3);
        
        System.out.println(maxList(root, new ArrayList<MyNode>()));
        
    }
    
    

    @Override
    public int compareTo(List<MyNode> o) {
        if (this.size() > o.size()) return 1;
        if (this.size() < o.size()) return -1;
        return 0;
                
    }
           
}

    class MyNode {
        String name;
        MyNode parent;
        List<MyNode> children;

        public MyNode(String name, MyNode parent, List<MyNode> children) {
            this.name = name;
            this.parent = parent;
            this.children = children;
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

        @Override
        public String toString() {
            System.out.println(this.name);
        }
        
    }