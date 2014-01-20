package org.tegner.niclas.jerseyspring.example4.dao;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tegner.niclas.jerseyspring.example4.model.Fruit;

@Component
public class SlowFruitDao extends FruitDao {

    private final Logger logger = LoggerFactory.getLogger(SlowFruitDao.class);

    @Override
    @PostConstruct
    protected void populateDao() {
        super.populateDao();

        logger.debug("slow populateDao");
        
        Fruit fruit = new Fruit();
        fruit.setName("Grape");
        fruit.setDescription("Yummy!");
        create(fruit);
        
        fruit = new Fruit();
        fruit.setName("Cherry");
        fruit.setDescription("Watch out for the core!");
        create(fruit);

        fruit = new Fruit();
        fruit.setName("Pear");
        fruit.setDescription("Crisp and delicious.");
        create(fruit);
    }

    
    @Override
    public Fruit find(Long id) {
        logger.debug("slow find, id : {}", id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            logger.error("Could not sleep..", ex);
        }
        return super.find(id);
    }
}
