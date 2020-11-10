// Round 2 Sample Scenario 
public class R2SS3{
    public static void main(String[] args){
        // already: 1391 kg, $348,000,000
        // allowance: 209 kg, $202,000,000
        // in kilograms
        final double MAX_WEIGHT = 209;
        // in millions
        final double MAX_PRICE = 202;
        // in minutes (gotten from simulation)
        final double DATA_TRANSMITTED = 16300.613;

        // SCComp * DataTrans * CACS = 4 * 4 * 4 = 64 
        int numCombinations = 64;
        // comp, trans, cs, weight, price score
        String[][] combinations = new String[numCombinations][6];
        int counter = 0;

        // every 0.5 kg over weight, loses 1 point
        // every $200,000 over budget, loses 1 point
        // every 180 minutes, 1 point
        // power percentage multiplied by the data
        // bonus multiplied by the points
        double weight;
        double price;
        double score;
        
        for(SCComp comp : SCComp.values()){
            for(DataTrans trans : DataTrans.values()){
                for(CACS CS : CACS.values()){
                    weight = comp.getWeight() + trans.getWeight() + CS.getWeight();
                    price = comp.getPrice() + trans.getPrice() + CS.getPrice();
                    score = DATA_TRANSMITTED * trans.getDataPercentage() / 180 * (1+CS.getBonus());
                    if(weight > MAX_WEIGHT){
                        score -= (weight - MAX_WEIGHT)/0.5;
                    }
                    if(price > MAX_PRICE){
                        score -= (price - MAX_PRICE)/0.2;
                    }
                    combinations[counter][0] = comp.getName();
                    combinations[counter][1] = trans.getName();
                    combinations[counter][2] = CS.getName();
                    combinations[counter][3] = String.valueOf(weight);
                    combinations[counter][4] = String.valueOf(price);
                    combinations[counter][5] = String.valueOf(score);
                    counter++;
                }
            }
        }

        String[] solution = combinations[0];
        for(String[] combo : combinations){
            if(combo == combinations[0]){
                continue;
            }
            if(Double.parseDouble(combo[5]) > Double.parseDouble(solution[5])){
                solution = combo;
            } 
        }

        System.out.println("\nSOLUTION");
        System.out.println("--------------------------------------");
        System.out.println("Spacecraft Computer: " + solution[0]);
        System.out.println("Data Transmitter: " + solution[1]);
        System.out.println("Altitude Control System: " + solution[2]);
        System.out.println("Weight: " + solution[3]);
        System.out.println("Price: " + solution[4]);
        System.out.println("Score: " + solution[5]);
        System.out.println();
    }

    public enum SCComp{
        FB_120("FastByte 120", 49.8, 51.2),
        FB_125("FastByte 125", 44.5, 60.4),
        FB_130("FastByte 130", 39.4, 72.6),
        FB_135("FastByte 135", 32.4, 84.9);

        private final String name;
        private final double weight;
        private final double price;

        SCComp(String compName, double compWeight, double compPrice){
            name = compName;
            weight = compWeight;
            price = compPrice;
        }

        public String getName(){
            return name;
        }

        public double getWeight(){
            return weight;
        }

        public double getPrice(){
            return price;
        }
    }

    public enum DataTrans{
        V_2300("Validium 2300", 45, 15.4, 30.9),
        V_2400("Validium 2400", 48, 18.3, 32.6),
        V_2500("Validium 2500", 50, 21.6, 24.7),
        V_2600("Validium 2600", 52, 24.5, 36.2);

        private final String name;
        private final int power;
        private final double weight;
        private final double price;

        DataTrans(String transName, int transPower, double transWeight, double transPrice){
            name = transName;
            power = transPower;
            weight = transWeight;
            price = transPrice;
        }

        public String getName(){
            return name;
        }


        public int getPower(){
            return power;
        }

        // 52 dBW perfect
        // every 1 dBW below 52 dBW, percentage falls by 2.5%
        // (100 - 2.5 * (52 - x)), given x is power (dBW)
        public double getDataPercentage(){
            return (100 - 2.5 * (52 - power))/100;
        }

        public double getWeight(){
            return weight;
        }

        public double getPrice(){
            return price;
        }
    }

    public enum CACS{
        SP_350("SurePoint 350", 20, 121.6, 83.4),
        SP_360("SurePoint 360", 15, 132.0, 86.2),
        SP_370("SurePoint 370", 10, 143.2, 92.3),
        SP_380("SurePoint 380", 5, 153.1, 98.4);

        private final String name;
        private final int accuracy;
        private final double weight;
        private final double price;

        CACS(String CSName, int CSAccuracy, double CSWeight, double CSPrice){
            name = CSName;
            accuracy = CSAccuracy;
            weight = CSWeight;
            price = CSPrice;
        }

        public String getName(){
            return name;
        }

        public int getAccuracy(){
            return accuracy;
        }

        public double getBonus(){
            switch(accuracy){
                case 20:
                    return 0;
                case 15:
                    return 0.05;
                case 10:
                    return 0.10;
                case 5:
                    return 0.20;
            }
            return 0;
        }

        public double getWeight(){
            return weight;
        }

        public double getPrice(){
            return price;
        }
    }
}