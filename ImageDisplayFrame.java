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
    int destFileNumber = 0;
    ImageFragmentation imageFragmentation;
    ImageDisplayFrame(JPanel image, JPanel path, JPanel count) {
        this.setTitle("Image");
        this.setBounds(600, 500, 700, 500);
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

    public void setDestFileNumber(int num) {
        destFileNumber = num;
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
        } else if ((keycode == KeyEvent.VK_1||keycode == KeyEvent.VK_NUMPAD1)&& 0 < destFileNumber) {
            imageFragmentation.classificationImage(0);
        } else if ((keycode == KeyEvent.VK_2||keycode == KeyEvent.VK_NUMPAD2) && 1 < destFileNumber) {
            imageFragmentation.classificationImage(1);
        } else if ((keycode == KeyEvent.VK_3||keycode == KeyEvent.VK_NUMPAD3) && 2 < destFileNumber) {
            imageFragmentation.classificationImage(2);
        } else if ((keycode == KeyEvent.VK_4||keycode == KeyEvent.VK_NUMPAD4) && 3 < destFileNumber) {
            imageFragmentation.classificationImage(3);
        } else if ((keycode == KeyEvent.VK_5||keycode == KeyEvent.VK_NUMPAD5) && 4 < destFileNumber) {
            imageFragmentation.classificationImage(4);
        } else if ((keycode == KeyEvent.VK_6||keycode == KeyEvent.VK_NUMPAD6) && 5 < destFileNumber) {
            imageFragmentation.classificationImage(5);
        } else if ((keycode == KeyEvent.VK_7||keycode == KeyEvent.VK_NUMPAD7) && 6 < destFileNumber) {
            imageFragmentation.classificationImage(6);
        } else if ((keycode == KeyEvent.VK_8||keycode == KeyEvent.VK_NUMPAD8) && 7 < destFileNumber) {
            imageFragmentation.classificationImage(7);
        } else if ((keycode == KeyEvent.VK_9||keycode == KeyEvent.VK_NUMPAD9) && 8 < destFileNumber) {
            imageFragmentation.classificationImage(8);
        } else if ((keycode == KeyEvent.VK_0||keycode == KeyEvent.VK_NUMPAD0) && 9 < destFileNumber) {
            imageFragmentation.classificationImage(9);
        }

        if (keycode == KeyEvent.VK_BACK_SPACE) {
            imageFragmentation.undo();
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