package com.mb;

import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ApplicationScoped
@ManagedBean
public class ThemeBean {

    public String getApplicationTheme() {
        return "pepper-grinder";
    }

}