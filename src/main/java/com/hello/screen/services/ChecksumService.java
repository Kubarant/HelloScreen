package com.hello.screen.services;

import com.hello.screen.utils.ChecksumUtil;
import org.pmw.tinylog.Logger;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ChecksumService {

    /**
     * @return checksum calculated basing on hashCode()
     */
    public <T> boolean areAlreadyInDb(List<T> newObject, List<T> alreadyStoredObject) {
        int newChecksum = ChecksumUtil.checksumOfHashCodes(newObject);

        int storedChecksum = ChecksumUtil.checksumOfHashCodes(alreadyStoredObject);
        Logger.debug("Is it already in DB?  {}", newChecksum == storedChecksum);
        return newChecksum == storedChecksum;
    }

    public <T> Mono<List<T>> replaceObjectsIfNotAlreadyStored(List<T> products, ReactiveCrudRepository<T, String> objRepository) {
        return objRepository.findAll()
                .collectList()
                .filter(productsList -> !areAlreadyInDb(productsList, products))
                .flatMap(products1 -> objRepository.deleteAll())
                .thenReturn(products)
                .flatMap(products1 -> objRepository.saveAll(products)
                        .collectList());
    }
}
