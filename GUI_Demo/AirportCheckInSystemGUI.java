import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger; // 导入日志记录器类

public class AirportCheckInSystemGUI extends JFrame {

    private AirportCheckInSystem checkInSystem;
    private static final Logger LOGGER = Logger.getLogger(AirportCheckInSystemGUI.class.getName()); // 创建日志记录器

    private JTextArea reportTextArea;
    private JLabel queueStatusLabel; // 添加用于显示队列状态的标签

    private JTextField nameField;
    private JTextField reservationField;

    private JTextField weightField;
    private JTextField volumeField;

    private JButton proceedToPaymentButton;
    private JButton proceedToSuccessButton;
    private JButton queueForBoardingButton; // 添加排队登机按钮

    private StringBuilder reportBuilder; // 用于保存报告内容

    public AirportCheckInSystemGUI() {
        super("机场值机系统");

        checkInSystem = new AirportCheckInSystem();

        // 设置窗口属性
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中显示

        // 创建面板和布局
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));

        // 创建按钮
        JButton loadButton = new JButton("加载数据");
        JButton checkInButton = new JButton("值机");
        JButton proceedToLuggageButton = new JButton("继续行李托运");
        queueForBoardingButton = new JButton("排队登机"); // 初始化排队登机按钮

        // 创建报告文本区域
        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);

        // 创建输入字段和标签
        JLabel nameLabel = new JLabel("姓名:");
        nameField = new JTextField();
        JLabel reservationLabel = new JLabel("预约号码:");
        reservationField = new JTextField();

        // 添加组件到输入面板
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(reservationLabel);
        inputPanel.add(reservationField);

        // 添加按钮到按钮面板
        buttonPanel.add(loadButton);
        buttonPanel.add(checkInButton);
        buttonPanel.add(proceedToLuggageButton);
        buttonPanel.add(queueForBoardingButton); // 将排队登机按钮添加到按钮面板

        // 添加组件到主面板
        mainPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // 添加队列状态标签到按钮面板
        queueStatusLabel = new JLabel("队列状态: 无人排队");
        buttonPanel.add(queueStatusLabel);

        // 添加主面板到窗口
        add(mainPanel);

        // 加载数据按钮的点击事件处理
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 模拟加载数据
                checkInSystem.loadMockData();
                appendToReport("模拟数据加载成功。");
            }
        });

        // 办理登机手续按钮的点击事件处理
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String reservationNumber = reservationField.getText();
                String checkInResult = checkInSystem.processCheckIn(name, reservationNumber);
                appendToReport(checkInResult);
                // 如果值机失败，则清空输入框
                if (checkInResult.equals("值机失败，请检查姓名和预约号码。")) {
                    nameField.setText("");
                    reservationField.setText("");
                }
            }
        });

        // 跳转到行李托运界面按钮的点击事件处理
        proceedToLuggageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 这里可以添加逻辑来处理跳转到行李托运界面的操作
                // 留待您根据需求来完成
                appendToReport("点击继续行李托运按钮。");
                openLuggageCheckPanel();
            }
        });

        // 排队登机按钮的点击事件处理
        queueForBoardingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String queueResult = checkInSystem.queueForBoarding(); // 调用排队登机方法
                appendToReport(queueResult);
                LOGGER.info("排队登机: " + queueResult); // 记录排队登机信息到日志
                updateQueueStatusLabel(); // 更新队列状态标签
            }
        });

        // 初始化报告内容
        reportBuilder = new StringBuilder();
    }

    // 打开行李托运界面
    private void openLuggageCheckPanel() {
        JFrame luggageCheckFrame = new JFrame("行李托运");
        luggageCheckFrame.setSize(400, 200);
        luggageCheckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        luggageCheckFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel weightLabel = new JLabel("行李重量(kg):");
        weightField = new JTextField();
        JLabel volumeLabel = new JLabel("行李体积(m³):");
        volumeField = new JTextField();
        proceedToPaymentButton = new JButton("付款");
        proceedToSuccessButton = new JButton("成功");

        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(volumeLabel);
        panel.add(volumeField);
        panel.add(proceedToPaymentButton);
        panel.add(proceedToSuccessButton);

        luggageCheckFrame.add(panel);
        luggageCheckFrame.setVisible(true);

        proceedToPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double weight = Double.parseDouble(weightField.getText());
                double volume = Double.parseDouble(volumeField.getText());
                String result = checkInSystem.processLuggageCheck(weight, volume);
                if (result.equals("Payment required.")) {
                    double fee = checkInSystem.calculateExtraFee(weight, volume); // 计算额外费用
                    JOptionPane.showMessageDialog(null, "行李超重或超体积，需要支付额外费用: $" + fee);
                    appendToReport("额外费用: $" + fee); // 将额外费用信息添加到报告
                } else {
                    JOptionPane.showMessageDialog(null, "行李托运成功。");
                }
            }
        });

        proceedToSuccessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 这里可以添加逻辑来处理跳转到成功界面的操作
                // 留待您根据需求来完成
                luggageCheckFrame.dispose();
            }
        });
    }

    // 在报告文本区域中追加文本
    private void appendToReport(String text) {
        reportTextArea.append(text + "\n");
        reportBuilder.append(text).append("\n"); // 将文本追加到报告内容中
        writeReportToFile(text); // 将文本写入报告文件
    }

    // 将文本写入报告文件
    private void writeReportToFile(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("check_in_report.txt", true))) {
            writer.write(text + "\n"); // 将文本追加到报告文件中
        } catch (IOException e) {
            LOGGER.warning("写入报告文件时出现错误: " + e.getMessage()); // 记录写入报告文件错误的日志
        }
    }

    // 更新队列状态标签
    private void updateQueueStatusLabel() {
        String queueStatus = checkInSystem.getQueueStatus();
        queueStatusLabel.setText("队列状态: " + queueStatus);
    }

    // 保存报告到文件
    private void saveReportToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("check_in_report.txt"))) {
            writer.write(reportBuilder.toString()); // 将报告内容写入文件
            LOGGER.info("报告已保存到文件: check_in_report.txt"); // 记录报告保存成功的日志
        } catch (IOException e) {
            LOGGER.warning("保存报告时出现错误: " + e.getMessage()); // 记录报告保存失败的日志
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        saveReportToFile(); // 在程序退出时保存报告到文件
    }

    public static void main(String[] args) {
        // 使用 Event Dispatch Thread (EDT) 启动 Swing 应用程序
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AirportCheckInSystemGUI().setVisible(true);
            }
        });
    }
}

