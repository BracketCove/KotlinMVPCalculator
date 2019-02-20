## MVP Kotlin Calculator (Coroutine Branch)

The primary goal of this project was to help me learn Kotlin, and to create a better implementation
of MVP than I have in previous years, which includes a ViewModel object (not to be confused with MVVM). 

### F.A.Q.
**If this is MVP, what's with the ViewModel?**

In previous implementations, I would maintain temporary UI state either within my Fragment (View) or my Presenter (Presenter). This is problematic, as I don't think the View, nor the Presenter, should have anything to do with storing state. This necessitated another object in my Presentation Layer which could store view state without being tightly coupled to anything.

I achieve this by having my Presenter delegate temporary state to the ViewModel.

1. Most people I see who make a "ViewModel" class, are actually making a ViewModel/ViewController. IMHO, if it stores the view state, ViewModel is a good name. If it also coordinates the view state, ViewModel is a **fundamentally misleading** name which has caused much confusion for people (including me). 

2. You'll see it extends AAC's ViewModel class. For me to call it something different might also confuse the issue even further.

### Architecture:
- Presenter: Presenter is the "Logic" center of the Application. It is the thing which you must be most certain to test, as it essentially coordinates "the dance" (à la Uncle Bob), of the View and Model. It should not be full of libs (RxJava is the exception as I do need some kind of concurrency tool), and most certainly shouldn't have references to Android Framework Classes.
- View: The View binds data to XML, and forwards click events to the Presenter. With little exception, it shouldn't contain complex logic (complexity necessitates testing, I want my View to be relatively unnecessary to test), and essentially does what it is told.  
- Model: Models 'Model' (as in the verb) the information relevant to the Application. This can be long-term (like a Database), or short-term (like a ViewModel, and even a POJO/Data Class). Like the View, the Model shouldn't contain much logic (in most cases anyway). In this App, it helps us maintain and decouple the UI State, which keeps it safe from being destroyed by orientation changes, thus leading to poor UX.
- UseCase: To avoid our Presenter becoming too complex, and to reduce it's coupling to the backend (Validator and Calculator), the Presenter talks to a Use Case (or Interactor). This one is hard to explain, so try to just look at the code and see how it might help. Notice how we just call on object (UseCase) which handles both Validaiton and Calculation, thus reducing the complexity of the Presenter (like if it spoke directly to both Classes).
- Repository: In order to decouple the Use Case from specific backend implementations, it talks to them via Interfaces (which can be thought of as the Repository/Facade Pattern). This means that we could swap out Validator or Calculator for something entirely different, and we would not need to change the rest of the Application one bit!
- Validator: Just performs some basic validation to see of an expression is...valid. At first this was part of the Calculator, but it became complicated enough to warrant separating it to a separate class which runs before Calculator. After all, if it isn't valid, we don't need to calculate anything.
- Calculator: 'nuff said. I wrote it myself using TDD, but both it and the Validator could potentially be improved. 
- Activity: Container for my MVP Components.
- Application: LeakCanary.
- Injector: A simple home baked Dependency Injection class, because everyone keeps s**ting themselves whenever I use D2 in my demos/tutorials (seriously, it's like there's a wierd "I hate D2 because I don't understand it" movement on the web).

## Sources and Inspiration
Awesome, Kotlin clean MVP open source Weather/News App by @darelbitsy:
https://github.com/bitsydarel/DBWeather

Awesome collection of projects made by pro devs and googlers:
https://github.com/googlesamples/android-architecture

Same as above, with emphasis on AAC things like ViewModel:
https://github.com/googlesamples/android-architecture-components

Martin Fowler (see his blog article called "Passive View"):
https://martinfowler.com/

