package ru.smole.binding.bind;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String id;
    private List<Bind> binds;

    public Category(String id) {
        this.id = id;
        binds = new ArrayList<>();
    }

    public Category add(String id, int keyCode, Runnable click) {
        binds.add(new Bind(id, keyCode, this.id, click));
        return this;
    }

    public List<Bind> getBinds() {
        return binds;
    }

    public String getId() {
        return id;
    }
}
