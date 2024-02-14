package app.logisctics.cache;

import app.logisctics.dao.model.Destination;
import app.logisctics.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
@RequiredArgsConstructor
public class DestinationCache {

    private final DestinationRepository destinationRepository;

    private Map<Long, Destination> destinationCache = new HashMap<>();

    private void initCache(){
         this.destinationCache = destinationRepository.findAll().stream()
                 .collect(toMap(Destination::getId, Function.identity()));
    }

    private Map<Long, Destination> getCacheData(){
        if(this.destinationCache.isEmpty()){
            initCache();
        }
        return this.destinationCache;
    }

    public List<Destination> findAll(){
        return getCacheData().values().stream().toList();
    }

    public Optional<Destination> findById(Long id){
        return Optional.ofNullable(getCacheData().get(id));
    }
    public Optional<Destination> findByName(String name){
        return getCacheData().values().stream()
                .filter(destination-> destination.getName().equals(name))
                .findAny();
    }

    public void save(Destination destination){
        getCacheData().put(destination.getId(), destination);
    }
    public void delete(Long id){
        getCacheData().remove(id);
    }

}
