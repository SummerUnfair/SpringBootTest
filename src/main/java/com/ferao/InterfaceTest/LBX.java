package com.ferao.InterfaceTest;

import java.util.LinkedList;
import java.util.Queue;

public class LBX {
	//坐标类
	private static class Node{
		public double x,y; //六个点
		public Node(double x,double y) {
			this.x=x;
			this.y=y;
		}

	}
	//六边形类
	private static class Hexagon{
		public  Node left_Low_points,low_points,right_Low_points,right_high_points,high_points,left_high_points;//当前菱形六个点//六个点
		public Hexagon(Node left_Low_points, Node low_points,
					   Node right_Low_points, Node right_high_points,
					   Node high_points, Node left_high_points) {
			this.left_Low_points = left_Low_points;
			this.low_points = low_points;
			this.right_Low_points = right_Low_points;
			this.right_high_points = right_high_points;
			this.high_points = high_points;
			this.left_high_points = left_high_points;
		}
	}


	public static Node zuoxiajiao;//左下角边界
	public static Node youshangjiaojiao;//右上角边界
	public static double len;//菱形边长
	public static void main(String[] args) {
		/* ********************初始赋值****************************** */
		Node mid=new Node(0, 0);//初始中心点坐标(0,0)
		len=1;//菱形边长为1
		//由于可以越界，所以设定范围为一个矩形  此处设计为10*10矩形
		//由于可以越界 所以程序把初始的中心点作为边界左下角的点 防止出现空隙
		//如边界左下角是（0,0） 那么起始中心点也是（0,0）
		zuoxiajiao=new Node(0,0);//左下角边界
		youshangjiaojiao=new Node(10,10);//右上角边界


		/* ********************获取第一行所有菱形******************************** */
		//先算出最底下第一行所有菱形
		Queue<Hexagon> queue1=new LinkedList<Hexagon>();
		Hexagon hexagon=null;
		while (true) {
			hexagon=getHexagonMidAndLen(mid);//根据边长和中心点坐标获得一个菱形
			//判断此菱形是否越界
			if (isBorder(hexagon)) {
				break;//如果越界跳出循环
			}
			queue1.offer(hexagon);//把菱形存入队列
			mid.y+=2*0.8*len;//更新下一个中心点位置
		}

		/* ************************************************************ */
		//Left_Low_points,low_points,right_Low_points,right_high_points,high_points,left_high_points
		hexagon=null;
		int index=1;
		Queue<Hexagon> queue2=new LinkedList<Hexagon>();//队列2是存储另一行与queue1 交替使用

		A: while (!queue1.isEmpty()||!queue2.isEmpty()) {
			System.out.println("打印第"+(index++)+"行坐标");

			int id=1;
			while (!queue1.isEmpty()) {
				hexagon=queue1.poll();//出队一个
				System.out.println("第"+(index-1)+"行,"+"第"+(id++)+"个菱形坐标点");
				System.out.println("["+hexagon.left_Low_points.x+","+hexagon.left_Low_points.y+"]");
				System.out.println("["+hexagon.low_points.x+","+hexagon.low_points.y+"]");
				System.out.println("["+hexagon.right_Low_points.x+","+hexagon.right_Low_points.y+"]");
				System.out.println("["+hexagon.right_high_points.x+","+hexagon.right_high_points.y+"]");
				System.out.println("["+hexagon.high_points.x+","+hexagon.high_points.y+"]");
				System.out.println("["+hexagon.left_high_points.x+","+hexagon.left_high_points.y+"]");

				//获取当前出队菱形的左上相邻的菱形
				Hexagon hexagonLeft = getHexagonLeft(hexagon);
				//判断是否越界 如果没有就加入队列2
				if (!isBorder(hexagonLeft)) {
					queue2.offer(hexagonLeft);
				}

				//如果当前出队的菱形是当前行最后一个  那么还需要算与他相邻的右上菱形
				if (queue1.isEmpty()) {
					Hexagon hexagonRight = getHexagonRight(hexagon);
					//判断是否越界 如果没有就加入队列2
					if (!isBorder(hexagonRight)) {
						queue2.offer(hexagonRight);
					}
					continue A ;
				}

			}


			while (!queue2.isEmpty()) {
				hexagon=queue2.poll();//出队一个
				System.out.println("第"+(index-1)+"行,"+"第"+(id++)+"个菱形坐标点");
				System.out.println("["+hexagon.left_Low_points.x+","+hexagon.left_Low_points.y+"]");
				System.out.println("["+hexagon.low_points.x+","+hexagon.low_points.y+"]");
				System.out.println("["+hexagon.right_Low_points.x+","+hexagon.right_Low_points.y+"]");
				System.out.println("["+hexagon.right_high_points.x+","+hexagon.right_high_points.y+"]");
				System.out.println("["+hexagon.high_points.x+","+hexagon.high_points.y+"]");
				System.out.println("["+hexagon.left_high_points.x+","+hexagon.left_high_points.y+"]");

				//获取当前出队菱形的左上相邻的菱形
				Hexagon hexagonLeft = getHexagonLeft(hexagon);
				//判断是否越界 如果没有就加入队列1
				if (!isBorder(hexagonLeft)) {
					queue1.offer(hexagonLeft);
				}

				//如果当前出队的菱形是当前行最后一个  那么还需要算与他相邻的右上菱形
				if (queue2.isEmpty()) {
					Hexagon hexagonRight = getHexagonRight(hexagon);
					//判断是否越界 如果没有就加入队列1
					if (!isBorder(hexagonRight)) {
						queue1.offer(hexagonRight);
					}

				}

			}
		}

	}

