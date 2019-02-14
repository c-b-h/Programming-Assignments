# Tattoodo
Programming assignment for Tattoodo

## Architecture
The codebase honors the MVVM-pattern recommended by Google with the use of Android's Architecture Components. **VIEW**s observe **VIEWMODEL**s through `LiveData`. This pattern creates a clear separation of concerns and allows **VIEWMODEL**s to be easily unit tested (not done in the first release). **VIEWMODEL**s communicate with so called *repositories* for obtaining **MODEL**s from a back-end and/or a cached source. Repositories could be shared among several **VIEWMODEL**s.
