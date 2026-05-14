package framework.config;

public final class TestConfig {

    public static final String BASE_URL = "https://practicesoftwaretesting.com";

    private TestConfig() {
    }

    public static String browser() {
        return System.getProperty("browser", "chrome").toLowerCase();
    }
}
