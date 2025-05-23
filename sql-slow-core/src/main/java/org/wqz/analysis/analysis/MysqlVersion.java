package org.wqz.analysis.analysis;


public enum MysqlVersion {

    MYSQL_5_6("MYSQL_5.6"),
    MYSQL_5_7( "MYSQL_5.7");


    MysqlVersion(String version) {
        this.version = version;
    }

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
