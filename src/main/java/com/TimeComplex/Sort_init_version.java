package com.TimeComplex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sort_init_version {

    private static class Node implements Comparable<Node>{

        int A;

        public Node(int A){
            this.A=A;
        }

        @Override
        public int compareTo(Node o) {
            if (this.A>o.A) {
                return 1;
            }
            if (this.A<o.A) {
                return -1;
            }

            return 0;
        }
    }

    public static void main(String[] args) {

        List<Node>  list =new ArrayList<Node>();
        Node node=null;

        list.add(new Node(1));
        list.add(new Node(3));
        list.add(new Node(8));
        list.add(new Node(2));
        list.add(new Node(0));

        Collections.sort(list);

        for (Node a: list){

            System.out.print(a.A+"\t");

        }


//        Set<Integer> set=new TreeSet<>();
//
//        set.add(1);
//        set.add(6);
//        set.add(2);
//        set.add(3);
//        set.add(1);
//
//        for (int a : set){
//
//            System.out.print(a+"\t");
//        }

//        for(Iterator<Integer> iterator = set.iterator(); iterator.hasNext();){
//
//            System.out.print(iterator.next());
//        }
//
//
//        Map<Integer,Integer> map =new TreeMap<>();
//
//        map.put(1,2);
//        map.put(4,2);
//        map.put(3,2);
//        map.put(7,2);
//        map.put(0,2);
//
//        for (int a : map.keySet()){
//
//            System.out.print(a+"\t");
//        }
//
//
//
//        List<Integer> list =new ArrayList();
//
//        list.add(7);
//        list.add(6);
//        list.add(5);
//        list.add(2);
//        list.add(1);
//        list.add(3);
//
//        Collections.sort(list);
//
//        for (int a : list){
//
//            System.out.print(a+"\t");
//        }
    }

}
