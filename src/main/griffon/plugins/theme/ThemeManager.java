/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.plugins.theme;

import griffon.core.ApplicationHandler;
import griffon.core.GriffonApplication;
import org.codehaus.griffon.runtime.core.AbstractVetoable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import java.beans.PropertyVetoException;

/**
 * @author Andres Almiray
 */
public class ThemeManager extends AbstractVetoable implements ApplicationHandler {
    public static final String CURRENT_THEME_PROP = "currentTheme";
    private final GriffonApplication app;
    private final List<String> themes = new ArrayList<String>();
    private String currentTheme;

    public ThemeManager(GriffonApplication app, Collection<String> availableThemes) {
        this.app = app;
        this.themes.addAll(availableThemes);
    }

    public GriffonApplication getApp() {
        return app;
    }

    public List<String> getThemes() {
        return Collections.unmodifiableList(themes);
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(String currentTheme) throws PropertyVetoException {
        if (themes.contains(currentTheme)) {
            fireVetoableChange(CURRENT_THEME_PROP, this.currentTheme, currentTheme);
            firePropertyChange(CURRENT_THEME_PROP, this.currentTheme, this.currentTheme = currentTheme);
        }
    }
}
