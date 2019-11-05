import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class ImageDisplayFrame extends JFrame implements ActionListener, WindowListener {
    ImageDisplayFrame() {
        this.setTitle("Image");
        this.setBounds(600, 500, 500, 500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        int option = JOptionPane.showConfirmDialog(this, "実行途中ですが終了しますか。", "確認ダイアログ", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            // 続行
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {   
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }
}