package com.isik.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author @fisik
 */
@ConfigurationProperties(prefix = "com.isik")
public class AppProperties {

    private int     sourceRabbitHttpPort;

    private String remoteRabbitIp;

    private int     remoteRabbitHttpPort;

    private String  remoteRabbitUserName;

    private String  remoteRabbitPassword;

    private int     remoteRabbitPort;

    public AppProperties() {

    }

    public int getSourceRabbitHttpPort() {
        return sourceRabbitHttpPort;
    }

    public void setSourceRabbitHttpPort(int sourceRabbitHttpPort) {
        this.sourceRabbitHttpPort = sourceRabbitHttpPort;
    }

    public String getRemoteRabbitIp() {
        return remoteRabbitIp;
    }

    public void setRemoteRabbitIp(String remoteRabbitIp) {
        this.remoteRabbitIp = remoteRabbitIp;
    }

    public int getRemoteRabbitHttpPort() {
        return remoteRabbitHttpPort;
    }

    public void setRemoteRabbitHttpPort(int remoteRabbitHttpPort) {
        this.remoteRabbitHttpPort = remoteRabbitHttpPort;
    }

    public String getRemoteRabbitUserName() {
        return remoteRabbitUserName;
    }

    public void setRemoteRabbitUserName(String remoteRabbitUserName) {
        this.remoteRabbitUserName = remoteRabbitUserName;
    }

    public String getRemoteRabbitPassword() {
        return remoteRabbitPassword;
    }

    public void setRemoteRabbitPassword(String remoteRabbitPassword) {
        this.remoteRabbitPassword = remoteRabbitPassword;
    }

    public int getRemoteRabbitPort() {
        return remoteRabbitPort;
    }

    public void setRemoteRabbitPort(int remoteRabbitPort) {
        this.remoteRabbitPort = remoteRabbitPort;
    }

}
