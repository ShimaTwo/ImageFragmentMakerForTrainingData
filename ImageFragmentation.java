import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class ImageFragmentation extends JFrame {

    // Constructor
    private String myDataPath;
    private File mySourceFile;
    private String myDestPaths[];
    private File myDestFiles[];
    ImageFragmentation(String dataSourcePath, ArrayList<String> destPaths) {
        // データソースパスと保存先パスを格納
        myDataPath = dataSourcePath;
        mySourceFile = new File(myDataPath);
        myDestPaths = new String[destPaths.size()];
        myDestFiles = new File[destPaths.size()];
        for (int i = 0; i < myDestPaths.length; i++) {
            myDestPaths[i] = destPaths.get(i);
            myDestFiles[i] = new File(myDestPaths[i]);
        }
    }

    public boolean dataPathCheck() {
        // データソースパスのチェック
        if (myDataPath.equals("")) {
            JLabel pathEmpty = new JLabel("データソースパスが空です。");
            JOptionPane.showMessageDialog(this, pathEmpty);
            return false;
        }
        if (!mySourceFile.exists()) {
            JLabel fileNotExist = new JLabel("データソースディレクトリが見つかりません。");
            JOptionPane.showMessageDialog(this, fileNotExist);
            return false;
        }

        // データ保存先のチェック
        for (int i = 0; i < myDestPaths.length; i++) {
            if (myDestPaths[i].equals("")) {
                JLabel pathEmpty = new JLabel("データ保存先パスが空です。");
                JOptionPane.showMessageDialog(this, pathEmpty);
                return false;
            }
            if (!myDestFiles[i].exists()) {
                JLabel fileNotExist = new JLabel("データ保存先ディレクトリが見つかりません。");
                JOptionPane.showMessageDialog(this, fileNotExist);
                return false;
            }
        }
        return true;
    }

    public void start() {

    }
}