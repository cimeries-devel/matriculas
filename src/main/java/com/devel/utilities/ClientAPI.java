package com.devel.utilities;

import com.devel.models.ModeloApiDNI;
import com.google.gson.Gson;
import com.sun.istack.Nullable;

import java.awt.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

public class ClientAPI {
    private static HttpClient client;
    private static HttpRequest request;
    private static Propiedades propiedades= new Propiedades();

    public static ModeloApiDNI getUserByDni(String dni, @Nullable String token)  {
        if(null==token){
            token=propiedades.getTokenApiperu();
        }
        ModeloApiDNI model = null;
        HttpResponse<String> response = null;
        try{
            propiedades=new Propiedades();
            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://apiperu.dev/api/dni/%s", dni)))
                .timeout(Duration.ofSeconds(10))
                .setHeader("Authorization", "Bearer "+token)
                .GET()
                .build();
            System.out.println(propiedades.getTokenApiperu());
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (!response.body().contains("<!DOCTYPE html>")) {
                    System.out.println(response.body());
                    return new Gson().fromJson(response.body(), ModeloApiDNI.class);
                }else{
                    Utilities.sendNotification("Error","Token invalido, ingrese un token válido", TrayIcon.MessageType.ERROR);
                }
        }catch (HttpTimeoutException e){
            Utilities.sendNotification("Error","Tiempo de espera agotado", TrayIcon.MessageType.ERROR);
        }catch (ConnectException e){
            Utilities.sendNotification("Error","Error de conexión", TrayIcon.MessageType.ERROR);
        } catch (URISyntaxException |InterruptedException |IOException e) {
            e.printStackTrace();
        }
        return model;
    }


}
