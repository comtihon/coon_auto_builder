package com.coon.coon_auto_builder.jpa.service;

import com.coon.coon_auto_builder.domain.BuildResult;
import com.coon.coon_auto_builder.domain.ErlPackage;
import com.coon.coon_auto_builder.repository.BuildResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component("buildResultServiceInterface")
public class BuildResultServiceInterfaceImpl implements BuildResultServiceInterface {

    @Autowired
    private BuildResultRepository repository;

    @Override
    public BuildResult save(BuildResult pack) {
        return repository.save(pack);
    }

    @Override
    public void delete(String resId) {
        repository.delete(resId);
    }

    @Override
    public Optional<BuildResult> find(String resId) {
        return Optional.ofNullable(repository.findOne(resId));
    }

    @Override
    public List<BuildResult> findByValues(String name, String namespace, String ref, String erlVsn) {
        // TODO
        return null;
    }

    @Override
    public BuildResult getByValues(String name, String namespace, String ref, String erlVsn) {
        // TODO
        return null;
    }

    @Override
    public Collection<BuildResult> getAllBuildResults() {
        Iterable<BuildResult> itr = repository.findAll();
        return (Collection<BuildResult>) itr;
    }
}
