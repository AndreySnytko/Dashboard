package Dashboard;

import com.mongodb.*;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;

public class Mongo {

    private MongoClient mongoClient; // клиент для подключения к БД
    private DB db;
    private boolean authenticate; // состояние подключения к БД
    private DBCollection table;

    private String errorText;
    private int error=0;

    final static Logger logger = Logger.getLogger(Mongo.class);


    public Mongo(Configuration config) {
        try {

            mongoClient = new MongoClient( config.getString("host"), Integer.valueOf(config.getString("port")) );
            db = mongoClient.getDB(config.getString("dbname"));
            authenticate = db.authenticate(config.getString("login"), config.getString("password").toCharArray());
            table = db.getCollection(config.getString("table"));

        } catch (UnknownHostException e) {
            error=1; errorText="Не смог подключиться к БД: "+config.getString("host")+":"+config.getString("port")+"/"+config.getString("dbname")+"/"+config.getString("table");
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