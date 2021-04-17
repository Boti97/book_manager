package aut.bme.bookmanager.interactor;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class GsonCustomConverterFactory extends Converter.Factory {
    public static GsonCustomConverterFactory create(Gson gson) {
        return new GsonCustomConverterFactory(gson);
    }

    private final Gson gson;
    private final GsonConverterFactory gsonConverterFactory;

    private GsonCustomConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        this.gsonConverterFactory = GsonConverterFactory.create(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type,
            @NotNull Annotation[] annotations,
            @NotNull Retrofit retrofit) {
        if (type.equals(String.class))
            return new GsonResponseBodyConverterToString<Object>(gson, type);
        else
            return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(
            @NotNull Type type,
            @NotNull Annotation[] parameterAnnotations,
            @NotNull Annotation[] methodAnnotations,
            @NotNull Retrofit retrofit) {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }


}