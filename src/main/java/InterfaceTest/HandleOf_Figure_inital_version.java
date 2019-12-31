package InterfaceTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/*
* 六边形网格的生成
* 入参：x,y 坐标点,z 网格大小
* 出参：批量坐标点（六边形六个点的坐标为一组,生成100组（可控）
* */
public class HandleOf_Figure_inital_version {

    static List<String[]> list=new ArrayList<String[]>();

    static int countJ=1;
    static int countO=0;

    //static int count=-1;
    public static void main(String[] args) throws FileNotFoundException {

        PrintStream out=new PrintStream(new File("C:\\Users\\user\\Desktop\\六边形.txt"));
        System.setOut(out);

//        double x=121.72683219676694;
//        double y=31.120248165886675;

        double x=31.120248165886675;
        double y=121.72683219676694;

        double X_Figure=y;
        double Y_Figure=x;
        final double len=0.005;


/////////////////////////////////////////横向这种形式最简单
        for (int i=0;i<10;i++){

            double X_Figures=X_Figure+0.866*len*2*i;

            String[]a = getFigure(X_Figures,Y_Figure,len);

            list.add(a);

        }

        for (String[] B: list){

            for (String C : B){

                System.out.println(C);

            }

            System.out.println();

        }
///////////////////////////////////////////////////////纵向思路
        for (int i=1;i<10;i++){

            if (i%2 == 0){

                list.clear();

                    double portrait_X=X_Figure+0.866*len;

                    double portrait_Y=Y_Figure+(2*countO+1)*3/2*len+len/2;

                    countO++;

                for (int L=0;L<10;L++){

                    double portrait_Xs=portrait_X+0.866*len*2*L;

                    String[]  c = getFigure(portrait_Xs,portrait_Y,len);

                    list.add(c);

                }

                for (String[] B: list){

                    for (String C : B){

                        System.out.println(C);

                    }
                    System.out.println();
                    //System.out.println("偶数纵向//////////////////////////");

                }
            }else{

                list.clear();

                    double portrait_Y=Y_Figure+3*countJ*len;

                countJ++;

                for (int L=0;L<10;L++){

                    double portrait_Xs=X_Figure+0.866*len*2*L;

                    String[]  b = getFigure(portrait_Xs,portrait_Y,len);

                    list.add(b);

                }

                for (String[] B: list){

                    for (String C : B){

                        System.out.println(C);

                    }

                    System.out.println();
                    //System.out.println("奇数纵向//////////////////////////");

                }
            }
        }

    }

    //正六边形六边点坐标获取
    public static String[]   getFigure(double x,double y,double len){
        ///////////////////////////////////////////////////////////
        /**
         * 顶点及底点
         */

        //顶点坐标
        String high_points="["+(y+len)+","+x+"]";

        //底点坐标
        double[] Low_point={x,y-len};
        String Low_points="["+(y-len)+","+x+"]";

        ///////////////////////////////////////////////////////////
        /**
         * 左边：顶点及底点
         */
        double[] Left_high_point={x-(len*0.866),y+(0.5*len)};
        String Left_high_points="["+(y+(0.5*len))+","+(x-(len*0.866))+"]";
        double[] Left_Low_point={x-(len*0.866),y-(0.5*len)};

        String Left_Low_points="["+(y-(0.5*len))+","+(x-(len*0.866))+"]";

        ///////////////////////////////////////////////////////////
        /**
         * 右边：顶点及底点
         */

        double[] right_high_point={x+(len*0.866),y+(0.5*len)};

        String right_high_points="["+(y+(0.5*len))+","+(x+(len*0.866))+"]";

        ///////////////////////////////////////////////////////////
        /**
         * 坐标原点，六边形打印
         */

        double[] right_Low_point={x+(len*0.866),y-(0.5*len)};

        String right_Low_points="["+(y-(0.5*len))+","+(x+(len*0.866))+"]";

        String [] point={Left_Low_points,Low_points,right_Low_points,right_high_points,high_points,Left_high_points};

            return  point;
    }

}
