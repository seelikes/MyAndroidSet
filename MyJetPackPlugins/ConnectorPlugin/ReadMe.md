# ConnectorPlugin
Connector try to build direct connection between interface or superclass to it's implementation or subclass at build time  
the first version we use reflect to build new instance, we are trying to generator direct initializer into our ConnectorApi.class  

## usage
please feel at home when using this plugin at any purposes, it's under MIT licence.  

1. add maven repository to your root build.gradle
    ```
            maven {
                url 'http://www.seelikes.com:34272/repository/maven-public'
            }
    ```
2. add classpath to your root build.gradle
    ```
            classpath 'com.github.seelikes.android.plugin:connector:1.0.01'
    ```
3. apply plugin after 'com.android.application' or maybe 'com.android.library'
    ```
            apply plugin: 'connector'
    ```
4. create connector config after android config
    ```
            connector {
                packageNames "com.github.seelikes.android.plugin.connector"
            }
    ```
5. create any interface or superclass below any module, and compile it into modules using the interface.
6. create an impl module for the interface or superclass, and compile it into where the plugin applied
7. use the interface with ConnectorApi to call it's implementation
    ```
            ConnectorApi.get(OneApi.class).showOne(this);
    ```
## attention
the subclass or interface implementation must have a public constructor, or public static getInstance() method or public static Instance  
remember, we do not check the method's name or instance's name, just makesure it can be cast to the interface or super class you pointed.