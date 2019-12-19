package DataSave;

/**
 * Output source：控制台
 * function:动态规划
 * 利用简洁的自底向上的递推方式，实现了时间和空间上的最优化
 * Created by FH on 2019/10/9.
 */
public class Dynamic_end2_version {

    //索引，方法区，redis，
    //N代表金矿  W代表工人的数量  F代表黄金数 F（N，W）   g[0]得到的黄金数量  ，p[0] 金矿的用工量
    int getMostGlod(int n,int w ,int []g ,int []p){

        //上一行的结果数组 preResults
        int [] preResults=new int[p.length];

        //当前行的结果数组results
        int [] results=new int [p.length] ;

        //填充边界格子的值

        for (int i=0; i<=n;i++ ){

            if (i<p[0]){

                preResults[i]=0;

            }else {

                preResults[i]=g[0];

            }

        }

        //填充其余各自的值，外层循环是金矿的数量，内层循环是工人数

        for (int i=0; i<n;i++){

            for (int j=0;j<=w;j++){

                if (j<p[i]){

                    results[j]=preResults[j];

                }else {
            //方法区溢出
                    results[j]=Math.max(preResults[j],preResults[j-p[i]]+g[i]);
                }
            }
            preResults=results;
        }
    return  results[n];
    }

}
