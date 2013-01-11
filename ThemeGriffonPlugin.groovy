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

class ThemeGriffonPlugin {
    // the plugin version
    String version = '0.1'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '1.2.0-SNAPSHOT > *'
    // the other plugins this plugin depends on
    Map dependsOn = [:]
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, qt
    List toolkits = []
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-theme-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'Theme injection'
    String description = '''
Enables injection of theme-based resources.

Usage
-----
Themes bay be switched at any time based on two conditions:

 * a valid value for `ThemeManager.currentTheme` is set
 * the application's Locale is updated

The following controller shows 4 actions showing how to trigger each one of
these conditions. The application assumes there are two themes named `red` and
`blue` and that there are locale aware versions of these themes for English and
Spanish

    import griffon.plugins.theme.ThemeManagerHolder
    class SampleController {
        void red(evt)  { ThemeManagerHolder.themeManager.currentTheme = 'red' }
        void blue(evt) { ThemeManagerHolder.themeManager.currentTheme = 'blue' }

        void spanish(evt) { app.localeAsString = 'es' }
        void english(evt) { app.localeAsString = 'en' }
    }

Classes that should participate in theme injection must be anotated with
`@ThemeAware` and have their properties anotated with `@InjectedResource`,
for example

    import groovy.beans.Bindable
    import java.awt.Color
    import griffon.core.resources.InjectedResource

    @griffon.plugins.theme.ThemeAware
    class SampleModel {
        @Bindable @InjectedResource Color color
        @Bindable @InjectedResource String message
    }

The resource injection mechanism relies on application events in order to handle
injections on instances. All griffon artifacts trigger an event upon creation
(`NewInstance`) and destruction (`DestroyInstance`). Non griffon artifact
instances can still participate in resource injection as long as these events
are triggered by your code, for example

    import groovy.beans.Bindable
    import griffon.core.resources.InjectedResource
    @griffon.plugins.theme.ThemeAware
    class Greeter {
        @Bindable @InjectedResource String message
        String greet() { message }
    }

    class MyService {
        private Greeter greeter
        void serviceInit() {
            greeter = new Greeter()
            app.event('NewInstance', [Greeter, '', greeter])
        }
        void serviceDestroy() {
            app.event('DestroyInstance', [Greeter, '', greeter])
        }
        String sayHello() {
            greeter.greet()
        }
    }

Marking bean properties as bindable makes it easier for the application to
update itself when a theme change occurs. For example, a View may use the
`color` and `message` model properties in this way

    application(title: 'Themes',
      preferredSize: [320, 240], pack: true,
      locationByPlatform: true) {
        borderLayout()
        label(text: bind { model.message },
              foreground: bind { model.color },
              constraints: CENTER)
        panel(constraints: WEST) {
            gridLayout(cols: 1, rows: 4)
            button(redAction)
            button(blueAction)
            button(spanishAction)
            button(englishAction)
        }
    }

It's worth noting that if a resource cannot be resolved by a theme then the
default application resources will be searched until the resource can be
resolved or a `NoSuchResourceException` is thrown.

Configuration
-------------
Theme files look exactly the same as resources files, as explained in the
[Resource management][1] chapter of the Griffon Guide. For the example shown
above, the application expects the following files to exist

 * griffon-app/i18n/red.properties
 * griffon-app/i18n/red_es.properties
 * griffon-app/i18n/blue.properties
 * griffon-app/i18n/blue_es.properties

These themes must be registered with the application's configuration, for example
in `Config.groovy`

    themes.basenames = ['red', 'blue']

[1]: http://griffon.codehaus.org/guide/latest/guide/resourceManagement.html
'''
}
