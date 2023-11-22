package org.example.enums;

import org.example.database.DatabaseManager;
import org.example.user.Property;

import java.util.List;

public enum Building {
    APARTMENT("APARTMENT"),
    HOUSE("HOUSE"),
    VILLA("VILLA"),
    MANSION("MANSION"),
    COTTAGE("COTTAGE");
    private final DatabaseManager db = new DatabaseManager();
    private final String type;

    Building(String type) {
        this.type = type;
    }

    /**
     * @return type of building
     */
    public String getType() {
        return type;
    }

    /**
     * finds all buildings with such type
     *
     * @return list of properties
     */
    public List<Property> findAll() {
        return db.getPropertiesByType(Building.this);
    }

}
