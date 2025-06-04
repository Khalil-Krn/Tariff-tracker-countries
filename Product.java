package tariffManagementSystem;

public class Product implements Comparable<Product>{

	private String productName;
	private String country;
	private String category;
	private double initialPrice;
	private double finalPrice;
	
	// Parameterized constructor
	public Product(String productName, String country, String category, double initialPrice) {
		this.productName = productName;
		this.country = country;
		this.category = category;
		this.initialPrice = initialPrice;
		this.finalPrice = initialPrice;
	}
	// Default constructor
	public Product() {
		this("No name for the product yet","Country not known yet","Category not known yet",0);
	}
	//Copy Constructor
	public Product(Product otherProduct) {
		this(otherProduct.productName, otherProduct.country, otherProduct.category, otherProduct.initialPrice);
	}
	// Getters
	public String getProductName() {
        return productName;
    }
    public String getCountry() {
        return country;
    }
    public String getCategory() {
        return category;
    }
    public double getInitialPrice() {
        return initialPrice;
    }
    public double getFinalPrice() {
        return finalPrice;
    }

    //Setters
    public void setProductName(String productName) {
    	this.productName = productName;
    }
    public void setCountry(String country) {
    	this.country = country;
    }
    public void setCategory(String category) {
    	this.category = category;
    }
    public void setInitialPrice(double initialPrice) {
    	this.initialPrice = initialPrice;
    }
    public void setFinalPrice(double finalPrice) {
    	this.finalPrice = finalPrice;
    }
 // Applies the appropriate tariff based on country and category
    public void applyTariff() {
    	switch (country) {
        case "China":
            finalPrice = initialPrice * 1.25;
            break;
        case "USA":
            if (category.equalsIgnoreCase("Electronics"))
            	finalPrice = initialPrice * 1.10;
            break;
        case "Japan":
            if (category.equalsIgnoreCase("Automobiles"))
            	finalPrice = initialPrice * 1.15;
            break;
        case "India":
            if (category.equalsIgnoreCase("Agriculture"))
            	finalPrice = initialPrice * 1.05;
            break;
        case "South Korea":
            if (category.equalsIgnoreCase("Electronics"))
            	finalPrice = initialPrice * 1.08;
            break;
        case "Saudi Arabia":
            if (category.equalsIgnoreCase("Energy"))
            	finalPrice = initialPrice * 1.12;
            break;
        case "Germany":
            if (category.equalsIgnoreCase("Manufacturing"))
            	finalPrice = initialPrice * 1.06;
            break;
        case "Bangladesh":
            if (category.equalsIgnoreCase("Textile"))
            	finalPrice = initialPrice * 1.04;
            break;
        case "Brazil":
            if (category.equalsIgnoreCase("Agriculture"))
            	finalPrice = initialPrice * 1.09;
            break;
    }
    }
    // Checks if two products are equal based on all fields
    @Override
    public boolean equals(Object otherObject) {
    	if(otherObject == null) {
    		return false;
    	}
    	if(this.getClass() != otherObject.getClass()) {
    		return false;
    	}
    	Product otherProduct = (Product) otherObject;
    	return this.productName.equals(otherProduct.productName) && this.country.equals(otherProduct.country) && this.category.equals(otherProduct.category) && this.initialPrice == otherProduct.initialPrice && this.finalPrice == otherProduct.finalPrice;
    }
    // Returns the product data as a comma-separated string for file output
    @Override
    public String toString() {
    	return productName + "," + country + "," + category + "," + String.format("%.2f", finalPrice);
    }
    // Used for sorting products alphabetically
    @Override
    public int compareTo(Product otherProduct) {
        return this.productName.compareToIgnoreCase(otherProduct.productName);
    }

}
