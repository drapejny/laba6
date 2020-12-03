public class Main {
    public static void main(String[] args){

        Tree tree = new Tree();

        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(2);
        tree.insert(8);
        tree.insert(9);
        tree.insert(6);
        tree.insert(7);
        tree.insert(14);
        tree.insert(20);


        tree.doPreOrder(); //прямой обход простого дерева

        tree.doThreading(); //прошиваем дерево

        System.out.println("Прямой обход прошитого дерева");

        tree.doPreOrderThreatedTree(); //прямой обход прошитого дерева

        tree.deleteFromThreadedTree(5);
        tree.deleteFromThreadedTree(14);
        tree.insertToThreadedTree(13);

        System.out.println("Прямой проход прошитого дерева после изменений");

        tree.doPreOrderThreatedTree(); //прямой обход прошитого дерева

    }
}
