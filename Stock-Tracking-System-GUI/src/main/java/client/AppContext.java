package client;

// tek bir client örneğini uygulama genelinde paylaşmak için
public class AppContext {
    private static final Client CLIENT = new Client();

    private AppContext(){}

    public static Client getClient(){
        return CLIENT;
    }
}
