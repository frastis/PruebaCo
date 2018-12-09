package com.lombatto.chelmo.pruebaco;

public class Owner {

    String login;
    long id;
    String node_id;
    String avatar_url;
    String html_url;
    String followers_url;
    String repos_url;

    public Owner(String login, long id, String node_id, String avatar_url, String html_url, String followers_url, String repos_url) {
        this.login = login;
        this.id = id;
        this.node_id = node_id;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
        this.followers_url = followers_url;
        this.repos_url = repos_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }
}
