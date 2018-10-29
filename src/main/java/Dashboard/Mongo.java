package Dashboard;

import com.mongodb.*;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.net.ConnectException;
import java.net.UnknownHostException;

public class Mongo {

    private MongoClient mongoClient; // клиент для подключения к БД
    private DB db;
    private boolean authenticate; // состояние подключения к БД
    private DBCollection table;

    private String host="127.0.0.1";
    private String port="27017";
    private String dbName="dashboard";
    private String collectionName="visitors";



    private String errorText;
    private int error=0;

    final static Logger logger = Logger.getLogger(Mongo.class);


    public Mongo(Configuration config) {
        try {

            if(config.getString("host")!=null){ //Если в файле конфига нашли параметры то используем их, иначе используем параметры для чистой локальной БД

                port=(config.getString("port")==null)           ?"27017":config.getString("port");
                dbName=(config.getString("dbname")==null)       ?"dashboard":config.getString("dbname");
                collectionName=(config.getString("table")==null)?"visitors":config.getString("table");
                host=config.getString("host");

                mongoClient = new MongoClient( host, Integer.valueOf(port) );
                db = mongoClient.getDB(dbName);
                if(config.getString("login")!=null && config.getString("password")!=null) {
                    authenticate = db.authenticate(config.getString("login"), config.getString("password").toCharArray());
                }

                table = db.getCollection(collectionName);
                logger.info(" Подключился к БД: "+host+":"+port+"/"+dbName+"/"+collectionName);


            }else{ //Если в файле конфига не заданы параметры для DB, то подключаемся к локальной

                mongoClient = new MongoClient();//System.out.println("Mongo:"+mongoClient.getDatabaseNames().toString());
                db = mongoClient.getDB(dbName);
                table = db.getCollection(collectionName);

                logger.info("Подключился к локальной БД "+dbName+"/"+collectionName);
            }

        }catch(MongoException e) {
            error = 1;
            errorText = "Не смог подключиться к БД (MongoException): " + host + ":" + port + "/" + dbName + "/" + collectionName;
            logger.error(errorText);
            logger.debug(errorText, e);
        }catch (Exception e) {
            error=2; errorText="Не смог подключиться к БД (Exception): "+host+":"+port+"/"+dbName+"/"+collectionName;
            logger.error(errorText);
            logger.debug(errorText,e);
        }
    }

    public void close(){
        mongoClient.close();
    }


    public float showAll(){
        try{
            BasicDBObject query = new BasicDBObject();
            DBCursor cursor = table.find();
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        }catch (Exception e){
            logger.error("Не смог получить данные из базы:"+host+":"+port+"/"+dbName+"/"+collectionName);
        }
        return 0;
    }


    public void add(){
        try{
            BasicDBObject document = new BasicDBObject();
            document.put("counter", "visitors");
            document.put("value", 1);
            table.insert(document);
        }catch (Exception e){
            logger.error("Не смог получить данные из базы:"+host+":"+port+"/"+dbName+"/"+collectionName);
        }
    }

    public int getCounterValue(){
        try {
            BasicDBObject query = new BasicDBObject();
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("counter", "visitors");
            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                BasicDBObject object = (BasicDBObject) cursor.next();
                Integer counter = object.getInt("value");
                return counter;
            }
        }catch(Exception e){
            logger.error("Не смог получить данные из базы:"+host+":"+port+"/"+dbName+"/"+collectionName);

        }
        return 0;
    }

    public void update(){
        try {
            BasicDBObject newDocument = new BasicDBObject();
            newDocument=new BasicDBObject().append("$inc",new BasicDBObject().append("value", 1)); //Увеличиваем счетчик на 1
            table.update(new BasicDBObject().append("counter", "visitors"),newDocument,true,true);//Обновляем значение для всех, добавляем если нет такого
        }catch (Exception e){
            logger.error("Не смог получить данные из базы:"+host+":"+port+"/"+dbName+"/"+collectionName);
        }
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