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
        frame.setBounds(500, 400, 600, 700);
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
        addDestinationPanel();
        addDestinationPanel();
        // ベースパネルを追加
        getContentPane().add(myPanel, BorderLayout.WEST);

        // ボタン群初期化
        addDestination = new JButton("データ保存先追加");
        addDestination.addActionListener(this);
        addDestination.setActionCommand("addDest");
        deletDestination = new JButton("データ保存先削除");
        deletDestination.addActionListener(this);
        deletDestination.setActionCommand("deletDest");
        startMaking = new JButton("開始");
        startMaking.addActionListener(this);
        startMaking.setActionCommand("start");
        addButtons(bPanel);
        // ボタンパネルを追加
        getContentPane().add(bPanel, BorderLayout.SOUTH);
    }

    // ベースパネルに保存先パネルを追加
    private void addDestinationPanel() {
        // 保存先は10以上にはならない
        if (destPanelNumber >= 10) return;

        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
        destPanel.add(new DestinationPanel(destPanelNumber+1));
        myPanel.add(destPanel.get(destPanelNumber));
        myPanel.revalidate();
        destPanelNumber++;
    }

    // ベースパネルから保存先パネルを削除
    private void deleteDestinationPanel() {
        // 保存先は2以下にならない
        if (destPanelNumber <= 2) return;

        myPanel.remove(destPanel.get(destPanelNumber-1));
        destPanel.remove(destPanelNumber-1);
        myPanel.revalidate();
        destPanelNumber--;
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
        String cmd = e.getActionCommand();
        // データ保存先追加ボタン
        if (cmd.equals("addDest")) {
            addDestinationPanel();
        }
        // データ保存先削除ボタン
        else if (cmd.equals("deletDest")) {
            deleteDestinationPanel();
        }
        // 開始ボタン
        else if (cmd.equals("start")) {

        }
    }
}