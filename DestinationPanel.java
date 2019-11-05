import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.ArrayList;
import java.util.List;

class DestinationPanel extends JPanel implements ActionListener {
    //Member
    JLabel     destLabel;
    JTextField destTextField;
    JButton    destButtons;
    // Constructor
    DestinationPanel(int destinationNumber) {
        makeDestinations(destinationNumber);
    }

    // ラベル、テキストフィールド、ボタンを割り当てる
    private void makeDestinations(int destinationNumber){
        destLabel = new JLabel( "保存先"+ (destinationNumber) );
        destTextField = new JTextField(50);
        destButtons = new JButton("参照...");
        destButtons.addActionListener(this);
        destButtons.setActionCommand("Brows");
        // フローレイアウト(横並べ)で配置
        setLayout(new FlowLayout());        
        add(destLabel);
        add(destTextField);
        add(destButtons);
    }

    public String getDestTextFieldString() {
        return destTextField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Brows")) {
            // ディレクトリ選択のみを許可
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // 選択がクリックされた時
            int selected = fileChooser.showOpenDialog(this);
            if (selected == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                destTextField.setText(file.getAbsolutePath());
            }
        }
    }
}