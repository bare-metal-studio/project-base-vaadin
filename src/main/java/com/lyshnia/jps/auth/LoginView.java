package com.lyshnia.jps.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("JPS | Login")
public class LoginView extends FlexLayout {
    private AccessControl accessControl;

    public LoginView() {
        setSizeFull();
        accessControl = AccessControlFactory.getInstance().createAccessControl();

        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(
                event -> Notification.show("Hint: same as username"));

        Button loginButton = new Button("Sign Up");
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        loginButton.addClickListener(e -> {
            getUI().get().navigate(RegisterView.class);
        });

        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);

        centeringLayout.add(loginButton);
        centeringLayout.add(new Span("or"));
        centeringLayout.add(loginForm);

        Image image = new Image("icons/logo.png", "logo");
        image.setWidth("150px");
        image.setHeight("150px");


        centeringLayout.getStyle().set("flex-direction", "column-reverse");
        getStyle().set("flex-direction", "column");
        centeringLayout.add(image);
        add(centeringLayout);
    }

    private void login(LoginForm.LoginEvent event) {
        if (accessControl.signIn(event.getUsername(), event.getPassword())) {
            getUI().get().navigate("");
        } else {
            event.getSource().setError(true);
        }


    }
}
