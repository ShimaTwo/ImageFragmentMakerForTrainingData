import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class ImageDisplayPanel extends JPanel {
    private JLabel icon;
    ImageDisplayPanel() {
        icon = new JLabel("");
        add(icon);
    }

    public void setImageIcon(ImageIcon image) {
        icon.setIcon(image);
    }
}

class ImageFilePathPanel extends JPanel {
    private JLabel filePathLabel;
    ImageFilePathPanel() {
        filePathLabel = new JLabel("");
        add(filePathLabel);
    }

    public void setFilePathLabel(String path) {
        filePathLabel.setText(path);
    }
}

class ImageFileCountLabel extends JPanel {
    private JLabel fileCountLabel;
    private int allFileNumber;
    ImageFileCountLabel(int fileNumber) {
        allFileNumber = fileNumber;
        fileCountLabel = new JLabel("0/"+allFileNumber);
        add(fileCountLabel);
    }

    public void setFileCount(int i) {
        fileCountLabel.setText(i+"/"+allFileNumber);
    }
}