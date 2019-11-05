import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

class DataSourcePanel extends JPanel implements ActionListener{
    // member
    JLabel     dataSourceLabel;
    JTextField dataSourceTextField;
    JButton    dataSourceButtons;
    DataSourcePanel () {
        makeDataSourcePanel();
    }

    private void makeDataSourcePanel() {
        dataSourceLabel = new JLabel("画像データソースディレクトリ");
        dataSourceTextField = new JTextField(40);
        dataSourceButtons = new JButton("参照...");
        dataSourceButtons.addActionListener(this);
        dataSourceButtons.setActionCommand("Brows");
        // フローレイアウトで配置
        setLayout(new FlowLayout());
        add(dataSourceLabel);
        add(dataSourceTextField);
        add(dataSourceButtons);
    }

    public String getTextFieldString() {
        return dataSourceTextField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Brows")) {
            // ディレクトリのみ選択
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // 選択がクリックされた時
            int selected = fileChooser.showOpenDialog(this);
            if (selected == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                dataSourceTextField.setText(file.getAbsolutePath());
            }
        }
    }
}