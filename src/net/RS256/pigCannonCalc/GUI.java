package net.RS256.pigCannonCalc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    JTextField initialXInput;
    JTextField initialZInput;
    JTextField targetXInput;
    JTextField targetZInput;
    JTextField golemCountInput;
    JLabel golemCountLabel;
    JLabel initialXLabel;
    JLabel initialZLabel;
    JLabel targetXLabel;
    JLabel targetZLabel;
    JLabel outputLabel;
    JTextArea ROM;
    JTextArea manual;
    JButton calculateButton;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        this.panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.panel.setLayout((LayoutManager) null);
        Font MonospaceFont = new Font("consolas", Font.PLAIN, 14);

        // initial X の表示

        this.initialXLabel = new JLabel("initial X");
        this.initialXLabel.setBounds(30, 15, 120, 30);
        this.panel.add(this.initialXLabel);
        this.initialXInput = new JTextField();
        this.initialXInput.setBounds(30, 45, 120, 30);
        this.panel.add(this.initialXInput);
        this.initialXInput.setText("0");

        // initial Z の表示

        this.initialZLabel = new JLabel("initial Z");
        this.initialZLabel.setBounds(180, 15, 120, 30);
        this.panel.add(this.initialZLabel);
        this.initialZInput = new JTextField();
        this.initialZInput.setBounds(180, 45, 120, 30);
        this.panel.add(this.initialZInput);
        this.initialZInput.setText("0");

        // target X の表示

        this.targetXLabel = new JLabel("target X");
        this.targetXLabel.setBounds(30, 90, 120, 30);
        this.panel.add(this.targetXLabel);
        this.targetXInput = new JTextField();
        this.targetXInput.setBounds(30, 120, 120, 30);
        this.panel.add(this.targetXInput);
        this.targetXInput.setText("0");

        // target Z の表示

        this.targetZLabel = new JLabel("target Z");
        this.targetZLabel.setBounds(180, 90, 120, 30);
        this.panel.add(this.targetZLabel);
        this.targetZInput = new JTextField();
        this.targetZInput.setBounds(180, 120, 120, 30);
        this.panel.add(this.targetZInput);
        this.targetZInput.setText("0");

        // golem countの表示

        this.golemCountLabel = new JLabel("golem count");
        this.golemCountLabel.setBounds(30, 165, 120, 30);
        this.panel.add(this.golemCountLabel);
        this.golemCountInput = new JTextField();
        this.golemCountInput.setBounds(30, 195, 120, 30);
        this.panel.add(this.golemCountInput);
        this.golemCountInput.setText("64");

        // calculate の表示

        this.calculateButton = new JButton("calculate!");
        this.calculateButton.setBounds(180, 195, 120, 29);
        this.calculateButton.addActionListener(this);
        this.panel.add(this.calculateButton);

        // output の表示

        this.outputLabel = new JLabel("output");
        this.outputLabel.setBounds(30, 240, 120, 30);
        this.panel.add(this.outputLabel);
        
        this.ROM = new JTextArea();
        this.ROM.setEditable(false);
        this.ROM.setBounds(30, 270, 270, 60);
        this.ROM.setBorder(BorderFactory.createEtchedBorder());
        this.ROM.setFont(MonospaceFont);
        this.panel.add(this.ROM);
        this.frame.add(this.panel, "Center");

        this.manual = new JTextArea();
        this.manual.setEditable(false);
        this.manual.setBounds(30, 345, 270, 60);
        this.manual.setBorder(BorderFactory.createEtchedBorder());
        this.manual.setFont(MonospaceFont);
        this.panel.add(this.manual);
        this.frame.add(this.panel, "Center");

        // タイトルとウィンドウサイズの指定

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setTitle("pig cannon calculator");
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setSize(345, 470);
        this.frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        int initialX;
        int initialZ;
        int targetX;
        int targetZ;
        int golemCount;

        // 無効な数字の検出

        try {
            initialX = Integer.parseInt(this.initialXInput.getText());
            initialZ = Integer.parseInt(this.initialZInput.getText());
            targetX = Integer.parseInt(this.targetXInput.getText());
            targetZ = Integer.parseInt(this.targetZInput.getText());
            golemCount = Integer.parseInt(this.golemCountInput.getText());
        }
        catch (Exception var11) {
            this.ROM.setText("Illegal argument");
            return;
        }
        if (golemCount <= 0) {
            this.ROM.setText("golem count must be > 0");
            return;
        }

        this.ROM.setText(ROMCalculate(initialX, initialZ, targetX, targetZ, golemCount));
        this.manual.setText(ManualCalculate(initialX, initialZ, targetX, targetZ, golemCount));
    }

