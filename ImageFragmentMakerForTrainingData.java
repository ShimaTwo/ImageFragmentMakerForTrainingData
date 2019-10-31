import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.scene.control.Button;

class ImageFragmentMakerForTrainingData extends JFrame implements ActionListener {
    public static void main(String[] args) {
        JFrame frame = new ImageFragmentMakerForTrainingData();
        
        frame.setTitle("ImageFragmentMaker");
        frame.setBounds(500, 400, 700, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // members
    // ベースパネル
    JPanel myPanel = new JPanel();
    // ボタンパネル
    JPanel bPanel = new JPanel();
    // データソースパネル
    DataSourcePanel dataSource;
    // 保存先パネル
    ArrayList<JPanel> destPanel = new ArrayList<>();
    int destPanelNumber;
    // メインダイアログボタン郡
    JButton addDestination;
    JButton deletDestination;
    JButton startMaking;

    // constructor
    ImageFragmentMakerForTrainingData() {
        destPanelNumber = 0;
        // データソース
        getContentPane().add(dataSource = new DataSourcePanel(), BorderLayout.NORTH);

        // 初期状態として2つの保存先を指定できるようにする
        addDestinationPanel(myPanel);
        addDestinationPanel(myPanel);
        // ベースパネルを追加
        getContentPane().add(myPanel, BorderLayout.WEST);

        // ボタン群初期化
        addDestination = new JButton("データ保存先追加");
        deletDestination = new JButton("データ保存先削除");
        startMaking = new JButton("開始");
        addButtons(bPanel);
        // ボタンパネルを追加
        getContentPane().add(bPanel, BorderLayout.SOUTH);
    }

    // ベースパネルに保存先パネルを追加
    private void addDestinationPanel(JPanel p) {
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        destPanel.add(new DestinationPanel(destPanelNumber+1));
        p.add(destPanel.get(destPanelNumber));
        destPanelNumber++;
    }

    // ボタン群追加
    private void addButtons(JPanel p) {
        p.setLayout(new FlowLayout());
        p.add(addDestination);
        p.add(deletDestination);
        p.add(startMaking);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}