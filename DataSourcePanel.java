import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

class DataSourcePanel extends JPanel {
    // member
    JLabel     dataSourceLabel;
    JTextField dataSourceTextField;
    JButton    dataSourceButtons;
    DataSourcePanel () {
        makeDataSourcePanel();
    }

    private void makeDataSourcePanel() {
        dataSourceLabel = new JLabel("画像データソースディレクトリ");
        dataSourceTextField = new JTextField(15);
        dataSourceButtons = new JButton("参照...");
        // フローレイアウトで配置
        setLayout(new FlowLayout());
        add(dataSourceLabel);
        add(dataSourceTextField);
        add(dataSourceButtons);
    }
}