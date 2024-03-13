import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AirportGUI extends JFrame {
    private JPanel queuePanel;
    private JPanel deskPanel;
    private JPanel flightPanel;

    private Random timeSetter = new Random();
    private final int deskWidth = 150; // 设置柜台窗口固定宽度
    private final int deskHeight = 150; // 设置柜台窗口固定高度
    private final int queueCount1 = 11; // 假定Queue1中有11人
    private final int queueCount2 = 8;  // 假定Queue2中有8人
    private final int queuePanelHeight = 200; // 设置队列面板固定高度

    public AirportGUI() {
        setTitle("Airport Check-in System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // 设置布局管理器间隔

        queuePanel = createQueuePanel(); // 创建队列显示区
        deskPanel = createDeskPanel();   // 创建柜台操作区
        flightPanel = createFlightPanel(); // 创建航班信息区

        add(queuePanel, BorderLayout.NORTH);
        add(deskPanel, BorderLayout.CENTER);
        add(flightPanel, BorderLayout.SOUTH);

        pack(); // 调整窗口以适应组件大小
        setVisible(true);
    }

    private JPanel createQueuePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setPreferredSize(new Dimension(getWidth(), queuePanelHeight)); // 设置固定高度
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JTextArea queue1Text = new JTextArea("There are currently "+ queueCount1 +" people in queue1");
        queue1Text.setEditable(false);
        JScrollPane queue1Scroll = new JScrollPane(queue1Text);
        panel.add(queue1Scroll);

        JTextArea queue2Text = new JTextArea("There are currently "+ queueCount2 +" people in queue2");
        queue2Text.setEditable(false);
        JScrollPane queue2Scroll = new JScrollPane(queue2Text);
        panel.add(queue2Scroll);

        return panel;
    }

    private JPanel createDeskPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加边距

        for (int i = 1; i <= 5; i++) {
            final int deskNumber = i; // 使用final变量以便在匿名类中使用
            JPanel desk = new JPanel();
            desk.setPreferredSize(new Dimension(deskWidth, deskHeight)); // 设置柜台固定大小
            desk.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 初始边框设置为绿色
            JCheckBox checkBox = new JCheckBox("Desk " + deskNumber + " Close");
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    if (cb.isSelected()) {
                        desk.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // 勾选时边框变绿
                        cb.setText("Desk " + deskNumber + " Open"); // 更新复选框文本为Open
                    } else {
                        desk.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 未勾选时边框变回黑色
                        cb.setText("Desk " + deskNumber + " Close"); // 更新复选框文本为初始状态
                    }
                }
            });

            desk.add(checkBox);
            panel.add(desk);
        }

        return panel;
    }


    private JPanel createFlightPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(getWidth(), queuePanelHeight)); // 假设已经定义了queuePanelHeight

        for (int i = 1; i <= 6; i++) {
            final int flightNumber = i;
            JPanel flightPanel = new JPanel(new BorderLayout());
            flightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel countdownLabel = new JLabel("", SwingConstants.CENTER);
            flightPanel.add(countdownLabel, BorderLayout.CENTER);

            // 设置倒计时时间为1分钟
            final int[] timeLeft = {60 + timeSetter.nextInt(200)}; // 以秒为单位

            Timer timer = new Timer(1000, new ActionListener() { // 计时器每秒触发一次
                public void actionPerformed(ActionEvent e) {
                    timeLeft[0]--;
                    countdownLabel.setText("Flight " + flightNumber + " - " + timeLeft[0]/60 + "M" + timeLeft[0]%60 + "S");

                    if (timeLeft[0] <= 0) {
                        flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // 边框变红
                        countdownLabel.setText("Flight " + flightNumber + " Closed"); // 文本更新为Closed
                        ((Timer)e.getSource()).stop(); // 停止计时器
                    }
                }
            });
            timer.start(); // 启动计时器

            panel.add(flightPanel);
        }

        return panel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AirportGUI();
            }
        });
    }
}
