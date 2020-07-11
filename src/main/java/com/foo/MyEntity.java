package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Version;

@Entity
public class MyEntity {
    @Id
    private String id;
    @Version
    private Long version;
    private String description;

// FIXME - Uncomment this block and the test will pass
//    @PrePersist
//    private void onSave() {
//        version = version == null ? 1l : version;
//    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }
}
