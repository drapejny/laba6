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

        //Симметричный обход
        tree.getInOrder();

        //Делаем прошивку
        tree.makeSymSew();

        //Выводим после прошивки
        tree.inOrderSewed();

        tree.deleteFromSymmetricTree(8);
        tree.deleteFromSymmetricTree(14);
        tree.insertToSymSewTree(13);
        System.out.println("Результат после преобразований:");
        tree.inOrderSewed();

    }
}
