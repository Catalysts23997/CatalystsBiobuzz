package org.firstinspires.ftc.teamcode.Competition_Code.Utilities;

public class OptimalShot {

    public static int pollen_hive = 0;
    public static int nectar_hive = 0;
    public static int pollen_robot = 0;
    public static int nectar_robot = 0;
    public static int pollen_tipping_val = 1/8;
    public static int nectar_tipping_val = 1/5;

    public static double marigin = 0.025;

    int[][] total_shots = new int[8*5][2];
    int[][] optimal_and_tip = new int[8*5][2];
    int[][] most_optimal_shots = new int[8*5][2];

    public int num_total_shots = 0;
    public int num_optimal_and_tip_shots = 0;
    public int num_most_optimal_shots = 0;




    public static class Shot {
        public int pollen;
        public int nectar;

        public Shot(int pollen, int nectar) {
            this.nectar = nectar;
            this.pollen = pollen;
        }
    }

    public Shot findOptimalShot() {
        int optimal_ball_num = 8;
        for(int pollen = 0; pollen < 9; pollen++){
            for(int nectar = 0; nectar < 6; nectar++){
                double total_pollen = pollen + pollen_hive;
                double total_nectar = nectar + nectar_hive;
                double tipping_value = total_pollen / 8 + total_nectar / 5;

                if(tipping_value >= 1 - marigin){
                    if(pollen_robot >= pollen && nectar_robot >= nectar){
                        total_shots[num_total_shots] = new int[]{pollen, nectar};
                        num_total_shots++;
                        if(total_pollen + total_nectar <= 8){
                            optimal_and_tip[num_optimal_and_tip_shots] = new int[]{pollen, nectar};
                            if(pollen + nectar < optimal_ball_num){
                                optimal_ball_num = pollen + nectar;
                            }
                            num_optimal_and_tip_shots++;
                        }
                    }
                }
            }
        }

        int count = 0;
        float optimal_tipping_val = 1;
        for(int shot = 0; shot < optimal_and_tip.length; shot++){
            int pollen = optimal_and_tip[shot][0];
            int nectar = optimal_and_tip[shot][1];
            if(pollen + nectar == optimal_ball_num){
                most_optimal_shots[count] = new int[]{pollen, nectar};
                count++;
                int tipping_val = pollen * pollen_tipping_val + nectar * nectar_tipping_val;
                if(tipping_val < optimal_tipping_val){
                    optimal_tipping_val = tipping_val;
                }
            }
        }

        for(int shot = 0; shot < most_optimal_shots.length; shot++){
            int pollen = most_optimal_shots[shot][0];
            int nectar = most_optimal_shots[shot][1];
            if(pollen * pollen_tipping_val + nectar * nectar_tipping_val == optimal_tipping_val){
                return new Shot(pollen, nectar);
            }
        }
        if(num_most_optimal_shots == 0){
            return new Shot(total_shots[total_shots.length - 1][0], total_shots[total_shots.length - 1][1]);
        }
        return new Shot(0, 0);
    }
}
