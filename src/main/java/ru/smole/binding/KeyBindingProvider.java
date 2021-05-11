package ru.smole.binding;

import ru.smole.binding.bind.Category;

public interface KeyBindingProvider {

    Category register(Category category);

    void unregister(String id);

}
