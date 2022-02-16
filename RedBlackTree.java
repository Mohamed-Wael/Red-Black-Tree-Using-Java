/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblacktree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohamed Wael
 */
public class RedBlackTree {
    private final int RED = 0;
    private final int BLACK = 1;

    private class Node {

        String key = null;
        int color = BLACK;
        Node left = nil, right = nil, parent = nil;

        Node(String key) {
            this.key = key;
        } 
    }

    private final Node nil = new Node(null); 
    private Node root = nil;
    
    private Node findNode(Node findNode, Node node) {
        if (root == nil) {
            return null;
        }

        if (findNode.key.compareToIgnoreCase(node.key)<0) {
            if (node.left != nil) {
                return findNode(findNode, node.left);
            }
        } else if (findNode.key.compareToIgnoreCase(node.key)>0) {
            if (node.right != nil) {
                return findNode(findNode, node.right);
            }
        } else if (findNode.key.equalsIgnoreCase(node.key)) {
            return node;
        }
        return null;
    }

    private void insert(Node node) {
        Node temp = root;
        if (root == nil) {
            root = node;
            node.color = BLACK;
            node.parent = nil;
        } else {
            node.color = RED;
            while (true) {
                if (node.key.compareToIgnoreCase(temp.key)<0) {
                    if (temp.left == nil) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.key.compareToIgnoreCase(temp.key)>=0) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    private void fixTree(Node node) {
        while (node.parent.color == RED) {
            Node uncle = nil;
            if (node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;

                if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                } 
                if (node == node.parent.right) {
                    node = node.parent;
                    rotateLeft(node);
                } 
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                rotateRight(node.parent.parent);
            } else {
                uncle = node.parent.parent.left;
                 if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                rotateLeft(node.parent.parent);
            }
        }
        root.color = BLACK;
    }

    void rotateLeft(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {
            Node right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    void rotateRight(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }
    
    void readFile()
    {
        Scanner x = null;
        String item;
        Node node;
        try {
            x = new Scanner(new File("dictionary.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RedBlackTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(x.hasNext())
        {
            item = x.nextLine();
            node = new Node(item);
            insert(node);
        }
    }
    
    int getmax(int a, int b)
    {
        if(a>b)
            return a;
        else
            return b;
    }
    
    int height(Node root)
    {
        if(root==null)
            return -1;
        else
            return 1+getmax(height(root.right),height(root.left));
    }


    
    int treeSize(Node node) {
        if (node == nil) {
            return 0;
        }
        int i = 0;
        i+=treeSize(node.left);
        i++;
        i+=treeSize(node.right);
        return i;
    }
    
    
    public void UI() {
        Scanner scan = new Scanner(System.in);
        int choice = 1;
        System.out.println("Size of Dictionary is: "+ treeSize(root));
        System.out.println("height of tree is: "+ height(root));
        System.out.println("Root is: "+ root.key);
        while (choice!=0) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1.- Insert\n"
                    + "2.- Search\n"
                    + "0.- Exit\n");
            choice = scan.nextInt();

            String item;
            Node node;
            switch (choice) {
                case 1:
                    System.out.println("Enter word you would like to insert:");
                    item = scan.next();
                    node = new Node(item);
                    if(findNode(node, root) != null)
                            {
                                System.out.println("Error: Word alreacdy exists");
                            }
                    else
                    {
                        insert(node);
                        System.out.println("New size of Dictionary is: "+ treeSize(root));
                        System.out.println("New height of tree is: "+ height(root));
                    }
                    break;
                case 2:
                    System.out.println("Enter word you would like to Search for:");
                    item = scan.next();
                    node = new Node(item);
                    System.out.println((findNode(node, root) != null) ? "YES" : "NO");
                    break;
            }
        }
    }
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        rbt.readFile();
        rbt.UI();
    }   
}
