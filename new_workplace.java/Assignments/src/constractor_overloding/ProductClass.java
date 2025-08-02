package constractor_overloding;
class Product{
    private String name;
    private double price;
    private int quantity;

    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product(String name) {
        this.name = name;
        this.price = 0.0;
        this.quantity = 0;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
public class ProductClass {
	public static void main(String[] args) {
		Product p = new Product("laptop");
		System.out.println("first method");
		System.out.println(p.getName());
		Product p1 =new Product("mobile", 10000);
		System.out.println("second constructor overloading");
		System.out.println(p1.getName());
		System.out.println(p1.getPrice());
		Product p2 = new Product("books", 50, 10);
		System.out.println("third constructor overloading");
		System.out.println(p2.getName());
		System.out.println(p2.getPrice());
		System.out.println(p2.getQuantity());
	}
}
