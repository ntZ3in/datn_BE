package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.models.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    public Optional<Store> getStore(Long id);
    public List<Store> findAll();
    public Store getStoreByOwnerEmail(String email);
}
