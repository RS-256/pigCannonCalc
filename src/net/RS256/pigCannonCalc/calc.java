package net.RS256.pigCannonCalc;

public class calc {

    public static String[] calcDirection(int dX, int dZ) {

        double[] movedDistance = {
                ( dX + dZ) / Math.sqrt(2),
                (-dX + dZ) / Math.sqrt(2)
        };
        String[] direction = new String[2];

        if (movedDistance[0] <= 0){
            direction[0] = "1000"; // --
        } else if (movedDistance[0] >= 0){
            direction[0] = "0001"; // ++
        }

        if (movedDistance[1] <= 0){
            direction[1] = "0010"; // +-
        } else if (movedDistance[1] >= 0){
            direction[1] = "0100"; // -+
        }

        return direction;
    }

    public static int[] calcTick(int dX, int dZ, double minDistance){

        return new int[]{
                (int) Math.round((double) (dX + dZ) / (2 * minDistance)),
                (int) Math.round((double) (dX - dZ) / (2 * minDistance))
        };
    }

    public static String[] tickToFormattedString(int[] tick){

        String[] formattedString = new String[2];
        formattedString[0] = String.format("%32s", Integer.toBinaryString(Math.abs(tick[0]))).replace(" ", "0");
        formattedString[1] = String.format("%32s", Integer.toBinaryString(Math.abs(tick[1]))).replace(" ", "0");

        return new String[]{
                formattedString[0].substring(8, 22),
                formattedString[0].substring(22, 27),
                formattedString[0].substring(27,32),
                formattedString[1].substring(8, 22),
                formattedString[1].substring(22, 27),
                formattedString[1].substring(27, 32)
        };
    }

    public static int[] estimateTarget(int[] tick, int[] initialPos, double minDistance){

        return new int[]{
                (int) Math.round(initialPos[0] + (double) (tick[0] + tick[1]) * minDistance),
                (int) Math.round(initialPos[1] + (double) (tick[0] - tick[1]) * minDistance)
        };
    }

    public static double[] calcMotion(int[] tick){
        double magicNumber = 0.3919183622519959;
        return new double[]{
                (tick[0] + tick[1]) * magicNumber,
                (tick[0] - tick[1]) * magicNumber
        };
    }
}