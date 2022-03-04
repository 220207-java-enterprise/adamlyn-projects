package com.revature.daos;

import java.util.ArrayList;

//Create read update delete
public interface CrudDAO <T> {
    void save(T newObject);
    T getById(String id);
    ArrayList<T> getAll();
    void update(T updatedObject);
    void deleteById(String id);

    // Interfaces can have static methods with a body
    // These methods cannot be overridden (but they can be redeclared [don't use @Override]; effectively shadowing)
    static void staticInterfaceMethod() {

    }

    // Since Java 8, interfaces can contain "default" methods
    // Provides a base functionality that all implementations get, but it can be overridden
    default void defaultInterfaceMethod() {

    }

}

