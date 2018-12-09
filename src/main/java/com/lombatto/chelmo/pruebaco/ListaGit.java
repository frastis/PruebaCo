package com.lombatto.chelmo.pruebaco;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ListaGit {

    String url;
    String id;
    String node_id;
    String html_url;
    //Files files;
    boolean state_public;
    String created_at;
    String updated_at;
    String description;
    Owner owner;

    public ListaGit(String url, String id, String node_id, String html_url, boolean state_public, String created_at, String updated_at, String description, Owner owner) {
        this.url = url;
        this.id = id;
        this.node_id = node_id;
        this.html_url = html_url;
        this.state_public = state_public;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.description = description;
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public boolean isState_public() {
        return state_public;
    }

    public void setState_public(boolean state_public) {
        this.state_public = state_public;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }




}