// 方角の指定

    private static String ROMCalculate(int initialX, int initialZ, int targetX, int targetZ, int golemCount) {

        // 変数の定義

        String output;
        String direction1;
        String direction2;
        int dX = targetX - initialX;
        int dZ = targetZ - initialZ;
        int directionNum1 = 0;
        int directionNum2 = 0;
        int directionWidth = 4;
        int tickWidth = 17;
        int temp1;
        double minDistance = 0.48193359375 * golemCount;
        boolean same = false;

        if (Math.abs(dX) < minDistance * 16 && Math.abs(dZ) < minDistance * 16){
            output = "target is too short\nminimum distance is " + minDistance * 16;
            return output;
        }

        if (minDistance * 131072 * 16 *2 < Math.abs(dX) && minDistance * 131072 * 16 * 2 < Math.abs(dZ)){
            output = "target is too far away\nmaximum distance is " + minDistance * 131072 * 16;
            return output;
        }

        if (dX >= 0 && Math.abs(dZ) <= Math.abs(dX)){
            directionNum1 = 1000;
            directionNum2 = 1;
        }else if (dZ <= 0 && Math.abs(dZ) >= Math.abs(dX)){
            directionNum1 = 1;
            directionNum2 = 10;
        }else if (dX <= 0 && Math.abs(dZ) <= Math.abs(dX)){
            directionNum1 = 10;
            directionNum2 = 100;
        }else if (dZ >= 0 && Math.abs(dZ) >= Math.abs(dX)){
            directionNum1 = 100;
            directionNum2 = 1000;
        }

        if (minDistance * 16 >= Math.abs(dX) - Math.abs(dZ) && Math.abs(dX) - Math.abs(dZ) >= 0){
            directionNum2 = directionNum1;
            same = true;
        }

        // numを入れ替え

        if (directionNum1 > directionNum2){
            temp1 = directionNum2;
            directionNum2 = directionNum1;
            directionNum1 = temp1;
        }

        // numをdirectionに変換し0埋め

        direction1 = String.format("%0" + directionWidth + "d", directionNum1);
        direction2 = String.format("%0" + directionWidth + "d", directionNum2);

        // tickの計算

        double tick1 = (dX + dZ) / (2 * minDistance);
        double tick2 = (dX - dZ) / (2 * minDistance);

        if (same == true){
            tick1 = tick1 / 2;
            tick2 = tick1;
        }

        // tickを17bitのバイナリに変換

        String tickBin1 = String.format("%0" + tickWidth + "d", Integer.parseInt(Integer.toBinaryString((int) Math.abs(Math.round(tick1)))));
        String tickBin2 = String.format("%0" + tickWidth + "d", Integer.parseInt(Integer.toBinaryString((int) Math.abs(Math.round(tick2)))));

        // バイナリをoptとメインバイナリに分割

        String opt1 = tickBin1.substring(tickBin1.length() - 4);
        String opt2 = tickBin1.substring(tickBin2.length() - 4);

        tickBin1 = tickBin1.substring(0, tickBin1.length() - 4);
        tickBin2 = tickBin2.substring(0, tickBin2.length() - 4);

        output = "Binary -" + direction1 + "-" + tickBin1 + "-" + opt1 + "-\n       -" + direction2 + "-" + tickBin2 + "-" + opt2 + "-";

        return output;
    }

    private static String ManualCalculate(int initialX, int initialZ, int targetX, int targetZ, int golemCount){

        // 変数の定義

        String output;
        String direction1 = "0";
        String direction2 = "0";
        int dX = targetX - initialX;
        int dZ = targetZ - initialZ;
        int directionNum1 = 0;
        int directionNum2 = 0;
        int itemWidth = 4;
        int tickWidth = 17;
        int temp1;
        double minDistance = 0.48193359375 * golemCount;
        boolean same = false;

        if (Math.abs(dX) < minDistance * 8 && Math.abs(dZ) < minDistance * 8){
            output = "target is too short\nminimum distance is " + minDistance * 8;
            return output;
        }

        if (minDistance * 1728 * 8 *2 < Math.abs(dX) && minDistance * 1728 * 8 * 2 < Math.abs(dZ)){
            output = "target is too far away\nmaximum distance is " + minDistance * 1728 * 8;
            return output;
        }

        if (dX >= 0 && Math.abs(dZ) <= Math.abs(dX)){
            directionNum1 = 1000;
            directionNum2 = 1;
        }else if (dZ <= 0 && Math.abs(dZ) >= Math.abs(dX)){
            directionNum1 = 1;
            directionNum2 = 10;
        }else if (dX <= 0 && Math.abs(dZ) <= Math.abs(dX)){
            directionNum1 = 10;
            directionNum2 = 100;
        }else if (dZ >= 0 && Math.abs(dZ) >= Math.abs(dX)){
            directionNum1 = 100;
            directionNum2 = 1000;
        }

        if (minDistance * 8 >= Math.abs(dX) - Math.abs(dZ) && Math.abs(dX) - Math.abs(dZ) >= 0){
            directionNum2 = directionNum1;
            same = true;
        }

        // numを入れ替え

        if (directionNum1 > directionNum2){
            temp1 = directionNum2;
            directionNum2 = directionNum1;
            directionNum1 = temp1;
        }

        // numをdirectionに変換し0埋め

        if (directionNum1 == 1){
            direction1 = "SE";
        }else if (directionNum1 == 10){
            direction1 = "SW";
        }else if (directionNum1 == 100){
            direction1 = "NW";
        }else if (directionNum1 == 1000){
            direction1 = "NE";
        }
        if (directionNum2 == 1){
            direction2 = "SE";
        }else if (directionNum2 == 10){
            direction2 = "SW";
        }else if (directionNum2 == 100){
            direction2 = "NW";
        }else if (directionNum2 == 1000){
            direction2 = "NE";
        }

        // tickの計算

        double tick1 = (dX + dZ) / (2 * minDistance);
        double tick2 = (dX - dZ) / (2 * minDistance);

        if (same == true){
            tick1 = tick1 / 2;
            tick2 = tick1;
        }

        String itemCount1 = String.format("% " + itemWidth + "d" , (int) Math.abs(Math.round(tick1 / 8)));
        String itemCount2 = String.format("% " + itemWidth + "d" , (int) Math.abs(Math.round(tick2 / 8)));

        output = "Manual L : " + direction1 + " " + itemCount1 + "\n       R : " + direction2 + " " + itemCount2;

        return output;
    }
}
