package aut.bme.bookmanager.interactor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import aut.bme.bookmanager.interactor.auth.ApiKeyAuth;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class ApiClient {

    private Map<String, Interceptor> apiAuthorizations;
    private OkHttpClient okClient;
    private Retrofit.Builder adapterBuilder;

    /**
     * Basic constructor for single auth name
     *
     * @param authName
     */
    public ApiClient(String authName) {
        apiAuthorizations = new LinkedHashMap<>();
        Interceptor auth;
        if (authName.equals("api-key")) {
            auth = new ApiKeyAuth("query", "api-key");
        } else {
            throw new RuntimeException("auth name \"" + authName + "\" not found in available auth names");
        }
        createAdapterWithInterceptor(authName, auth);
    }

    /**
     * Helper constructor for single api key
     *
     * @param authName
     * @param apiKey
     */
    public ApiClient(String authName, String apiKey) {
        this(authName);
        this.setApiKey(apiKey);
    }

    /**
     * Creates the adapter and adds an authorization to be used by the client
     *
     * @param authName
     * @param authorization
     */
    public void createAdapterWithInterceptor(String authName, Interceptor authorization) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();

        if (apiAuthorizations.containsKey(authName)) {
            throw new RuntimeException("auth name \"" + authName + "\" already in api authorizations");
        }
        apiAuthorizations.put(authName, authorization);

        okClient = new OkHttpClient().newBuilder().addInterceptor(authorization).build();

        String baseUrl = "https://api.nytimes.com/svc/books/v3";
        baseUrl = baseUrl + "/";

        adapterBuilder = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(okClient)
                .addConverterFactory(GsonCustomConverterFactory.create(gson));
    }

    public <S> S createService(Class<S> serviceClass) {
        return adapterBuilder.build().create(serviceClass);

    }

    /**
     * Helper method to configure the first api key found
     *
     * @param apiKey
     */
    private void setApiKey(String apiKey) {
        for (Interceptor apiAuthorization : apiAuthorizations.values()) {
            if (apiAuthorization instanceof ApiKeyAuth) {
                ApiKeyAuth keyAuth = (ApiKeyAuth) apiAuthorization;
                keyAuth.setApiKey(apiKey);
                return;
            }
        }
    }

    public Map<String, Interceptor> getApiAuthorizations() {
        return apiAuthorizations;
    }

    public void setApiAuthorizations(Map<String, Interceptor> apiAuthorizations) {
        this.apiAuthorizations = apiAuthorizations;
    }

    public Retrofit.Builder getAdapterBuilder() {
        return adapterBuilder;
    }

    public void setAdapterBuilder(Retrofit.Builder adapterBuilder) {
        this.adapterBuilder = adapterBuilder;
    }

    public OkHttpClient getOkClient() {
        return okClient;
    }

    public void addAuthsToOkClient(OkHttpClient okClient) {
        for (Interceptor apiAuthorization : apiAuthorizations.values()) {
            okClient.interceptors().add(apiAuthorization);
        }
    }

    /**
     * Clones the okClient given in parameter, adds the auth interceptors and uses it to configure the Retrofit
     *
     * @param okClient
     */
    public void configureFromOkclient(OkHttpClient okClient) {
        OkHttpClient clone = okClient.newBuilder().build();
        addAuthsToOkClient(clone);
        adapterBuilder.client(clone);
    }
}

