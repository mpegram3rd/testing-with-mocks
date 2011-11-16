package com.ctv.dao;

import java.util.List;

import com.ctv.exceptions.PersistenceException;

public interface GenericDao<T> {

	public List<T> findAll();
	public int add(T model) throws PersistenceException;
	public void save(T model) throws PersistenceException;
	public void remove(T model) throws PersistenceException;
	
}
