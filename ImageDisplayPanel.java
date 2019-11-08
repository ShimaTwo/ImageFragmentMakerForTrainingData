import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class ImageDisplayPanel extends JPanel {
    private JLabel icon;
    private ImageIcon imageIcon;
    ImageDisplayPanel() {
        icon = new JLabel("");
        imageIcon = null;
        add(icon);
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        icon.setIcon(imageIcon);
    }

    public ImageIcon getDisplayedImage() {
        return this.imageIcon;
    }
}

class ImageFilePathPanel extends JPanel {
    private JLabel filePathLabel;
    private String pathString;
    ImageFilePathPanel() {
        filePathLabel = new JLabel("");
        pathString = "";
        add(filePathLabel);
    }

    public void setFilePathLabel(String path) {
        pathString = path;
        filePathLabel.setText(pathString);
    }

    public String getPathString() {
        return this.pathString;
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