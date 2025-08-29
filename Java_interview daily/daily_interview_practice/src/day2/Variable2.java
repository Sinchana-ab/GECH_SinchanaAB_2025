package day2;

public class Variable2 {
	 static int s = 0;
	    int i = 0;
	    public void increatment() {
	    	int j = 0;
	    	s++;
	    	i++;
	    	j++;
	    	System.out.println("static s=" + s + "\t instance i=" + i + "\t localvariable j=" + j);
	    }
	    public static void main(String[] args) {
			Variable2 v1 = new Variable2();
			Variable2 v2 = new Variable2();
			
			v1.increatment();
			v1.increatment();
			v2.increatment();
		}
}
