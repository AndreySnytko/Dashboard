package Dashboard;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Properties;

public class Mongo {

    private MongoClient mongoClient; // клиент для подключения к БД
    private DB db;
    private boolean authenticate; // состояние подключения к БД
    private DBCollection table;

    public Mongo(Properties prop) {
        try {

            // Создаем подключение
            mongoClient = new MongoClient( prop.getProperty("host"), Integer.valueOf(prop.getProperty("port")) );

            // Выбираем БД для дальнейшей работы
            db = mongoClient.getDB(prop.getProperty("dbname"));

            // Входим под созданным логином и паролем
            authenticate = db.authenticate(prop.getProperty("login"), prop.getProperty("password").toCharArray());

            // Выбираем коллекцию/таблицу для дальнейшей работы
            table = db.getCollection(prop.getProperty("table"));

            System.out.println("Connected");

        } catch (UnknownHostException e) {

            System.err.println("Don't connect!");
        }
    }

    public void addValue(){

    }

    public void getValue(){

    }


    public void close(){
        mongoClient.close();
    }


}