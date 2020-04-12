package com.lyshnia.jps;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class Menu extends FlexLayout {

    private static final String SHOW_TABS = "show-tabs";
    private Tabs tabs;

    public Menu() {

        // container for the navigation buttons, which are added by addView()
        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        setFlexGrow(1, tabs);
        add(tabs);


    }

    /**
     * Add a view to the navigation menu
     *
     * @param viewClass that has a {@code Route} annotation
     * @param caption   view caption in the menu
     */
    public void addView(Class<? extends Component> viewClass, String caption) {
        Tab tab = new Tab();
        RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        Span span = new Span(caption);
//        span.getStyle().set("color", "black");
        routerLink.add(span);
        tab.add(routerLink);
        tabs.add(tab);
    }
}