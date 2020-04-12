package com.lyshnia.jps;

import com.lyshnia.jps.auth.AccessControl;
import com.lyshnia.jps.auth.BasicAccessControl;
import com.lyshnia.jps.navigation.drawer.NaviDrawer;
import com.lyshnia.jps.navigation.drawer.NaviItem;
import com.lyshnia.jps.navigation.drawer.NaviMenu;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The main view contains a button and a click listener.
 */
@Theme(value = Lumo.class)
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PageTitle("JPS Web")
@PWA(name = "JPS Web", shortName = "JPS", iconPath = "icons/logo.png")
@CssImport("./styles/shared-styles.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends AppLayout implements RouterLayout {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8077/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    AccessControl accessControl = new BasicAccessControl();
    private NaviDrawer naviDrawer;
    private NaviMenu menu;

    public MainView() {
        Image img = new Image("images/logo-flat.png", "Flat Logo");
        img.setHeight("44px");
        addToNavbar(new DrawerToggle(), img);
        naviDrawer = new NaviDrawer();
        menu = naviDrawer.getMenu();

        menu.addNaviItem(VaadinIcon.HOME, "Home", HomeView.class);
        NaviItem productManagement = menu.addNaviItem(VaadinIcon.LOCATION_ARROW, "Address Management",
                null);
        /*menu.addNaviItem(productManagement, "Manage Address", AddressView.class);
        menu.addNaviItem(productManagement, "Add Address", NewAddressView.class);

        menu.addNaviItem(VaadinIcon.CASH, "Pricing Table", PricingView.class);

        menu.addNaviItem(VaadinIcon.INFO_CIRCLE, "About", AboutView.class);
*/

        menu.addNaviItem(VaadinIcon.EXIT, "Logout", Logout.class);

        addToDrawer(menu);
    }
}
