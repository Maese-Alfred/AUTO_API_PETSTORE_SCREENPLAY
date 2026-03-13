package org.auto.screenplay.models;

import java.util.List;

public class Pet {

    private long id;
    private String name;
    private String status;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;

    public Pet() {
    }

    public Pet(long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.photoUrls = List.of();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}

