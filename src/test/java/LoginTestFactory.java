import org.testng.annotations.Factory;

public class LoginTestFactory {

    @Factory
    public Object[] createInstances() {
        return new Object[] {
                new LoginTest("1354737307", "0123698745doruk", true), // Geçerli kullanıcı adı ve şifre
                new LoginTest("wrongusername", "wrongpassword", false), // Geçersiz kullanıcı adı ve şifre
                new LoginTest("1354737307", "wrongpassword", false), // Geçerli kullanıcı adı ve yanlış şifre
                new LoginTest("wrongusername", "0123698745doruk", false), // Yanlış kullanıcı adı ve geçerli şifre
                new LoginTest(" ", "0123698745doruk", false), // Boş kullanıcı adı
                new LoginTest("1354737307", " ", false), // Boş şifre
                new LoginTestChrome("1354737307", "0123698745doruk", true), // Chrome'da geçerli kullanıcı adı ve şifre
                new LoginTestChrome("wrongusername", "wrongpassword", false), // Chrome'da geçersiz kullanıcı adı ve şifre
                new LoginTestChrome("1354737307", "wrongpassword", false), // Chrome'da geçerli kullanıcı adı ve yanlış şifre
                new LoginTestChrome("wrongusername", "0123698745doruk", false), // Chrome'da yanlış kullanıcı adı ve geçerli şifre
                new LoginTestChrome(" ", "0123698745doruk", false), // Chrome'da boş kullanıcı adı
                new LoginTestChrome("1354737307", " ", false) // Chrome'da boş şifre
        };
    }
}
