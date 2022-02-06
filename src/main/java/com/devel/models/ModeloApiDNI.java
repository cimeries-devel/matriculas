package com.devel.models;

public class ModeloApiDNI {
    private boolean success;
    private DataAPIDNI data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataAPIDNI getDataAPIDNI() {
        return data;
    }

    public void setData(DataAPIDNI data) {
        this.data = data;
    }
}
