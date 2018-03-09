package io.github.golok56.manajemenuang.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Satria Adi Putra
 */
@Entity
public class Category {

    @PrimaryKey
    private long id;

    private String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
