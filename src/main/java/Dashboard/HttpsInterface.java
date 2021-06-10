package Dashboard;


import org.apache.log4j.Logger;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpsInterface
{

    private String url;

    private String errorText;
    private int error=0;

    final static Logger logger = Logger.getLogger(HttpsInterface.class);



    public HttpsInterface(String url)
    {
        //TODO: Добавить проверку на корректность url
        System.out.println("URL"+url);
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

            errorText="URL запроса: "+url+"   ResponseCode:"+httpConnection.getResponseCode()+" ResponseMessage:"+httpConnection.getResponseMessage();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseXml=responseXml+inputLine;
            }
            in.close();

        }
        catch (SocketTimeoutException se)
        {
            errorText="Не смог получить данные (SocketTimeoutException) по urlу: "+url;
            error=1;
            logger.error(errorText);
            logger.debug(errorText,se);
        }
        catch (MalformedURLException me)
        {
            errorText="Не смог получить данные (MalformedURLException) по urlу: "+url;
            error=2;
            logger.error(errorText);
            logger.debug(errorText,me);
        }
        catch (Exception e)
        {
            errorText="Не смог получить данные (Прочие ошибки) по urlу: "+url;
            error=3;
            logger.error(errorText);
            logger.debug(errorText,e);

        }
        finally {
            logger.info(errorText);
        }

        return responseXml;
    }


    public int getError() {
        return error;
    }

    public String getErrorText() {
        return errorText;
    }
}
