package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.models.Store;

import java.util.Optional;

public interface StoreService {
    public Optional<Store> getStore(Long id);
}
