package aut.bme.bookmanager.interactor

import aut.bme.bookmanager.interactor.auth.ApiKeyAuth
import aut.bme.bookmanager.interactor.network.NetworkConstants.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*

class ApiClient(authName: String) {
    private val apiAuthorizations: MutableMap<String, Interceptor?>
    private var adapterBuilder: Retrofit.Builder? = null

    /**
     * Helper constructor for single api key
     *
     * @param authName
     * @param apiKey
     */
    constructor(authName: String, apiKey: String) : this(authName) {
        setApiKey(apiKey)
    }

    /**
     * Creates the adapter and adds an authorization to be used by the client
     *
     * @param authName
     * @param authorization
     */
    private fun createAdapterWithInterceptor(
        authName: String,
        authorization: Interceptor?
    ) {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()
        if (apiAuthorizations.containsKey(authName)) {
            throw RuntimeException("auth name \"$authName\" already in api authorizations")
        }
        apiAuthorizations[authName] = authorization
        val okClient =
            OkHttpClient().newBuilder().addInterceptor(authorization).build()
        adapterBuilder = Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .client(okClient)
            .addConverterFactory(GsonCustomConverterFactory.create(gson))
    }

    fun <S> createService(serviceClass: Class<S>?): S {
        return adapterBuilder!!.build().create(serviceClass)
    }

    /**
     * Helper method to configure the first api key found
     *
     * @param apiKey
     */
    private fun setApiKey(apiKey: String) {
        for (apiAuthorization in apiAuthorizations.values) {
            if (apiAuthorization is ApiKeyAuth) {
                apiAuthorization.setApiKey(apiKey)
                return
            }
        }
    }

    /**
     * Basic constructor for single auth name
     *
     * @param authName
     */
    init {
        apiAuthorizations = LinkedHashMap()
        val auth: Interceptor = if (authName == "api-key") {
            ApiKeyAuth("query", "api-key")
        } else {
            throw RuntimeException("auth name \"$authName\" not found in available auth names")
        }
        createAdapterWithInterceptor(authName, auth)
    }
}