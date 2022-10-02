public class Main {
    public static double a;
    public static double b;
    public static double c;
    public static double eps;
    public static double delta;
    //equation: x^3 + a*x^2 + b*x + c = 0
    public static void main(String[] args) {
        double x_1, x_2, x_3;
        double left, right;

        a = Double.parseDouble(args[0]);
        b = Double.parseDouble(args[1]);
        c = Double.parseDouble(args[2]);
        System.out.println("Equation is: x^3 +" + a + "*x^2 + " + b + "*x + " + c);
        eps = Double.parseDouble(args[3]);//0.0001;
        delta = Double.parseDouble(args[4]);//0.01;

        double A = 3;
        double B = 2 * a;
        double C = b;
        //Смотрим дискриминант и сравниваем его с 0
        double D = (B*B - 4*A*C);  //переделать подсчет дискриминанта
        System.out.println("Discriminant: " + D);
        if (D <= 0) { //если дискриминант меньше либо равен 0, то:
            System.out.println("f(0) = " + funcValue(0));
            if ( epsComparison(0)) { //если f(0) = c по модулю меньше eps, то 0 корень
                System.out.println("1.Root is:\nx_1 = 0");
            } else if (c < -eps) {        //если f(0) < - eps, то x лежит на отрезке [0, бесконечность)
                //ищем на интервале от 0 до беск
                left = 0.0;
                right = 999999999.0;
                x_1 = findIntervalDelta(left, right);
                System.out.println("2.Root is:\nx_1 = " + x_1);
            } else if (c > eps) {         //если f(0) > eps, то x лежит на отрезке (-бесконечность, 0]
                //ищем на интервале от 0 до -беск
                right = 0;
                left = -999999999.0;
                x_1 = findIntervalDelta(left, right);
                System.out.println("3.Root is:\nx_1 = " + x_1);
            }
        } else {
            //ищем экстремумы
            double extrA = (-B - Math.sqrt(D))/(2*A);
            double extrB = (-B + Math.sqrt(D))/(2*A);
            System.out.println("Extremum_1: " + extrA + ", Extremum_2: " + extrB);
            System.out.println("f(E_1): " + funcValue(extrA) + ", f(E_2): " + funcValue(extrB));

            if (epsComparison(extrA) && epsComparison(extrB)){
                System.out.println("4.Roots are:\nx_1 = " + extrA + "\nx_2 = " + extrB);
            } else if (epsComparison(extrA) && !epsComparison(extrB)) {
                x_1 = extrA;
                left = extrB;
                right = 999999999.0;
                x_2 = findIntervalDelta(left, right);
                System.out.println("5.Roots are:\nx_1 = " + x_1 + "\nx_2 = " + x_2);
            } else if (!epsComparison(extrA) && epsComparison(extrB)) {
                x_2 = extrB;
                left = -999999999.0;
                right = extrA;
                x_1 = findIntervalDelta(left, right);
                System.out.println("6.Roots are:\nx_1 = " + x_1 + "\nx_2 = " + x_2);
            } else if (funcValue(extrA) > eps && funcValue(extrB) > eps) {
                left = -999999999.0;
                right = extrA;
                System.out.println("left: " + left + " , right: " + right);
                x_1 = findIntervalDelta(left, right);
                System.out.println("7.Root is:\nx_1 = " + x_1);
            } else if (funcValue(extrA) < -eps && funcValue(extrB) < -eps) {
                left = extrB;
                right = 999999999.0;
                x_1 = findIntervalDelta(left, right);
                System.out.println("8.Root is:\nx_1 = " + x_1);
            } else if (funcValue(extrA) > eps && funcValue(extrB) < -eps){
                //x_1
                left = -999999999.0;
                right = extrA;
                x_1 = findIntervalDelta(left, right);
                System.out.println(x_1);
                //x_2
                left = extrA;
                right = extrB;
                x_2 = findIntervalDelta(left, right);
                System.out.println(x_2);
                //x_3
                left = extrB;
                right = 999999999.0;
                x_3 = findIntervalDelta(left, right);
                System.out.println("9.Roots are:\nx_1 = " + x_1 + "\nx_2 = " + x_2 + "\nx_3 = " + x_3);
            }
        }
    }

    public static boolean epsComparison(double a){
        return -eps < funcValue(a) && funcValue(a) < eps;
    }

    public static double findIntervalDelta(double left, double right){
        double x;
        if(right == 999999999.0){
            x = left;
            while(funcValue(x) < 0){
                x += delta;
            }
            left = x - delta;
            right = x;
            x = findRoot(left, right);
        } else if(left == -999999999.0){
            x = right;
            while(funcValue(x) > 0){
                x -= delta;
            }
            left = x;
            right = x + delta;
            x = findRoot(left, right);
        } else {
            x = (left + right) / 2;
            while(!( -eps < funcValue(x) && funcValue(x) < eps)){
                if(funcValue(x) > eps)
                    left = x;
                else
                    right = x;
                x = (left + right) / 2;
            }
        }
        return x;
    }

    public static double findRoot(double left, double right){
        double middle;
        middle = (left + right) / 2;
        while (!(-eps < funcValue(middle) && funcValue(middle) < eps)) {
            if (funcValue(middle) > eps)
                right = middle;
            else
                left = middle;
            middle = (left + right) / 2;
        }
        return middle;
    }

    public static double funcValue(double x){
        return x*x*x + a*x*x + b*x + c;
    }
}
