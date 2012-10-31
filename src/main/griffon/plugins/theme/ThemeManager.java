/*
 * Copyright 2012 the original author or authors.
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
import org.codehaus.griffon.runtime.core.AbstractObservable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Andres Almiray
 */
public class ThemeManager extends AbstractObservable implements ApplicationHandler {
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

    public void setCurrentTheme(String currentTheme) {
        if (themes.contains(currentTheme)) {
            firePropertyChange(CURRENT_THEME_PROP, this.currentTheme, this.currentTheme = currentTheme);
        }
    }
}