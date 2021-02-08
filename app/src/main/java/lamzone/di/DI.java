package lamzone.di;

import lamzone.service.DummyApiService;
import lamzone.service.ApiService;

public class DI {

    private static ApiService service = new DummyApiService();

    public static ApiService getApiService() { return service; }

    public static ApiService getNewInstanceApiService() {
        return new DummyApiService();
    }
}
