package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.models.Store;
import bookcarupdate.bookcar.repositories.StoreRepository;
import bookcarupdate.bookcar.services.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    @Override
    public Optional<Store> getStore(Long id) {

        return storeRepository.findById(id);
    }
}
