package DataSave;

public class ArrayStack_initial_version<T> {

    private T data[];
    private int maxSize;
    private int top;


    //初始化栈
    public ArrayStack_initial_version(int maxSize) {
        this.maxSize=maxSize;
        data=(T[])new Object[maxSize];
        this.top=-1;
    }

    //判断栈是否为空
    public boolean isEmpty() {
        return (top==-1);
    }
    //判断栈是否已经满了
    public  boolean isFull() {
        return (top==maxSize-1);
    }

    //压栈
    public boolean push(T value) {
        if(isFull()) {
            return false;
        }
        top++;
        data[top]=value;
        return true;
    }


    //取出栈顶元素
    public T pop() {
        if(isEmpty()) {
            return null;
        }
        T tmp=data[top];
        data[top]=null;
        top--;
        return tmp;
    }

    //============测试代码============
    public static void main(String[] args) {

        ArrayStack_initial_version<String> as=new ArrayStack_initial_version<String>(4);
        as.push("anhui");
        as.push("shanghai");
        as.push("beijing");
        as.push("nanj");
        //测试栈已经满了的情况
        System.out.println(as.push("aa"));
        for(int i=0;i<4;i++) {
            System.out.println(as.pop());
        }
    }

}
