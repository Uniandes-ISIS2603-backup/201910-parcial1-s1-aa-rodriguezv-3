/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.test.logic;

import co.edu.uniandes.csw.recipes.ejb.RecipeLogic;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Angel Rodriguez aa.rodriguezv
 */
@RunWith(Arquillian.class)
public class RecipeLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    RecipeLogic recipeLogic;

    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;
    
     private List<RecipeEntity> data = new ArrayList<RecipeEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipePersistence.class.getPackage())
                .addPackage(RecipeLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
        /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from RecipeEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RecipeEntity entity = factory.manufacturePojo(RecipeEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    @Test
    public void createRecipeTest()throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("name");
        newEntity.setDescription("description");
        
        RecipeEntity result = recipeLogic.createRecipe(newEntity);
        Assert.assertNotNull(result);
        
        RecipeEntity entity = em.find(RecipeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
        
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeWithSameNameTest()throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName(data.get(0).getName());
        newEntity.setDescription("description");
        
        recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeWithEmptyNameTest()throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("");
        newEntity.setDescription("description");
        recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeWithNameGreaterThan30CharsTest()throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("esta cadena contiene mas de treinta caracteres");
        newEntity.setDescription("description");
        recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeWithEmptyDescriptionTest()throws BusinessLogicException
    {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("name");
        newEntity.setDescription("");
        recipeLogic.createRecipe(newEntity);
    }
    
}