	//传入一个菱形 获取此菱形左上的相邻菱形
	public static Hexagon getHexagonLeft(Hexagon hexagon) {
		Node left_high_points=hexagon.left_high_points; //获取当前菱形左上点

		//获得左上相邻菱形中点坐标
		Node mid=new Node(left_high_points.x+len, left_high_points.y);

		//获得左上菱形
		return getHexagonMidAndLen(mid);



	}

	//传入一个菱形 获取此菱形右上的相邻菱形
	public static Hexagon getHexagonRight(Hexagon hexagon) {
		Node right_high_points=hexagon.right_high_points; //获取当前菱形右上点

		//获得右上相邻菱形中点坐标
		Node mid=new Node(right_high_points.x+len, right_high_points.y);

		//获得右上菱形
		return getHexagonMidAndLen(mid);



	}



	//根据中心点获得一个菱形
	public static Hexagon getHexagonMidAndLen(Node mid){
		Node left_Low_points,low_points,right_Low_points,right_high_points,high_points,left_high_points;//当前菱形六个点

		double midX=mid.x; //中心点x
		double midY=mid.y; //中心线y

		high_points=new Node(midX+len, midY);//最上
		low_points=new Node(midX-len, midY);//最下
		left_high_points=new Node(midX+0.5*len, midY-0.8*len);//左上
		left_Low_points=new Node(midX-0.5*len, midY-0.8*len);//左下
		right_high_points=new Node(midX+0.5*len, midY+0.8*len);//右上
		right_Low_points=new Node(midX-0.5*len, midY+0.8*len);//右下

		Hexagon hexagon=new Hexagon(left_Low_points, low_points, right_Low_points, right_high_points, high_points, left_high_points);


		return hexagon;


	}

	//验证这个菱形是所以点否越界
	public static boolean isBorder(Hexagon hexagon) {

		Node high_points=hexagon.high_points;//最上
		//判断坐标是否越界
		if (!(high_points.x<zuoxiajiao.x
				||high_points.x>youshangjiaojiao.x
				||high_points.y<zuoxiajiao.y
				||high_points.y>youshangjiaojiao.y)) {

			//只要有一个点没越界就返回没越界
			return false;
		}



		Node low_points=hexagon.low_points;//最下
		if (!(low_points.x<zuoxiajiao.x
				||low_points.x>youshangjiaojiao.x
				||low_points.y<zuoxiajiao.y
				||low_points.y>youshangjiaojiao.y)) {

			//只要有一个点没越界就返回没越界
			return false;
		}
		Node left_high_points=hexagon.left_high_points;//左上
		if (!(left_high_points.x<zuoxiajiao.x
				||left_high_points.x>youshangjiaojiao.x
				||left_high_points.y<zuoxiajiao.y
				||left_high_points.y>youshangjiaojiao.y)) {

			//只要有一个点没越界就返回没越界
			return false;
		}
		Node left_Low_points=hexagon.left_Low_points;//左下
		if (!(left_Low_points.x<zuoxiajiao.x
				||left_Low_points.x>youshangjiaojiao.x
				||left_Low_points.y<zuoxiajiao.y
				||left_Low_points.y>youshangjiaojiao.y)) {

			//只要有一个点没越界就返回没越界
			return false;
		}
		Node right_high_points=hexagon.right_high_points;//右上
		if (!(right_high_points.x<zuoxiajiao.x
				||right_high_points.x>youshangjiaojiao.x
				||right_high_points.y<zuoxiajiao.y
				||right_high_points.y>youshangjiaojiao.y)) {

			//只要有一个点没越界就返回没越界
			return false;
		}
		Node right_Low_points=hexagon.right_Low_points;//右下
		if (!(right_Low_points.x<zuoxiajiao.x
				||right_Low_points.x>youshangjiaojiao.x
				||right_Low_points.y<zuoxiajiao.y
				||right_Low_points.y>youshangjiaojiao.y)) {

			//只要有一个点没越界就返回没越界
			return false;
		}
		return true; //否则越界

	}
}