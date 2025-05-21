package tariffManagementSystem;

public class Tariff {

	//Attribute
	private String destinationCountry;
    private String originCountry;
    private String productCategory;
    private double minimumTariff;
    
    //Parameterized Constructor
    public Tariff(String destinationCountry, String originCountry, String productCategory, double minimumTariff) {
        this.destinationCountry = destinationCountry;
        this.originCountry = originCountry;
        this.productCategory = productCategory;
        this.minimumTariff = minimumTariff;
    }
    //default constructor
    public Tariff() {
    	this("No destination yet", "No origin yet", "No product categroy yet", 0);
    }
    //Copy construtor
    public Tariff(Tariff otherTariff) {
    	this(otherTariff.destinationCountry, otherTariff.originCountry, otherTariff.productCategory, otherTariff.minimumTariff);
    }
    //getters
    public String getDestinationCountry() {
    	return this.destinationCountry;
    }
    public String getOriginCountry() {
    	return this.originCountry;
    }
    public String getProductCategory() {
    	return this.productCategory;
    }
    public double getMinimumTariff() {
    	return this.minimumTariff;
    }
    //setters
    public void setDestinationCountry(String destinationCountry) {
    	this.destinationCountry = destinationCountry;
    }
    public void setOriginCountry(String originCountry) {
    	this.originCountry = originCountry;
    }
    public void setProductCategory(String productCategory) {
    	this.productCategory = productCategory;
    }
    public void setMinimumTariff(double minimumTariff) {
    	this.minimumTariff = minimumTariff;
    }
    // Checks if two Tariff objects are equal
   @Override
   public boolean equals(Object otherObject) {
	   if(otherObject == null) {
		   return false;
	   }
	   if(this.getClass() != otherObject.getClass()) {
		   return false;
	   }
	   Tariff otherTariff = (Tariff) otherObject;
	   return this.destinationCountry.equals(otherTariff.destinationCountry) && this.originCountry.equals(otherTariff.originCountry) && this.productCategory.equals(otherTariff.productCategory) && this.minimumTariff == otherTariff.minimumTariff;
   }
   // Returns a comma-separated string representation of the tariff
   @Override
   public String toString() {
       return this.destinationCountry + "," + this.originCountry + "," + this.productCategory + "," + this.minimumTariff;
   }
   // Returns a deep copy of this Tariff object
   public Tariff clone() {
       return new Tariff(this);
   }

}
