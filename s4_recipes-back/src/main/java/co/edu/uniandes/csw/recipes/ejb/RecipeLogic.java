/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.ejb;

import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author CesarF
 */
@Stateless
public class RecipeLogic {
    
     private static final Logger LOGGER = Logger.getLogger(RecipeLogic.class.getName());

    @Inject
    private RecipePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    public RecipeEntity getRecipe(Long id) {
        return persistence.find(id);
    }

    //TODO crear el método createRecipe
    public RecipeEntity createRecipe(RecipeEntity entity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de una receta");
        if(entity.getName() == null|| entity.getName().isEmpty() || entity.getName().length() <= 30)
        {
            throw new BusinessLogicException("El nombre de la receta no es valido");
        }
        
        if(persistence.findByName(entity.getName()) != null)
        {
            throw new BusinessLogicException("Ya existe una receta con dicho nombre");
        }
        
        if(entity.getName() == null|| entity.getName().isEmpty() || entity.getName().length() <= 150)
        {
            throw new BusinessLogicException("La descripcion de la receta no es valida");
        }
        
        return persistence.createRecipe(entity);
    }

}
