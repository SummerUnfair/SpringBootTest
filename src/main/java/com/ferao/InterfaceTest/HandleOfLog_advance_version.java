package com.ferao.InterfaceTest;

import java.io.*;
import java.util.*;

/**
 * Information sources：日志文本
 * Output source：文本
 * function:获取各接口总数量及各状态码总数量,及各接口所属各状态码数量
 * Created by FH on 20190910.
 */
public class HandleOfLog_advance_version {

	private static class Node implements Comparable<Node>{
		String name;
		double count;
		int code200 ,code500; //各个状态码出现次数
		int other;
		public Node(String name,Long count) {
			this.count=count;
			this.name=name;
		}
		public Node(String name, double count, int code200, int code500,int other) {

			this.name = name;
			this.count = count;
			this.code200 = code200;
			this.code500 = code500;
			this.other = other;
		}

		@Override
		public int compareTo(Node o) {
			//return (int) (this.count-o.count);//从小到大
			return (int) (o.count-this.count);//从大到小
		}
	}

	public static void main(String[] args) throws Exception {

		String path1="C:\\Users\\user\\Desktop\\日志测试4.txt";
		String path2="C:\\Users\\user\\Desktop\\log4.txt";
		getApiUseCount(path1, path2);
		getLongCount(path1, path2);

	}

	/**
	 *
	 * @param path1 日志路径
	 * @param path2 保存路径
	 * @throws Exception
	 */
	public static void getApiUseCount(String path1,String path2) throws Exception {

		PrintStream out=new PrintStream(new File(path2));
		System.setOut(out);

		long startTime=System.currentTimeMillis();
		Reader in = new FileReader(new File(path1));
		BufferedReader reader = new BufferedReader(in);

		String s = "";
		int headindex , doindex  , statusCodeIndex;
		long code200 = 0,code500=0,code403=0,code404=0,code304=0,other=0;

		String str=null,str2=null;
		long sum1=0;

		Map<String,Long> map =new HashMap<String, Long>();
		while ((s = reader.readLine()) != null) {
			sum1++;
			doindex=s.indexOf(".do");

			if ((headindex=s.indexOf("POST")) != -1) {

				if (doindex!=-1) {

					str=s.substring(headindex+5,doindex);

					if(!map.containsKey(str)){

						map.put(str,1l);

					}else{

						map.put(str,map.get(str)+1);
					}
				}
			} else if ((headindex=s.indexOf("GET")) != -1) {

				if (doindex!=-1) {
					str=s.substring(headindex+4,doindex);
					//map.put(str, map.getOrDefault(str, 0l)+1);
					if(!map.containsKey(str)){

						map.put(str,1l);

					}else{

						map.put(str,map.get(str)+1);
					}
				}
			}
			statusCodeIndex=s.lastIndexOf("HTTP");
			str = s.substring(statusCodeIndex,s.length()-1);
			if (str.contains("200")) {

				code200++;
			}else if (str.contains("500")) {
				code500++;
			}else if (str.contains("403")) {
				code403++;
			} else if (str.contains("404")) {
				code404++;
			}else if (str.contains("304")) {
				code304++;
			}else {
				//其他状态
				//System.out.println(str);
				other++;
			}
		}
		long sum=0;

		List<Node> list=new ArrayList<Node>();

		for (String key : map.keySet()) {
			//System.out.println(key+" : "+map.get(key));
			list.add(new Node(key, map.get(key)));
			sum+=map.get(key);
		}

		Collections.sort(list);

		for (Node n : list) {
			System.out.println(n.name+"\t\t"+"该接口被调用："+n.count+"次");
		}

		List<Node> list2=new ArrayList<>();
		list2.add(new Node("200", code200));
		list2.add(new Node("500", code500));
		list2.add(new Node("403", code403));
		list2.add(new Node("404", code404));
		list2.add(new Node("304", code304));
		list2.add(new Node("其他", other));

		Collections.sort(list2);//排序

		long endTime0=System.currentTimeMillis();
		long S=endTime0-startTime;
		float W=(float) code200/(float) (sum1-code403);    //成功率
		float E=(float) (code500+code404+code304)/(float) (sum1-code403);
		System.out.println();


		for (Node node : list2) {
			//writer.append(node.name+" 状态码次数\t:\t"+node.count+"\r\n");
			System.out.println(node.name+" 状态码次数:"+node.count);
		}
		System.out.println("注：此处为文本全量状态码数据，包含HTML，CSS，JS，图片等");
		System.out.println();
		System.out.print("接口调用成功率:"+W+"\t");
		System.out.println("注:状态码200除以（容量数-403状态数）");
		System.out.print("接口调用失败率:"+E+"\t");
		System.out.println("注:(状态码500,404,304,其他状态)除以（容量数-403状态数）");
		System.out.println("日志容量数:"+sum1);
		System.out.println("程序执行时间："+S+"毫秒");
		System.out.println();
		reader.close();
	}


	public static void getLongCount(String path1,String path2) throws Exception {

		//读取日志
		Reader in = new FileReader(new File(path1));
		BufferedReader reader = new BufferedReader(in);

		String s = "";

		int headindex , doindex;

		String str=null,str2=null;
		Map<String, Node> map =new HashMap<String,Node>();

		while ((s = reader.readLine()) != null) {

			doindex=s.indexOf(".do");
			if (doindex==-1) {
				continue; //如果不是.do地址就跳出
			}

			if ((headindex=s.indexOf("POST")) != -1) {
				str=s.substring(headindex+5,doindex);
				Node node=null;
				if (map.containsKey(str)) {
					node = map.get(str);
				}else {
					node=new Node(str, 0, 0,0, 0);
				}
				node.count++;
				//统计状态码
				codeCount(node, s);
				map.put(str, node);

			} else if ((headindex=s.indexOf("GET")) != -1) {
				str=s.substring(headindex+4,doindex);
				Node node=null;
				if (map.containsKey(str)) {
					node = map.get(str);
				}else {
					node=new Node(str, 0, 0, 0, 0);
				}
				node.count++;
				//统计状态码
				codeCount(node, s);
				map.put(str, node);

			}

		}

		List<Node> list=new ArrayList<Node>();//存储不同接口的各个数据

		long sum=0;//统计.do
		Node n=null;
		for (String key : map.keySet()) {
			n=map.get(key);
			list.add(n);
			sum+=n.count;
		}
		//Collections.sort(list);

		Collections.sort(list,new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
//				if (o1.code500>o2.code500)return -1;
//				if (o1.code500<o2.code500) return 1;
//				return 0;
			if(o1.code500/o1.count>o2.code500/o2.count) return -1;
			if(o1.code500/o1.count<o2.code500/o2.count) return 1;
			return  0;
			}
		});

		for (Node nn : list) {
			System.out.println("接口名称: "+nn.name);
			System.out.println("200 出现次数: "+nn.code200);
			System.out.println("500 出现次数: "+nn.code500);
			System.out.println("其他状态码次数: "+nn.other);
			System.out.println("调用此接口的失败率为: "+String.format("%.3f", nn.code500/nn.count*100)+" % ");
			System.out.println("总调用次数: "+(long)nn.count+"\r\n");

		}
		System.out.println("全量接口总计调用:"+sum+"\r\n");

		reader.close();
	}
	//统计状态码
	private static void codeCount(Node node, String s){

		int statusCodeIndex=s.lastIndexOf("HTTP");
		String str2 = s.substring(statusCodeIndex);
		if (str2.contains("200")) {
			node.code200++;
		}else if (str2.contains("500")) {
			node.code500++;
		}else {
			node.other++;
		}
	}
}
