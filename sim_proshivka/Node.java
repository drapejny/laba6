class Node {
    public int data;
    public boolean leftTag;     //true когда обычная связь
    public boolean rightTag;    //true когда обычная связь
    public Node left;
    public Node right;

    public Node(int data){
        this.data = data;
        leftTag = true;
        rightTag = true;
        left  = null;
        right = null;
    }

    public void show() { System.out.print(data + " "); }
}
