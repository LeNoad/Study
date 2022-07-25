package algorithm;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack_BruteForce {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] NM = sc.nextLine().split(" ");
        int n = Integer.parseInt(NM[0]);
        int m = Integer.parseInt(NM[1]);

        String [] cardValueList = sc.nextLine().split(" ");

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < cardValueList.length; i++) {
            list.add( Integer.parseInt(cardValueList[i]));
        }
        int max=0;
        for (int i = 0; i < n-2; i++) {
            for (int j = i+1; j < n-1; j++) {
                for (int k = j+1; k < n; k++) {
                    int sum = list.get(i)+list.get(j)+list.get(k);
                    System.out.println(sum);
                    if(sum <= m){
                        max = Math.max(max, sum);
                    }
                    if(max == m ) {
                        System.out.println(max);
                        return;
                    }
                }
            }
        }
        System.out.println(max);
    }
}