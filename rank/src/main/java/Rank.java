

public class Rank {

    public static String[] reRankTwoPointers(String[] input) {
        if(input == null || input.length == 0) {
            return new String[0];
        }
        int i = 0;
        int left = 0;
        int right = input.length - 1;
        while(i <= right) {
            if(input[i].equals("r")) {
                String t = input[i];
                input[i] = input[left];
                input[left] = t;
                i++;
                left++;
            }
            else if(input[i].equals("g")) {
                i++;
            }
            else if(input[i].equals("b")) {
                String t1 = input[i];
                input[i] = input[right];
                input[right] = t1;
                right--;
            }
        }
        return input;
    }

    public static String[] reRankCount(String[] input) {
        if(input == null || input.length == 0) {
            return new String[0];
        }

        int r=0,b=0,g=0;
        for (int i=0;i< input.length;i++){
            if(input[i].equals("r")){
                r++;
            }else if(input[i].equals("b")){
                b++;
            }else if(input[i].equals("g")){
                g++;
            }

        }

        for (int i=0;i< input.length;i++){
            if(r!=0){
                input[i] = "r";
                r--;
            }else if(g!=0){
                input[i] = "g";
                g--;
            }else if(b!=0){
                input[i] = "b";
                b--;
            }
        }

        return input;
    }

    public static void main(String[] args) {
        String[] input = new String[]{"r", "r", "b", "g", "b", "r", "g"};

        String[] list1 = reRankTwoPointers(input);
        System.out.println("方法1排序结果:");
        for (int i=0;i< list1.length;i++){
            System.out.print(list1[i]);
        }

        String[] list2 = reRankCount(input);
        System.out.println("\n方法2排序结果:");
        for (int i=0;i< list2.length;i++){
            System.out.print(list2[i]);
        }
    }
}
