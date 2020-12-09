import java.util.ArrayList;
import java.util.List;

public class AllPath {

    public static List<String> findAllPath(TreeNode root) {
        List<String> paths = new ArrayList<>();

        if(root == null) {
            return paths;
        }

        List<String> leftPaths = findAllPath(root.left);
        List<String> rightPaths = findAllPath(root.right);

        for(String path : leftPaths) {
            paths.add(root.val + "->" + path);
        }

        for(String path : rightPaths) {
            paths.add(root.val + "->" + path);
        }

        if(paths.isEmpty()) {
            paths.add("" + root.val);
        }

        return paths;
    }

    public static void main(String[] args) {
        int[] num1={1,2,3,4,5,6,7,8,9};
        int[] num2={1,2,3,4,5,6};
        List<TreeNode> list1=TreeNode.createBinTree(num1);
        List<TreeNode> list2=TreeNode.createBinTree(num2);
        List<String> paths1 = findAllPath(list1.get(0));
        List<String> paths2 = findAllPath(list2.get(0));

        System.out.println("path1为：");
        for(String path : paths1) {
            System.out.println(path);
        }

        System.out.println("\npath2为：");
        for(String path : paths2) {
            System.out.println(path);
        }
    }
}
