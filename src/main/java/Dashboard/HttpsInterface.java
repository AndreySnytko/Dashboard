package Dashboard;


import org.w3c.dom.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpsInterface
{

    private String url;
    private String connectionError;
    private int error=0;


    public HttpsInterface(String url)
    {
        //TODO: Добавить проверку на корректность url
        this.url=url;
    }


    public String sendRequest()
    {

        String responseXml="";

        try
        {
            URL httpurl = new URL(this.url);


            HttpURLConnection httpConnection = (HttpURLConnection) httpurl.openConnection();

            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Accept", "application/xml");
            httpConnection.setRequestProperty("Content-Type", "application/xml");

            httpConnection.setDoOutput(true);
            OutputStream outStream = httpConnection.getOutputStream();
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");


            outStreamWriter.write(responseXml);
            outStreamWriter.flush();
            outStreamWriter.close();
            outStream.close();

            //TODO: Добавить логирование ответов от сервера.
            //System.out.println("ResponseCode:"+httpConnection.getResponseCode());
            //  System.out.println("ResponseMessage:"+httpConnection.getResponseMessage());

            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                responseXml=responseXml+inputLine;
            }
            in.close();

        }
        catch (SocketTimeoutException se)
        {
            connectionError="SocketTimeoutException";
            error=1;
        }
        catch (MalformedURLException me)
        {
            connectionError="MalformedURLException";
            error=2;
        }
        catch (Exception e)
        {
            connectionError="Another error";
            error=3;

        }finally {
            //TODO: Добавить логирование результатов
            //System.out.println("connectionError:"+connectionError);
        }

        return responseXml;
    }


}
