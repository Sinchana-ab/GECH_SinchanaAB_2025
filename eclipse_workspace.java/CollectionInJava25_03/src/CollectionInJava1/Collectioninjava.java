package CollectionInJava1;

public class Collectioninjava {

	public static void main(String[] args) {
		/*
		 * why we need colletion
		 * =========================
		 * to over come the drawback of array we go for collection
		 * 
		 * Drawback
				 * =====
				 * we cannot extend the size of array
				 * we cannot store *  type of elements
				 * we delete the array, it will not shrink
				 * 
				 *advantages
				 *===================
				 *random access
				 *type safety-the element inside the array is based on the data type 
				 *type cast-to print element in array  s=no need of type casting
		 * 
		 * */
		int [] arr=new int [2];
		arr[0]=10;
		arr[1]=90;
		System.out.println((int)arr[0]);//no need of type casting
		
		System.out.println(arr[1]);
	}
}
