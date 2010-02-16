package ru.point.dao.impl;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

/**
 * Created by Mikhail Sedov [23.01.2010]
 */
@Repository
public class DatabaseManager {

    private static Logger logger = Logger.getLogger(DatabaseManager.class);

    private Environment environment;
    private EntityStore entityStore;

    private File envHome = new File("/data/db");

    private boolean allowCreate = true;
    private boolean transactional = true;

    public DatabaseManager() {
    }

    @PostConstruct
    public void init() throws DatabaseException {

        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        StoreConfig storeConfig = new StoreConfig();

        myEnvConfig.setAllowCreate(allowCreate);
        storeConfig.setAllowCreate(allowCreate);

        myEnvConfig.setTransactional(transactional);
        storeConfig.setTransactional(transactional);

        if (!envHome.exists()) {
            envHome.mkdirs();
        }

        environment = new Environment(envHome, myEnvConfig);
        entityStore = new EntityStore(environment, "EntityStore", storeConfig);

    }

    public Environment getEnvironment() {
        return environment;
    }

    public EntityStore getEntityStore() {
        return entityStore;
    }

    @PreDestroy
    public void close() {
        if (entityStore != null) {
            try {
                entityStore.close();
            } catch (DatabaseException dbe) {
                logger.error("Error closing store: " + dbe.toString());
            }
        }

        if (environment != null) {
            try {
                // Finally, close environment.
                environment.close();
            } catch (DatabaseException dbe) {
                logger.error("Error closing MyDbEnv: " + dbe.toString());
            }
        }
    }
}