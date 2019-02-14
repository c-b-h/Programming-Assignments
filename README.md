# Tattoodo
Programming assignment for Tattoodo

## Architecture
![MVVM](https://cdn-images-1.medium.com/max/1600/1*8KprSpqqPtSuYObjOFPt2g.png)
The codebase honors the MVVM-pattern recommended by Google with the use of Android's Architecture Components. **VIEW**s observe **VIEW MODEL**s through `LiveData`. This pattern creates a clear separation of concerns and allows **VIEW MODEL**s to be easily unit tested (not done in the first release). **VIEW MODEL**s communicate with so called *repositories* for obtaining **MODEL**s from a back-end and/or a cached source. Repositories could be shared among several **VIEW MODEL**s.

### Data Binding
The Data Binding Library allows the data sources in the app to bind to UI components in the layouts using a declarative format. The bindings are generated on the fly from XML layouts. Besides reducing boilerplate code for calls such as `findViewById`, it also allows for optimized re-layouting and/or redrawing of the UI when several attributes need to be changed simultaneously.  

### Dependency Injection
With the use of Dagger2 and its specialized Android Dagger library the consumer components of utility dependencies to become agnostic to where the dependencies originate further separating the concerns between components. The pattern also allows for seamless swapping of one implementation with another in for instance testing environments.

In order for Dagger2 to correctly build a dependency tree, all dependencies need to be "touched". The `AppModule` includes an `ActivitiesModule` defines a `SubComponent` for each `Activity` in the project. Each of these `SubComponent`s in turn define their own dependents such as `Fragment`s that also want to be included in the tree. This approach allows for future modularization of the codebase into separate feature modules.
