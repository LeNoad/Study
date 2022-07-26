package algorithm;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack_BruteForce {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] NM = sc.nextLine().split(" ");
        int n = Integer.parseInt(NM[0]); // 카드의 개수
        int m = Integer.parseInt(NM[1]); // 카드의 합 목표

        String [] cardValueList = sc.nextLine().split(" ");

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < cardValueList.length; i++) {
            list.add( Integer.parseInt(cardValueList[i]));
        }
        int max=0;
        /* 3개의 카드를 고르고 첫 번째 카드에서는 n-2 까지 순환 */
        for (int i = 0; i < n-2; i++) {
            /* 두 번째 카드는 첫 번째 다음부터 n-1 까지 순환 */
            for (int j = i+1; j < n-1; j++) {
                /* 세 번째 카드는 두 번째 카드 다음부터 n까지 순환 */
                for (int k = j+1; k < n; k++) {
                    int sum = list.get(i)+list.get(j)+list.get(k);
                    System.out.println(sum);

                    /* 문제상 3장의 합은 카드의 합보다 클수가 없음 따라서 작은 경우만 계산 */
                    if(sum <= m){
                        max = Math.max(max, sum);
                    }
                    /* max == m 이 가장 가까운 답이므로 출력 후 종료 */
                    if(max == m ) {
                        System.out.println(max);
                        return;
                    }
                }
            }
        }
        System.out.println(max);
        sc.close();
    }
}