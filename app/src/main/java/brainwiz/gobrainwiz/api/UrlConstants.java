package brainwiz.gobrainwiz.api;

/**
 */
public interface UrlConstants {


    String BASE_URL = "http://apshutters.justfordemo.biz/Services/Admin/Admin.svc/";

    String LOGIN = BASE_URL + "Salesman.svc/login?";
    String REGISTRATION = BASE_URL + "Cust_Registration.svc/custreg";
    String FORGOT_PASSWORD = BASE_URL + "Cust_Registration.svc/ForgotPassword?";
    String STATES = BASE_URL + "ShowCountries.svc/States?";
    String CITIES = BASE_URL + "ShowCountries.svc/City?";
    String SEARCH = BASE_URL + "Salesman.svc/salesmanSearch?";
    //http://apshutters.justfordemo.biz/Services/Salesman/Salesman.svc/salesmanSearch?keyword=Sr&CommandType=StartsWith
}
