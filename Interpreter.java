import java.io.*;
import java.lang.Math;
import java.util.*;

public class Interpreter {

	private static Scanner file;

	public static void main(String[] args) {
		
		try {
			file = new Scanner(System.in);
			System.out.print("Enter the text: ");
			String name = file.nextLine();
			
			FileInputStream fstream = new FileInputStream(name);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine;
			int aCounter = 0;
			String[] array = new String[45];
			
			int lim1 = 0;
			int lim2 = 0;
			int aLength = 0;
			String limit = "+9 999 999 999";
			String limit2 = "";
			
			
			// Read File Line By Line And Add To An Array
			while ((strLine = br.readLine()) != null)
			{
				strLine = strLine.trim();
				
				if (strLine.length()!=0)
				{
					array[aCounter] = strLine;
					aCounter++;
				}
			}
			
			
			// Find The Points In The Array
			System.out.println("Codes:");
			for(int i = 0; i < array.length; i++)
			{		
				if(array[i] != null)
				{
					aLength++;
					
					if(array[i].equals(limit))
					{
						lim1 = i + 1;
						limit2 = limit;
						limit = "";
					}
					else if(array[i].equals(limit2))
					{
						lim2 = i + 1;
					}
					System.out.println(array[i]);
				}
			}
			
			
			// Initializes The Instructions And The Values With The Codes
			System.out.println("\nInstructions:");
			double[] data = new double[lim1 - 1];
			String[] intr = new String[(lim2 - lim1) - 1];
			String[] value = new String[aLength - lim2];
			for(int i = 0; i < intr.length; i++)
			{
				intr[i] = array[i + lim1];
				System.out.println(intr[i]);
			}
			
			System.out.println("\nValues:");
			for(int i = 0; i < value.length; i++)
			{
				value[i] = array[i + lim2];
				System.out.println(value[i]);
			}
			

			// Performs The Actions
			System.out.println("\nResult:");
			int inx = 0;
			for(int i = 0; i < intr.length; i++)
			{
				//System.out.print(intr[i] + " ");
				String[] action = intr[i].split("\\s+");
				
				if(action[0].equals("+0")) //Move	data(zzz) <--- data(xxx)
				{
					int move = Integer.parseInt(action[1]);
					int dest = Integer.parseInt(action[3]);
					
					data[dest] = data[move];
				}
				if(action[0].equals("+1")) //Add	data(zzz) <--- data(xxx) + data(yyy)
				{
					int add1 = Integer.parseInt(action[1]);
					int add2 = Integer.parseInt(action[2]);
					int sum = Integer.parseInt(action[3]);
					
					data[sum] = data[add1] + data[add2];
				}
				if(action[0].equals("-1")) //Subtract	data(zzz) <--- data(xxx) - data(yyy)
				{
					int sub1 = Integer.parseInt(action[1]);
					int sub2 = Integer.parseInt(action[2]);
					int diff = Integer.parseInt(action[3]);
					
					data[diff] = data[sub1] - data[sub2];
				}
				if(action[0].equals("+2")) //Multiply	data(zzz) <--- data(xxx) * data(yyy)
				{
					int fact1 = Integer.parseInt(action[1]);
					int fact2 = Integer.parseInt(action[2]);
					int prod = Integer.parseInt(action[3]);
					
					data[prod] = data[fact1] * data[fact2];
				}
				if(action[0].equals("-2")) //Divide		data(zzz) <--- data(xxx) / data(yyy)
				{
					int divd = Integer.parseInt(action[1]);
					int divs = Integer.parseInt(action[2]);
					int quo = Integer.parseInt(action[3]);
					
					data[quo] = data[divd] / data[divs];
				}
				if(action[0].equals("+3")) //Square		data(zzz) <--- data(xxx);
				{
					int num = Integer.parseInt(action[1]);
					int sqrd = Integer.parseInt(action[3]);
					
					data[sqrd] = Math.pow(data[num], 2);
				}
				if(action[0].equals("-3")) //Square Root	data(zzz) <--- sqrt(data(xxx))
				{
					int num = Integer.parseInt(action[1]);
					int sqrt = Integer.parseInt(action[3]);
					
					data[sqrt] = Math.sqrt(data[num]);
				}
				if(action[0].equals("+4")) //Equals		if data(xxx) == data(yyy) goto location zzz
				{
					int fst = Integer.parseInt(action[1]);
					int sec = Integer.parseInt(action[2]);
					int loc = Integer.parseInt(action[3]);
					
					if(data[fst] == data[sec])
					{
						i = loc - 1;
					}
				}
				if(action[0].equals("-4")) //Not Equals		if data(xxx) != data(yyy) goto location zzz
				{
					int fst = Integer.parseInt(action[1]);
					int sec = Integer.parseInt(action[2]);
					int loc = Integer.parseInt(action[3]);
					
					if(data[fst] != data[sec])
					{
						i = loc - 1;
					}
				}
				if(action[0].equals("+5")) //Greater Than Or Equal To	if data(xxx) >= data(yyy) goto location zzz
				{
					int fst = Integer.parseInt(action[1]);
					int sec = Integer.parseInt(action[2]);
					int loc = Integer.parseInt(action[3]);
					
					if(data[fst] >= data[sec])
					{
						i = loc - 1;
					}
				}
				if(action[0].equals("-5")) //Less Than		if data(xxx) <  data(yyy) goto location zzz
				{
					int fst = Integer.parseInt(action[1]);
					int sec = Integer.parseInt(action[2]);
					int loc = Integer.parseInt(action[3]);
					
					if(data[fst] < data[sec])
					{
						i = loc - 1;
					}
				}
				if(action[0].equals("+6")) //GETA	data(ddd) <--- data(xxx + data(iii))
				{
					int org = Integer.parseInt(action[1]);
					int ext = Integer.parseInt(action[2]);
					int tog = Integer.parseInt(action[3]);
					
					data[tog] = data[(int) (org + data[ext])];
				}
				if(action[0].equals("-6")) //PUTA	data(yyy + data(iii)) <--- data(sss)
				{
					int reg = Integer.parseInt(action[1]);
					int org = Integer.parseInt(action[2]);
					int ext = Integer.parseInt(action[3]);
					
					data[(int) (org + data[ext])] = data[reg];
				}
				if(action[0].equals("+7")) //LOOP	if (++data(iii) < data(nnn)) goto loc. ddd
				{
					int loop = Integer.parseInt(action[1]);
					int comp = Integer.parseInt(action[2]);
					int loc = Integer.parseInt(action[3]);
					
					while(data[loop]++ < data[comp])
					{
						i = loc - 1;
					}
				}
				if(action[0].equals("+8")) //Read	data(ddd) <--- read_float
				{
					int txt = Integer.parseInt(action[3]);
					String rmv = value[inx].replaceAll("\\s","");
					
					if(rmv.charAt(0) == '+')
					{
						int part = Integer.parseInt(rmv.substring(1, 3));
						
						if(part < 50 && part != 0)
						{
							double num = Double.parseDouble(rmv.substring(3,11));
							
							num = num / (Math.pow(10,8));
							num = num * Math.pow(10,(part-50));
							
							data[txt] = num;
							inx++;
						}
						else if(part > 50)
						{
							double num = Double.parseDouble(rmv.substring(3,11));
							
							num = num / (Math.pow(10,8));
							num = num * Math.pow(10,(part-50));
							
							data[txt] = num;
							inx++;
						}
						else
						{
							data[txt] = Double.parseDouble(rmv.substring(3,11));
							inx++;
						}
					}
					
					if(rmv.charAt(0) == '-')
					{
						int part = Integer.parseInt(rmv.substring(1, 3));
						
						if(part < 50 && part != 0)
						{
							double num = Double.parseDouble(rmv.substring(3,11));
							
							num = num / (Math.pow(10,8));
							num = num * Math.pow(10,(part-50));
							
							data[txt] = num * -1;
							inx++;
						}
						else if(part > 50)
						{
							double num = Double.parseDouble(rmv.substring(3,11));
							
							num = num / (Math.pow(10,8));
							num = num * Math.pow(10,(part-50));
							
							data[txt] = num * -1;
							inx++;
						}
						else
						{
							data[txt] = Double.parseDouble(rmv.substring(3,11)) * -1;
							inx++;
						}
					}
				}
				if(action[0].equals("-8")) //Move	write_float(data(sss))
				{
					int prnt = Integer.parseInt(action[1]);
					
					System.out.println(data[prnt]);
				}
			}
			
			
			System.out.println("\nInformation:");
			System.out.println("Number of Lines: " + aLength);
			System.out.println("Index of Limit 1: " + lim1);
			System.out.println("Index of Limit 2: " + lim2 + "\n");
			// Close the input stream
			br.close();
			
		} catch (Exception e) {// Catch exception if any
			
			System.err.println("Error: " + e.getMessage());
			
		}

	}

}
