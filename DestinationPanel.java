import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.ArrayList;
import java.util.List;

class DestinationPanel extends JPanel {
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
        destTextField = new JTextField(15);
        destButtons = new JButton("参照...");
        // フローレイアウト(横並べ)で配置
        setLayout(new FlowLayout());        
        add(destLabel);
        add(destTextField);
        add(destButtons);
    }
}