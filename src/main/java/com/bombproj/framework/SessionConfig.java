package com.bombproj.framework;

import com.bombproj.constants.UserType;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String KEY_VERIFICATION_CODE = "__ver_code";
    public static final String KEY_SESSION_CONFIG = "__session_config";

    private String userId;
    private String userName;
    private String nickName;
    private UserType userType;

    public static SessionConfig current() {
        return (SessionConfig) WebUtils.getSessionAttribute(KEY_SESSION_CONFIG);
    }

}
