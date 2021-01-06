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

    private static final SessionConfig TESTER = new SessionConfig();
    static {
        TESTER.setUserId("admin");
        TESTER.setUserName("admin");
        TESTER.setNickName("admin");
        TESTER.setUserType(UserType.NORMAL);
    }

    public static SessionConfig current() {
        return TESTER;
//        return (SessionConfig) WebUtils.getSessionAttribute(KEY_SESSION_CONFIG);
    }

}
