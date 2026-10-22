package org.firstinspires.ftc.teamcode.Competition_Code.Utilities;

public class OptimalShot {

    static int max_choices = 8 * 5;

    public static double pollen_hive = 0;
    public static double nectar_hive = 2;
    public static double pollen_robot = 1;
    public static double nectar_robot = 3;
    public static double pollen_tipping_val = 1.0 / 8;
    public static double nectar_tipping_val = 1.0 / 5;

    public static double marigin = 0.025;

    static double[][] total_shots = new double[max_choices][2];
    static double[][] optimal_and_tip = new double[max_choices][2];
    static double[][] most_optimal_shots = new double[max_choices][2];

    public static int num_total_shots = 0;
    public static int num_optimal_and_tip_shots = 0;
    public static int num_most_optimal_shots = 0;


    public static class Shot {
        public double pollen;
        public double nectar;

        public Shot(double pollen, double nectar) {
            this.nectar = nectar;
            this.pollen = pollen;
        }
    }

    public static Shot findOptimalShot() {
        double optimal_ball_num = 8;
        for (double pollen = 0; pollen < 9; pollen++) {
            for (double nectar = 0; nectar < 6; nectar++) {
                double total_pollen = pollen + pollen_hive;
                double total_nectar = nectar + nectar_hive;
                double tipping_value = total_pollen / 8 + total_nectar / 5;

                if (pollen_robot >= pollen && nectar_robot >= nectar) {
                    total_shots[num_total_shots] = new double[]{pollen, nectar};
                    num_total_shots++;
                }

                if (tipping_value >= 1 - marigin) {
                    if (pollen_robot >= pollen && nectar_robot >= nectar && total_pollen + total_nectar <= 8) {
                        optimal_and_tip[num_optimal_and_tip_shots] = new double[]{pollen, nectar};
                        num_optimal_and_tip_shots++;
                        if (pollen + nectar < optimal_ball_num) {
                            optimal_ball_num = pollen + nectar;
                        }
                    }
                }
            }
        }

        double optimal_tipping_val = 1;
        for (int shot = 0; shot < optimal_and_tip.length; shot++) {
            double pollen = optimal_and_tip[shot][0];
            double nectar = optimal_and_tip[shot][1];
            if (pollen + nectar == optimal_ball_num) {
                most_optimal_shots[num_most_optimal_shots] = new double[]{pollen, nectar};
                num_most_optimal_shots++;
                double tipping_val = pollen * pollen_tipping_val + nectar * nectar_tipping_val;
                if (tipping_val < optimal_tipping_val) {
                    optimal_tipping_val = tipping_val;
                }
            }
        }

        for (int shot = 0; shot < num_optimal_and_tip_shots; shot++) {
            double pollen = optimal_and_tip[shot][0];
            double nectar = optimal_and_tip[shot][1];
            if (pollen * pollen_tipping_val + nectar * nectar_tipping_val == optimal_tipping_val) {
                return new Shot(pollen, nectar);
            }
        }
        if (num_most_optimal_shots == 0) {
            return new Shot(pollen_robot, nectar_robot);
        }
        return new Shot(0, 0);
    }
}