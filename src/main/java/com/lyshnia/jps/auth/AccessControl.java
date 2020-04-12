package com.lyshnia.jps.auth;

import com.lyshnia.jps.user.User;

public interface AccessControl {
    boolean signIn(String username, String password);

    boolean isUserSignedIn();

    boolean isUserInRole(String role);

    String getPrincipalName();

    User getUserInfo();

    LoginDetails getLogin();

    void signOut();
}
