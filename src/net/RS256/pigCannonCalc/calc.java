package net.RS256.pigCannonCalc;

import java.util.Arrays;

public class calc {

    public static String[] calcDirection(int dX, int dZ) {

        String[] direction = new String[2];

        if (dX >= 0 && Math.abs(dZ) <= Math.abs(dX)){
            direction[0] = "0100";
            direction[1] = "1000";
        }else if (dZ <= 0 && Math.abs(dZ) >= Math.abs(dX)){
            direction[0] = "0001";
            direction[1] = "0100";
        }else if (dX <= 0 && Math.abs(dZ) <= Math.abs(dX)){
            direction[0] = "0010";
            direction[1] = "0001";
        }else if (dZ >= 0 && Math.abs(dZ) >= Math.abs(dX)){
            direction[0] = "1000";
            direction[1] = "0010";
        }

        return direction;
    }

    public static int[] calcTick(int dX, int dZ, double minDistance){

        int[] tick = new int[2];

        tick[0] = (int) Math.round((double) (dX + dZ) / (2 * minDistance));
        tick[1] = (int) Math.round((double) (dX - dZ) / (2 * minDistance));
        System.out.println(Arrays.toString(tick));

        return tick;
    }

    public static String[] tickToFormattedString(int[] tick){

        String[] formattedString = new String[2];
        formattedString[0] = String.format("%32s", Integer.toBinaryString(Math.abs(tick[0]))).replace(" ", "0");
        formattedString[1] = String.format("%32s", Integer.toBinaryString(Math.abs(tick[1]))).replace(" ", "0");

        String[] output = new String[6];
        output[0] = formattedString[0].substring(8, 22);
        output[1] = formattedString[0].substring(22, 26);
        output[2] = formattedString[0].substring(26,32);
        output[3] = formattedString[1].substring(8, 22);
        output[4] = formattedString[1].substring(22, 26);
        output[5] = formattedString[1].substring(26, 32);
        System.out.println(Arrays.toString(formattedString));
        System.out.println(Arrays.toString(output));

        return output;
    }

    public static double[] estimateTarget(int[] tick, int initialX, int initialZ, double minDistance){

        double[] estimatedCoordinate = new double[2];

        estimatedCoordinate[0] = initialX + (tick[0] + tick[1]) * minDistance + 0.50;
        estimatedCoordinate[1] = initialZ + (tick[0] - tick[1]) * minDistance + 0.50;

        return estimatedCoordinate;
    }
}