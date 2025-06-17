package adapter;

/**
 * Adapter pattern for different price formats and currencies
 */
public class PriceAdapter {
    private static final double USD_TO_TL_RATE = 30.0; // Example rate
    private static final double EUR_TO_TL_RATE = 33.0; // Example rate
    
    /**
     * Adapts USD price to TL
     */
    public static double adaptUsdToTl(double usdPrice) {
        return usdPrice * USD_TO_TL_RATE;
    }
    
    /**
     * Adapts TL price to USD
     */
    public static double adaptTlToUsd(double tlPrice) {
        return tlPrice / USD_TO_TL_RATE;
    }
    
    /**
     * Adapts EUR price to TL
     */
    public static double adaptEurToTl(double eurPrice) {
        return eurPrice * EUR_TO_TL_RATE;
    }
    
    /**
     * Adapts TL price to EUR
     */
    public static double adaptTlToEur(double tlPrice) {
        return tlPrice / EUR_TO_TL_RATE;
    }
    
    /**
     * Formats price with currency symbol
     */
    public static String formatPrice(double price, String currency) {
        switch (currency.toUpperCase()) {
            case "TL":
                return String.format("%.2f TL", price);
            case "USD":
                return String.format("$%.2f", price);
            case "EUR":
                return String.format("€%.2f", price);
            default:
                return String.format("%.2f %s", price, currency);
        }
    }
}