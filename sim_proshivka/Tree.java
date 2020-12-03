class Tree {
    private Node root;
    private Node head;
    private boolean TreeIsSewed;

    public Tree(){
        root = null;
        TreeIsSewed = false;
        head = null;
    }

    //Метод для прошивки дерева
    Node y;
    public void makeSymSew(){
        if (TreeIsSewed){
            System.out.println("Дерево уже прошито");
        } else {
            head = new Node(-1);
            head.left  = root;
            head.right = head;

            y = head;

            symRightSew(root);     //правосторонняя прошивка
            y.right = head;
            y.rightTag = false;

            y = head;

            symLeftSew(root);          //левосторонняя прошивка
            y.left = head;
            y.leftTag = false;
        }
    }


    //Метод правосторонней прошивки
    private void symRightSew(Node x){
        if (x != null){
            symRightSew(x.left);
            doSymRightSew(x);
            symRightSew(x.right);
        }
    }

    private void doSymRightSew(Node p){
        if (y != null){
            if (y.right == null){
                y.rightTag = false;
                y.right = p;
            } else {
                y.rightTag = true;
            }
        }
        y = p;
    }


    //Метод левостороннеей прошивки
    private void symLeftSew(Node x){
        if (x != null){
            if (x.rightTag) symLeftSew(x.right);
            doSymLeftSew(x);
            symLeftSew(x.left);
        }
    }

    private void doSymLeftSew(Node p){
        if (y != null){
            if (y.left == null){
                y.leftTag = false;
                y.left = p;
            } else {
                y.leftTag = true;
            }
        }
        y = p;
    }

    //Метод для симметричного обхода прошитого дерева слева направо
    public void inOrderSewed(){
        Node curr = root;
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход прошитого дерева слева направо");
        while (curr != head){
            while (curr.left != null && curr.leftTag) curr = curr.left;
            curr.show();
            while (!curr.rightTag && curr.right != null) {
                curr = curr.right;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.right;
        }
        System.out.println();
    }

    //Метод для симметричного обхода прошитого дерева справа налево
    public void inOrderSewedObratno(){
        Node curr = root;
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход прошитого дерева справа налево:");
        while (curr != head){
            while (curr.right != null && curr.rightTag) curr = curr.right;
            curr.show();
            while (!curr.leftTag) {
                curr = curr.left;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.left;
        }
        System.out.println();
    }



    //Метод для удаления из прошитого дерева
    public void deleteFromSymmetricTree(int data){
        Node prev = null;
        Node curr  = root;
        boolean found = false;

        while (curr != null) {
            if (data == curr.data) {
                found = true;
                break;
            }
            prev = curr;
            if (data < curr.data) {
                if (curr.leftTag)
                    curr = curr.left;
                else
                    break;
            }
            else {
                if (curr.rightTag)
                    curr = curr.right;
                else
                    break;
            }
        }

        if (!found)
            System.out.println("Данного элемента нет в дереве");
        else if (curr.leftTag && curr.rightTag)
            deleteNodeWithTwoChildren(prev, curr);
        else if (curr.leftTag)
            deleteNodeWithOneChild(prev, curr);
        else if (curr.rightTag)
            deleteNodeWithOneChild(prev, curr);
        else
            deleteLeaf(prev, curr);
    }

    //Удаление листа
    private void deleteLeaf(Node prev, Node curr){
        if (prev == null)
            root = null;
        else if (curr == prev.left) {
            prev.leftTag = false;       //левая ссылка теперь нить
            prev.left = curr.left;
        } else {
            prev.rightTag = false;      //правая ссылка теперь нить
            prev.right = curr.right;
        }
    }

    //Удаление узла с одним наследником
    private void deleteNodeWithOneChild(Node prev, Node curr){
        Node child;

        if (curr.leftTag)
            child = curr.left;
        else
            child = curr.right;

        if (prev == null)
            root = child;
        else if (curr == prev.left)
            prev.left = child;
        else
            prev.right = child;

        Node bolshiy = getBolshego(curr);            //находим следующего большего элемента
        Node menshiy = getMenshego(curr);            //находим предыдущего меньшего элемента

        if (curr.leftTag)
            menshiy.right = bolshiy;
        else {
            if (curr.rightTag)
                menshiy.left = bolshiy;
        }
    }

    //Удаление узла с двумя наследниками
    private void deleteNodeWithTwoChildren(Node prev, Node curr){
        Node prevBolshiy = curr;
        Node bolshiy = curr.right;

        while (bolshiy.leftTag) {                              //находим следующий больший элемент
            prevBolshiy = bolshiy;
            bolshiy = bolshiy.left;
        }
        curr.data = bolshiy.data;                              //заменяем данные
        if (!bolshiy.leftTag && !bolshiy.rightTag)             //если лист, удаляем как лист
            deleteLeaf(prevBolshiy, bolshiy);
        else                                                   //если не лист, удаляем как узел с одним потомком
            deleteNodeWithOneChild(prevBolshiy, bolshiy);
    }

    //Получение следущего большего узла
    private Node getBolshego(Node ptr) {
        if (!ptr.rightTag)
            return ptr.right;

        ptr = ptr.right;
        while (ptr.leftTag)
            ptr = ptr.left;
        return ptr;
    }

    //Получение предыдущего меньшего узла
    private Node getMenshego(Node ptr) {
        if (!ptr.leftTag)
            return ptr.left;

        ptr = ptr.left;
        while (ptr.rightTag)
            ptr = ptr.right;
        return ptr;
    }

    //Вставка в прошитое дерево
    public void insertToSymSewTree(int data) {
        Node curr = root;
        Node prev = null;
        while (curr != null) {
            prev = curr;
            if (data < curr.data) {
                if (curr.leftTag)
                    curr = curr.left;
                else
                    break;
            } else {
                if (curr.rightTag)
                    curr = curr.right;
                else
                    break;
            }
        }

        Node newNode = new Node(data);
        newNode.leftTag = false;
        newNode.rightTag = false;

        if (prev == null) {
            root = newNode;
            newNode.left = null;
            newNode.right = null;
        } else if (data < (prev.data)) {
            newNode.left = prev.left;
            newNode.right = prev;
            prev.leftTag = true;
            prev.left = newNode;
        } else {
            newNode.left = prev;
            newNode.right = prev.right;
            prev.rightTag = true;
            prev.right = newNode;
        }
    }


    //Вставка нового ключа в обычное дерево
    public void insert(int data){
        if (TreeIsSewed){
            System.out.println("Дерево прошито, данная вставка не поддерживается");
        } else {
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
    }

    //Вызов симметричного обхода дерева
    public void getInOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева:");
        inOrder(root);
        System.out.println();
    }


    private void inOrder(Node root){
        if (root != null){
            inOrder(root.left);
            root.show();
            inOrder(root.right);
        }
    }
}
