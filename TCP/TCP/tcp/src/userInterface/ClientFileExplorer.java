package userInterface;

import java.io.File;
//import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ClientFileExplorer extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTree myTree;
    private DefaultTreeModel MyDefaultTree;
    JButton send = new JButton("envoyer");
    private String path;

    public ClientFileExplorer() {
        MyDefaultTree = new MyTreeModel();
        myTree = new JTree(MyDefaultTree);
        myTree.addTreeExpansionListener(new myExpensionListener());
        myTree.addTreeSelectionListener(new myTreeSelectionListener());
    }

    public JTree getMyTree() {
        return myTree;
    }

    public String getPath() {
        return path;
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
            super(new DefaultMutableTreeNode(new File("/")));
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
                /* action si ca n'est pas un répertoire */
                path = ff.getAbsolutePath();
                System.out.println("this can be sent ");
            } else {
                /* action si c'est un répertoire */}
            // System.out.println(ff.isDirectory());
            // System.out.println(ff.getAbsolutePath());
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