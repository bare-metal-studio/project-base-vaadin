package com.lyshnia.jps.auth;

import com.lyshnia.jps.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import retrofit2.Response;

import java.util.HashMap;

@PageTitle("JPS | Sign Up")
@Route("register")
public class RegisterView extends FlexLayout {
    public RegisterView() {
        setSizeFull();

        Image image = new Image("icons/logo.png", "logo");
        image.setWidth("150px");
        image.setHeight("150px");

        FormLayout columnLayout = new FormLayout();
        columnLayout.setSizeUndefined();

        TextField names = new TextField("Full Name");
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        Button aContinue = new Button("Continue");
        aContinue.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        loginButton.addClickListener(e -> {
            getUI().get().navigate(LoginView.class);
        });

        names.setRequired(true);
//        email.setRequired(true);
        password.setRequired(true);


        // Error Message
        Div error = new Div();
        H5 errorH = new H5("An error occurred");
        Paragraph errorP = new Paragraph("Seems your email is already registered with us.");
        error.getElement().setAttribute("part", "error-message");
        errorH.getElement().setAttribute("part", "error-message-title");
        errorP.getElement().setAttribute("part", "error-message-description");
        error.add(errorH, errorP);
        error.getStyle().set("background-color", "var(--lumo-error-color-10pct)");
        error.getStyle().set("color", "var(--lumo-error-text-color)");
        errorH.getStyle().set("color", "var(--lumo-error-text-color)");
        error.getStyle().set("border-radius", "var(--lumo-border-radius)");
        error.getStyle().set("padding", "var(--lumo-space-m)");
        error.getStyle().set("padding-left", "var(--lumo-space-m)");
        error.getStyle().set("flex-shrink", "1");
        error.setVisible(false);


        // Success Message
        Div success = new Div();
        H5 successH = new H5("Registration Successful!");
        Paragraph successP = new Paragraph("You can now login using your username.");
        success.add(successH, successP);
        success.getStyle().set("background-color", "var(--lumo-success-color-10pct)");
        success.getStyle().set("color", "var(--lumo-success-text-color)");
        successH.getStyle().set("color", "var(--lumo-success-text-color)");
        success.getStyle().set("border-radius", "var(--lumo-border-radius)");
        success.getStyle().set("padding", "var(--lumo-space-m)");
        success.getStyle().set("padding-left", "var(--lumo-space-m)");
        success.getStyle().set("flex-shrink", "1");
        success.setVisible(false);

        columnLayout.add(success, error, names, email, password);
        columnLayout.setColspan(names, 1);
        columnLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0em", 1));
        columnLayout.getStyle().set("width", "fit-content");


        HorizontalLayout hl = new HorizontalLayout(new RouterLink("Login", LoginView.class));
        hl.setSpacing(true);
        hl.setPadding(true);

        VerticalLayout formHolder = new VerticalLayout();
        formHolder.setMaxWidth("350px");
        formHolder.setPadding(true);
        formHolder.setMargin(true);
        formHolder.add(columnLayout);


        setAlignItems(Alignment.CENTER);

        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(loginButton);
        centeringLayout.add(new Span("or"));
        centeringLayout.add(aContinue);
        centeringLayout.add(formHolder);

        centeringLayout.getStyle().set("flex-direction", "column-reverse");
        getStyle().set("flex-direction", "column");
        centeringLayout.add(image);
        add(centeringLayout);

        aContinue.addClickListener(event -> {
            if (names.getValue().isEmpty() || email.getValue().isEmpty() || password.getValue().isEmpty()) {
                Notification.show("All fields are required", 3000, Notification.Position.MIDDLE);
                return;
            }

            HashMap<String, String> userinfo = new HashMap<>();
            userinfo.put("names", names.getValue());
            userinfo.put("username", email.getValue());
            userinfo.put("password", password.getValue());

            Login service = MainView.retrofit.create(Login.class);

            try {
                System.out.println(userinfo);
                Response<HashMap<String, Object>> response = service.register(userinfo).execute();

                System.out.println(response.code());
                System.out.println(response.raw());
                if (response.isSuccessful()) {
                    error.setVisible(false);
                    success.setVisible(true);
                } else {
                    success.setVisible(false);
                    error.setVisible(true);
                }
                System.out.println(response.body());
            } catch (Exception e) {
                System.out.println(e);

                return;
            }
        });
    }
}
