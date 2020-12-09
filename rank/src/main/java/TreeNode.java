import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public static List<TreeNode> createBinTree(int[] input) {
        List<TreeNode> nodeList = new LinkedList<TreeNode>();
        // 将数组里的值转换为节点
        for (int nodeIndex = 0; nodeIndex < input.length; nodeIndex++) {
            nodeList.add(new TreeNode(input[nodeIndex]));
        }
        for (int parentIndex = 0; parentIndex < input.length / 2 - 1; parentIndex++) {
            // 左孩子
            nodeList.get(parentIndex).left = nodeList.get(parentIndex * 2 + 1);
            // 右孩子
            nodeList.get(parentIndex).right = nodeList.get(parentIndex * 2 + 2);
        }
        // 最后一个父节点
        int lastParentIndex = input.length / 2 - 1;
        // 左孩子节点
        nodeList.get(lastParentIndex).left = nodeList.get(lastParentIndex * 2 + 1);
        // 右孩子节点
        if (input.length % 2 == 1) {
            nodeList.get(lastParentIndex).right = nodeList.get(lastParentIndex * 2 + 2);
        }
        return nodeList;
    }
}
