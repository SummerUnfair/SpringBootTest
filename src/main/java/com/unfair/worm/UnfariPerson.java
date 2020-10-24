package com.unfair.worm;/*
 * @author Ferao
 * @date
 * @discription
 */

public class UnfariPerson {

    public static void main (String[] args)
    {
        Fu son =new zi();
        son.eat();
        System.out.println(son.age);

        System.out.println(((zi) son).name);
    }

}

class Fu{
    int age =20 ;
    public void eat(){
        System.out.println(this.age);
    }
    public static void foot(){
        System.out.println("foot fu");
    }
}
class zi extends  Fu{
    int age =12;
    String name="ferap";
    public void eat(){
        System.out.println(this.age);
    }
    public static void foot(){
        System.out.println("foot fu");
    }
}
