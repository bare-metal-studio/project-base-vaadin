package com.lyshnia.jps;

import com.lyshnia.jps.auth.AccessControl;
import com.lyshnia.jps.auth.AccessControlFactory;
import com.lyshnia.jps.auth.BasicAccessControl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "Logout", layout = MainView.class)
public class Logout extends VerticalLayout {
    public static final String VIEW_NAME = "Logout";
    AccessControl accessControl = new BasicAccessControl();

    public Logout() {
        AccessControlFactory
                .getInstance().createAccessControl().signOut();

        navigateTo(HomeView.VIEW_NAME);

    }

    private void navigateTo(String viewName) {
        getUI().ifPresent(ui ->
                ui.navigate(viewName));
    }
}
