/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.entities;

/**
 *
 * @author Angel Rodriguez aa.rodriguezv
 */
public class IngredientEntity {
    private Long id;
    
    private String name;
    
    private Long calories;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the calories
     */
    public Long getCalories() {
        return calories;
    }

    /**
     * @param calories the calories to set
     */
    public void setCalories(Long calories) {
        this.calories = calories;
    }
    
}
