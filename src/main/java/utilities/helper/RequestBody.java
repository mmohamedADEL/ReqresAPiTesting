package utilities.helper;

public class RequestBody {
    public static String getLoginRequestBody(String email, String password) {
        return "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";
    }

    public static String getUserRequestBody(String name, String job) {
        return "{ \"name\": \"" + name + "\", \"job\": \"" + job + "\" }";
    }

    public static String getRegisterRequestBody(String email, String password) {
        return "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";
    }
}
