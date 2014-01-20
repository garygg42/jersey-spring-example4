package org.tegner.niclas.jerseyspring.example4.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tegner.niclas.jerseyspring.example4.model.Fruit;

@Component
public class FruitDao {

    private final Logger logger = LoggerFactory.getLogger(FruitDao.class);
    
    private final Map<Long, Fruit> fruitMap = new HashMap();
    private final AtomicLong fruitCounter = new AtomicLong();
    
    @PostConstruct
    protected void populateDao() {
        logger.debug("populateDao");
        
        Fruit fruit = new Fruit();
        fruit.setName("Banana");
        fruit.setDescription("Tastes best when yellow.");
        create(fruit);
        
        fruit = new Fruit();
        fruit.setName("Apple");
        fruit.setDescription("Round and tasty");
        create(fruit);
    }
    
    public Fruit find(Long id) {
        logger.debug("find, id: {}", id);
        return fruitMap.get(id);
    }
    
    public Fruit delete(Long id) {
        logger.debug("delete, id: {}", id);
        return fruitMap.remove(id);
    }
    
    public Long create(Fruit fruit) {
        logger.debug("create, fruit: {}", fruit);
        if (fruit == null) {
            throw new NullPointerException("Create called with null input");
        }
        
        Long newId = fruitCounter.incrementAndGet();
        fruit.setId(newId);
        fruitMap.put(newId, fruit);
        return newId;
    }
    
    public void update(Fruit fruit) {
        logger.debug("update, fruit: {}", fruit);
        if (fruit == null) {
            throw new NullPointerException("Update called with null input");
        }
        
        Fruit foundFruit = fruitMap.get(fruit.getId());
        if (foundFruit != null) {
            foundFruit.setDescription(fruit.getDescription());
            foundFruit.setName(fruit.getName());
        }
    }
}
