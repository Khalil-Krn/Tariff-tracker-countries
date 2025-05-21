package tariffManagementSystem;

public interface TariffPolicy {

	// Method to determine if a proposed tariff is accepted, conditionally accepted, or rejected
	String evaluateTrade(double proposedTariff, double minimumTariff);
}
