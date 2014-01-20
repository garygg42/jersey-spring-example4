package org.tegner.niclas.jerseyspring.example4.resource;

import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Component;
import org.tegner.niclas.jerseyspring.example4.dao.SlowFruitDao;
import org.tegner.niclas.jerseyspring.example4.model.Fruit;

@Component
@Path("fruit")
public class FruitResource {
    
    private final Logger logger = LoggerFactory.getLogger(FruitResource.class);
    
    @Autowired
    private SlowFruitDao fruitDao;

    @GET
    @Path("{fruitid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Cacheable(value = "fruit")
    public Fruit getFruit(@PathParam("fruitid") Long fruitId) {
        logger.debug("getFruit, fruitId: {}", fruitId);
        return fruitDao.find(fruitId);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newFruit(Fruit fruit) {
        logger.debug("newFruit, fruit: {}", fruit);
        Long id = fruitDao.create(fruit);
        URI createdUri = URI.create(id.toString());
        return Response.created(createdUri).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @CacheEvict(value = "fruit", key = "#fruit.id")
    public void updateFruit(Fruit fruit) {
        logger.debug("updateFruit, fruit: {}", fruit);
        fruitDao.update(fruit);
    }
    
    @DELETE
    @Path("{fruitid}")
    @CacheEvict(value = "fruit")
    public void deleteFruit(@PathParam("fruitid") Long fruitId) {
        logger.debug("deleteFruit, fruitId: {}", fruitId);
        fruitDao.delete(fruitId);
    }
}
