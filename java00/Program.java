
sealed class  Shape permits Circle, Cube
{

}

sealed class Circle  {

    public Circle()
    {
        super();
    }
}

public class Program
{
    public static int getSum(int number)
    {
        int s = 0;
        while (number != 0)
        {
            s += number % 10;
            number /= 10;
        }
        return s;
    }
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("requires at least an argument, no more no less");
            System.exit(1);
        }
        try {     
            int param = Integer.parseInt(args[0]);
            int res = getSum(param);
            System.out.println(res);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}