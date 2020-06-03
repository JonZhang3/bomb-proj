package com.bombproj.framework;

import java.io.Serializable;

public class SessionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String KEY_VERIFICATION_CODE = "__ver_code";
    public static final String KEY_SESSION_CONFIG = "__session_config";

    private String userId;
    private String userName;
    private String nickName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
