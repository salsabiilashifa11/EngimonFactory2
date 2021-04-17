public class ElementAdvantage {
    public static double getAdv(String el1, String el2){
        if (el1.equals("fire")) {
            if (el2.equals("fire")) {
                return 1.0;
            }
            else if (el2.equals("water")) {
                return 0.0;
            }
            else if (el2.equals("electric")) {
                return 1.0;
            }
            else if (el2.equals("ground")) {
                return 0.5;
            }
            else if (el2.equals("ice")) {
                return 2.0;
            }
        }
        else if (el1.equals("water")) {
            if (el2.equals("fire")) {
                return 2.0;
            }
            else if (el2.equals("water")) {
                return 1.0;
            }
            else if (el2.equals("electric")) {
                return 0.0;
            }
            else if (el2.equals("ground")) {
                return 1.0;
            }
            else if (el2.equals("ice")) {
                return 1.0;
            }
        }
        else if (el1.equals("electric")) {
            if (el2.equals("fire")) {
                return 1.0;
            }
            else if (el2.equals("water")) {
                return 2.0;
            }
            else if (el2.equals("electric")) {
                return 1.0;
            }
            else if (el2.equals("ground")) {
                return 0.0;
            }
            else if (el2.equals("ice")) {
                return 1.5;
            }
        }
        else if (el1.equals("ground")) {
            if (el2.equals("fire")) {
                return 1.5;
            }
            else if (el2.equals("water")) {
                return 1;
            }
            else if (el2.equals("electric")) {
                return 2;
            }
            else if (el2.equals("ground")) {
                return 1;
            }
            else if (el2.equals("ice")) {
                return 0;
            }
        }
        else if (el1.equals("ice")) {
            if (el2.equals("fire")) {
                return 0;
            }
            else if (el2.equals("water")) {
                return 1;
            }
            else if (el2.equals("electric")) {
                return 0.5;
            }
            else if (el2.equals("ground")) {
                return 2;
            }
            else if (el2.equals("ice")) {
                return 1;
            }
        }

        return 0;
    }
}
