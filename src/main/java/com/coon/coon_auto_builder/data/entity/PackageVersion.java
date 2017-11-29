package com.coon.coon_auto_builder.data.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "package_versions")
@Configurable(autowire = Autowire.BY_TYPE)
public class PackageVersion {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "version_id")
    private String versionId;

    @Column(name = "ref", length = 100, nullable = false)
    private String ref;

    @Column(name = "erl_version", length = 10, nullable = false)
    private String erlVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repository_url")
    private Repository repository;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "packageVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Build> buildsRes = new ArrayList<>();

    @Transient
    private String email; //email of last commit for this ref.

    public PackageVersion() {
    }

    public PackageVersion(String ref, String erlVersion) {
        this.ref = ref;
        this.erlVersion = erlVersion;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getRef() {
        return ref;
    }

    public String getErlVersion() {
        return erlVersion;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public List<Build> getBuildsRes() {
        return buildsRes;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addBuild(Build build) {
        build.setPackageVersion(this);
        buildsRes.add(build);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageVersion version = (PackageVersion) o;

        return versionId != null ? versionId.equals(version.versionId) : version.versionId == null;
    }

    @Override
    public int hashCode() {
        return versionId != null ? versionId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PackageVersion{" +
                "id='" + versionId + '\'' +
                ", ref='" + ref + '\'' +
                ", erlVersion='" + erlVersion + '\'' +
                '}';
    }
}
