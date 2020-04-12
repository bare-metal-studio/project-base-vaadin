package com.lyshnia.jps.auth;

import com.lyshnia.jps.MainView;
import com.lyshnia.jps.user.User;
import com.lyshnia.jps.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import retrofit2.Response;

import java.util.HashMap;

public class BasicAccessControl implements AccessControl {
    @Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty())
            return false;

        Login service = MainView.retrofit.create(Login.class);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("username", username);
        data.put("password", password);
        try {
            Response<LoginDetails> loginDetails = service.login(data).execute();

            if (loginDetails.isSuccessful()) {
                if (!loginDetails.body().getRoles().contains("ROLE_USER")) {
                    throw new Exception("User is Admin");
                }
                // Get user details
                UserService userService = MainView.retrofit.create(UserService.class);

                Response<User> userResponse = userService.getDetails(loginDetails.body().getToken_type()
                        + " " + loginDetails.body().getAccess_token(), username).execute();

                System.out.println(userResponse.raw());

                System.out.println(userResponse.body());

                CurrentUser.set(loginDetails.body(), userResponse.body());
            }
            System.out.println(loginDetails.body().toString());
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean isUserSignedIn() {
        return !(CurrentUser.get() == null);
    }

    @Override
    public boolean isUserInRole(String role) {
       /* if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }*/

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get().getUsername();
    }

    @Override
    public User getUserInfo() {
        return CurrentUser.get();
    }

    @Override
    public LoginDetails getLogin() {
        return CurrentUser.getLogin();
    }

    @Override
    public void signOut() {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().navigate("");
    }
}
