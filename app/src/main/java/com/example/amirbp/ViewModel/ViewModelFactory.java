package com.example.amirbp.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, ViewModel> viewModels;

    public ViewModelFactory(Map<Class<? extends ViewModel>, ViewModel> viewModels) {
        this.viewModels = viewModels;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (viewModels.containsKey(modelClass)) {
            return (T) viewModels.get(modelClass);
        }
        throw new IllegalArgumentException("Unknown model class " + modelClass);
    }


    public static class Builder {
        private final Map<Class<? extends ViewModel>, ViewModel> viewModels = new HashMap<>();

        public <T extends ViewModel> Builder add(@NonNull Class<T> viewModelClass, @NonNull T viewModel) {
            viewModels.put(viewModelClass, viewModel);
            return this;
        }

        public ViewModelFactory build() {
            return new ViewModelFactory(viewModels);
        }
    }
}