class AirportCheckInSystem {

    // 模拟乘客数据和航班数据
    private String[] passengerNames = {"Alice", "Bob", "Charlie", "David", "Eva"};
    private String[] reservationNumbers = {"123", "456", "789", "101", "112"};
    private double[] luggageWeights = {20.5, 25.0, 30.2, 28.8, 23.5};
    private boolean[] checkedInStatus = {false, false, false, false, false};
    private int maxPassengerCapacity = 5;
    private int currentPassengerCount = 0;

    // 模拟排队登机方法
    public String queueForBoarding() {
        // 模拟排队登机过程，可以根据实际情况进行逻辑编写
        return "排队登机成功。";
    }

    // 获取队列状态信息
    public String getQueueStatus() {
        // 模拟返回队列状态信息，可以根据实际情况进行逻辑编写
        return "有人排队";
    }

    // 计算额外费用（模拟方法）
    public double calculateExtraFee(double weight, double volume) {
        // 模拟计算额外费用的逻辑，例如每超重1kg或超体积1m³，收取$10的额外费用
        double extraWeightFee = Math.max(0, weight - 23.0) * 10.0;
        double extraVolumeFee = Math.max(0, volume - 1.5) * 10.0;
        return extraWeightFee + extraVolumeFee;
    }

    // 模拟加载数据
    public void loadMockData() {
        // 可以根据需要进行实际的数据加载操作
        // 这里简单地增加乘客数量和更新航班数据
        currentPassengerCount = passengerNames.length;
    }

    // 处理乘客办理登机手续
    public String processCheckIn(String name, String reservationNumber) {
        for (int i = 0; i < currentPassengerCount; i++) {
            if (checkedInStatus[i]) {
                return "您已经办理过值机。";
            }
            if (passengerNames[i].equals(name) && reservationNumbers[i].equals(reservationNumber)) {
                checkedInStatus[i] = true;
                return "值机成功。";
            }
        }
        return "值机失败，请检查姓名和预约号码。";
    }

    // 处理行李托运
    public String processLuggageCheck(double weight, double volume) {
        if (weight > 23.0 || volume > 1.5) {
            return "Payment required.";
        }
        return "Luggage checked successfully.";
    }
}
