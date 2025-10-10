package assignment;

public class InterviewPredict {
	public static void main(String[] args) {
	//	System.out.println(10 % 4.0);//based on two integer input it integer object only 
		
//		for(int i = 1;i<=5;i++) {// here it will skip the 3 value it will print al the values
//			if(i == 3) continue;
//			System.out.println(i+" ");
//		}
//		for(int i = 1;i<=5;i++) {// here it will break that loop when it reaches to  3 value it will print al the values
//			if(i == 3) break;
//			System.out.println(i+" ");
//		}
		for(int i = 1;i<=3;i++) {
			for(int j =1;j<=3;j++) {
				if(i == 2) break;
				System.out.println(i+" "+j+" ");
			}
		}
		int x = 5;
		//x++ -> at present 5 only but in memeory 5 becomes 6
		//++x ->7
		System.out.println(x++ + ++x);
	}
}
