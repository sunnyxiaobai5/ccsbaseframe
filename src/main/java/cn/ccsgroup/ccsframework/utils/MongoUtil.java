package cn.ccsgroup.ccsframework.utils;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(MongoUtil.class);

    /**
     * client object
     */
    private static Mongo client;

    private MongoUtil() {
    }


    public static Mongo getClient() throws UnknownHostException {
        LoadProperties load = new LoadProperties("/resources.properties");
        String host = load.getValue("mongo.host");
        int port = Integer.parseInt(load.getValue("mongo.port"));
        client = client == null ? new MongoClient(host, port) : client;
        return client;
    }



}
