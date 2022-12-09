package userInterface;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTree myTree;
    private DefaultTreeModel MyDefaultTree;

    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() {
        super("Explorateur");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        MyDefaultTree = new MyTreeModel();
        myTree = new JTree(MyDefaultTree);
        myTree.addTreeExpansionListener(new myExpensionListener());
        myTree.addTreeSelectionListener(new myTreeSelectionListener());

        getContentPane().add(new JScrollPane(myTree), BorderLayout.CENTER);
        setSize(600, 400);
        setVisible(true);
    }

    class myExpensionListener implements TreeExpansionListener {

        public myExpensionListener() {
            super();
        }

        public void treeExpanded(TreeExpansionEvent e) {
            addChildren((DefaultTreeModel) ((JTree) e.getSource()).getModel(),
                    (DefaultMutableTreeNode) e.getPath().getLastPathComponent());
        }

        public void treeCollapsed(TreeExpansionEvent e) {
        }
    }

    class MyTreeModel extends DefaultTreeModel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public MyTreeModel() {
            super(new DefaultMutableTreeNode(new File("F:")));
            addChildren(this, (DefaultMutableTreeNode) getRoot());
        }

        public boolean isLeaf(Object e) {
            return !((File) ((DefaultMutableTreeNode) e).getUserObject()).isDirectory();
        }
    }

    class myTreeSelectionListener implements TreeSelectionListener {

        public myTreeSelectionListener() {
            super();
        }

        public void valueChanged(TreeSelectionEvent arg0) {

            DefaultMutableTreeNode f = (DefaultMutableTreeNode) arg0.getPath().getLastPathComponent();
            File ff = (File) f.getUserObject();

            if (!ff.isDirectory()) {
                /* action si ca n'est pas un répertoire */} else {
                /* action si c'est un répertoire */}

            System.out.println(ff.isDirectory());
            System.out.println(ff.getAbsolutePath());
        }
    }

    protected static void addChildren(DefaultTreeModel treeModel, DefaultMutableTreeNode parentNode) {

        File selectedFile = (File) parentNode.getUserObject();
        parentNode.removeAllChildren();
        treeModel.nodeStructureChanged(parentNode);

        File[] children = selectedFile.listFiles();

        int x = 0;

        for (int i = 0; i < children.length; i++) {
            if (children[i].isDirectory()) {
                treeModel.insertNodeInto(new DefaultMutableTreeNode(new File(children[i].getAbsolutePath())),
                        parentNode, x);
                x++;
            }
        }

        for (int i = 0; i < children.length; i++) {
            if (!children[i].isDirectory()) {
                treeModel.insertNodeInto(new DefaultMutableTreeNode(new File(children[i].getAbsolutePath())),
                        parentNode, x);
                x++;
            }
        }
    }
}