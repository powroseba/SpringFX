#SpringFX library

####To use SpringFX in your Spring-boot application you need to:</b>

>#####1. Execute `mvn clean install` to install library in your .m2 repository or copy created jar to your project
>
>---
>#####2. Add generated jar to your project or import it by maven
>*maven dependency code*
>```xml
><dependency>
>    <groupId>com.springfx</groupId>
>    <artifactId>spring-fx-application</artifactId>
>    <version>1.0-SNAPSHOT</version>
></dependency>
>```
> ---
>#####3. Create main class
> Main class should be annotated by `@SpringBootApplication` annotation and application should be started by code
>```
>SpringFXRunner.run([1], [2], [3]);
>```
> 
>[1]. This argument is the main class of application which is also annotated by `@SpringBootApplication`. It is required to 
>tell `SpringFXRunner` which class is configuration class for spring-boot application.
> 
>[2]. This argument is implementation of `FXScene` class. `FXScene` is class which need to provide some data about scenes in SpringFX application.
>These data must specify:
>
>* Title
> <br> *This property is used to display title of defined scene,*
>* FxmlFilePath
> <br> *This property define path to fxml file of scene,* 
>* IsAlwaysNewScene
> <br> *This property define that scene should be always shown as new window,* 
>* isResizable
> <br> *This property define scene as resizable.*
>
> *Optional properties are maxWidth, minWidth, maxHeight, minHeight*
>
> #####For comfortable implementation of scenes i recommend enum type with 2 constructors. Example implementation with help or lombok can look like that:
>
>```java
>@AllArgsConstructor
>@RequiredArgsConstructor
>@Getter
>public enum Scene implements FXScene {
>
>    MAIN("Main title", "/main.fxml", false, true, 500, 500, 400, 400),
>    SECOND("Second title", "/second.fxml", true, false);
>
>    private final String title;
>    private final String fxmlFilePath;
>    private final boolean isAlwaysNewScene;
>    private final boolean isResizable;
>    private int maxHeight;
>    private int maxWidth;
>    private int minHeight;
>    private int minWidth;
>
>}
>```
>
>[3] This argument is simple array of arguments which is passed to every main method. This array need to be passed again as third parameter of run method in SpringRunner
>
> <b>Example running of SpringFXRunner can look like that</b>
>```java
>@SpringBootApplication
>public class DemoApplication {
>
>    public static void main(String[] args) throws IOException {
>        SpringFXRunner.run(DemoApplication.class, Scene.MAIN, args);
>    }
>}    
>```
>
> `SpringFXRunner.run()` method can throw IOException in case of problem to find fxml file of scene
>
> ---
>#####4. Controllers of FX scenes
> Every controller which implement `Initializable` interface need to be annotate by `@Component` annotation.
> Otherwise SpringFX wont be ably to find required scene manager for current shown scene
###That is all!
Library should run Spring-boot application with JavaFX context together and make possible creating JavaFX application.

---
#####Additional features:
1. SpringFX is supporting i18n. You can easily create your own ResourceBundle bean and use it in your fxml files.
2. Functionality of switching scenes is already implemented! <br>
   *SpringFX is defining StageManager bean which offer method to switch current scene to another passed as parameter or show this scene as new scene.
   To use this implementation you can inject to spring bean `StageManager` bean but only in `@Lazy` strategy. 
   It is because that bean is define in spring context after spring context starts.*
3. Your FX Controllers can automatically offer you stage manager methods <br>
   *You can extends `FXController` class in your every class which will manage components of FX scenes. Thanks this you will get access to `switchScene()` and `showNewScene()` methods.
   I created this class because controllers are possibly the most often used class to switch or show new scene*
    