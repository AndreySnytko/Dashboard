package Dashboard;

import com.mongodb.*;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.Properties;

public class Mongo {

    private MongoClient mongoClient; // клиент для подключения к БД
    private DB db;
    private boolean authenticate; // состояние подключения к БД
    private DBCollection table;

    private String errorText;
    private int error=0;

    final static Logger logger = Logger.getLogger(Mongo.class);


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

        } catch (UnknownHostException e) {
            error=1; errorText="Не смог подключиться к БД: "+prop.getProperty("host")+":"+prop.getProperty("port")+"/"+prop.getProperty("dbname")+"/"+prop.getProperty("table");
            logger.error(errorText);
            logger.debug(errorText,e);
        }
    }

    public void close(){
        mongoClient.close();
    }


    public float showAll(){
        BasicDBObject query = new BasicDBObject();
        DBCursor cursor = table.find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        return 0;
    }


    public void add(){
        BasicDBObject document = new BasicDBObject();
        document.put("counter", "visitors");
        document.put("value", 1);
        table.insert(document);
    }

    public int getCounterValue(){
        BasicDBObject query = new BasicDBObject();
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("counter", "visitors");
        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            BasicDBObject object = (BasicDBObject) cursor.next();
            Integer counter = object.getInt("value");
            return counter;
        }
        return 0;
    }

    public void update(){
        BasicDBObject newDocument = new BasicDBObject();
        newDocument=new BasicDBObject().append("$inc",new BasicDBObject().append("value", 1)); //Увеличиваем счетчик на 1
        table.update(new BasicDBObject().append("counter", "visitors"),newDocument,true,true);//Обновляем значение для всех, добавляем если нет такого
    }


    public void delete(){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("counter", "visitors");
        table.remove(searchQuery);
    }

    public int getError(){
        return error;
    }

    public String getErrorText() {
        return errorText;
    }
}