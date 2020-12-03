import java.util.Stack;

public class Tree {
    private Node root;


    public Tree() {
        root = null;
    }

    //Вставка в простое дерево
    public void insert(int data) {
        Node newNode = new Node(data);

        if (root == null) {
            root = newNode;
        } else {

            Node curr = root;
            Node prev;

            while (true) {
                prev = curr;
                if (data < curr.data) {
                    curr = curr.left;
                    if (curr == null) {
                        prev.left = newNode;
                        return;
                    }
                } else {
                    curr = curr.right;
                    if (curr == null) {
                        prev.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    //Вставка в прошитое дерево
    public void insertToThreadedTree(int data) {
        removeThreading(root);
        insert(data);
        doThreading();
    }

    //Прямой обход прошитого дерева
    public void doPreOrderThreatedTree() {
        Node curr = root;

        while (curr != null) {
            curr.show();
            if (curr.left != null && !curr.leftIsThread) curr = curr.left;
            else if (curr.left == null) curr = curr.right;
            else if (curr.leftIsThread) curr = curr.left;
        }
        System.out.println();
    }

    //Прямой обход обычного дерева
    public void doPreOrder() {
        System.out.println("Прямой проход обычного дерева");
        preOrder(root);
        System.out.println();
    }

    public void preOrder(Node node) {
        if (node != null) {
            node.show();
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    //Метод прямой прошивки дерева
    public void doThreading() {
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);

        while (!stack.empty()) {
            Node extracted = stack.pop();

            if (extracted.right != null) stack.push(extracted.right);
            if (extracted.left != null && !extracted.leftIsThread) stack.push(extracted.left);

            if (stack.empty()) return;

            if (extracted.right == null && (extracted.leftIsThread || extracted.left == null)) {

                if (stack.peek() == root) {
                    extracted.left = null;
                    extracted.leftIsThread = false;
                } else {
                    extracted.left = stack.peek();
                    extracted.leftIsThread = true;
                }
            }
        }
    }

    //Метод удаления прошивки из дерева
    private void removeThreading(Node n) {
        if (n != null) {
            if (n.leftIsThread) {
                n.leftIsThread = false;
                n.left = null;
            }
            removeThreading(n.left);
            removeThreading(n.right);
        }
    }


    //Метод для удаления элемента в простом дереве
    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        //Поиск удаляемого узла
        while (current.data != key) {
            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null)
                return false;
        }

        //Если удаляемый узел - лист дерева
        if (current.left == null && current.right == null) {
            if (current == root)
                root = null;
            else if (isLeftChild)
                parent.left = null;
            else
                parent.right = null;
        } else

            //Если у удаляемого узла один потомок:

            //Если у удаляемого узла только левый потомок
            if (current.right == null) {
                if (current == root)
                    root = current.left;
                else if (isLeftChild)
                    parent.left = current.left;
                else
                    parent.right = current.left;
            } else

                //Если у удаляемого узла только правый потомок
                if (current.left == null) {
                    if (current == root)
                        root = current.right;
                    else if (isLeftChild)
                        parent.left = current.right;
                    else
                        parent.right = current.right;
                } else

                //Если у удаляемого узла два потомка
                {

                    Node successor = getSuccessor(current); //находим следущий больший узел
                    if (current == root)
                        root = successor;
                    else if (isLeftChild)           //если уд. узел - левый потомок
                        parent.left = successor;    //заменяем приемником
                    else                            //если уд. узел - правый потомок
                        parent.right = successor;   //заменяем приемником

                    successor.left = current.left;  //добавляем левое поддерево от удалённого узла приемнику
                }
        return true;
    }

    private Node getMin(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    //Метод нахождения приемника для удаляемого узла
    private Node getSuccessor(Node delNode) {

        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.right;

        //цикл поиска самого левого узла
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        //если приемник не правый потомок от удаляемого узла
        if (successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }
        return successor;
    }

    //Удаление из прошитого дерева
    public boolean deleteFromThreadedTree(int key) {
        removeThreading(root);
        boolean res = delete(key);
        doThreading();
        return res;
    }

}
