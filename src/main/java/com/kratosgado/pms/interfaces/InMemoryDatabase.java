
package com.kratosgado.pms.interfaces;

import java.util.List;

import com.kratosgado.pms.utils.exceptions.ConflictException;
import com.kratosgado.pms.utils.exceptions.EntityNotFoundException;

public interface InMemoryDatabase<T> {

  /**
   * Adds the given entity to the database
   * 
   * @param model
   * @return the entity added to the database
   */
  public T add(T model) throws ConflictException;

  public T update(T model) throws EntityNotFoundException;

  /**
   * @return List of all entities in the database
   */
  public List<T> getAll();

  /**
   * Returns the entity with the given id
   * 
   * @param id
   * @return Optional containing the entity with the given id
   * @throws RuntimeException
   */
  public T getById(String id) throws EntityNotFoundException;

  /**
   * Removes the entity with the given id from the database
   * 
   * @param id
   * @return true if the entity was removed, false otherwise
   */
  public void removeById(String id) throws EntityNotFoundException;

  /**
   * Checks if the entity with the given id exists in the database
   * 
   * @param id
   * @return true if the entity exists, false otherwise
   */
  public boolean exists(String id) throws EntityNotFoundException;

  /**
   * @return number of entities in the database
   */
  public int count();

}
