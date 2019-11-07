import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

class ImageDisplayFrame extends JFrame implements ActionListener, WindowListener, KeyListener {
    JPanel imageDisplay;
    JPanel fileCounter;
    JPanel pathDisplay;
    ImageFragmentation imageFragmentation;
    ImageDisplayFrame(JPanel image, JPanel path, JPanel count) {
        this.setTitle("Image");
        this.setBounds(600, 500, 500, 500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);
        imageDisplay = image;
        fileCounter = count;
        pathDisplay = path;
        getContentPane().add(imageDisplay, BorderLayout.CENTER);
        getContentPane().add(fileCounter, BorderLayout.SOUTH);
        getContentPane().add(pathDisplay, BorderLayout.NORTH);
        addKeyListener(this);
        this.setVisible(true);
    }

    public void setImageFragmentation(ImageFragmentation imageFragmentation) {
        this.imageFragmentation = imageFragmentation;
    }
    
    @Override
    public void repaint() {
        super.repaint();
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
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (keycode == KeyEvent.VK_ENTER) {
            imageFragmentation.classificationImage(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
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