package com.ef.model;

/**
 * Entity to store each IP address found in access.log file
 */
public class Ip {

    private Integer id; // PK
    private String ipAddress;

    public Ip(Integer id, String ipAddress) {
        this.id = id;
        this.ipAddress = ipAddress;
    }

    public Integer getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

}
