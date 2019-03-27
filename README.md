# My Java Archetype

Example of 'My Java Archetype' as described on my Blog 'Florian Kempenich'.

> To know more about what this Archetype is about, check out my article:
>
> [My Java Archetype - Florian Kempenich](https://floriankempenich.com/post/11)

## A working implementation to explore

The goal of this implementation is to showcase the Archetype described in [my article](https://floriankempenich.com/post/11).
Feel free to look into the code and see how I implemented each layer / module.

### Quick start
**3 scripts** are available at the root of the project to allow you to get the application un and running in one click.   
> To run these scripts: **Docker must be installed**   
> _Docker_ is used to pop a temporary _Postgres_ container on port `1234`. 
> _Postgres_ is required to run the integration tests as well as when running the app with database. 
> If you do not wish to use _Docker_, you can also use your own instance of _Postgres_ and update the configuration in [application.yml](/src/main/resources/application.yml)

* #### `./run_tests.sh`
  Build the application and run all the tests
* #### `./start_app_with_inmemory.sh`
  Build and start the application. Application will be using in-memory storage.
* #### `./start_app_with_inmemory.sh`
  Build and start the application. Application will be using database storage (Postgres).


---

As always if you have a suggestion, comment, or doubt don't hesitate to post a comment on [the related article](https://floriankempenich.com/post/11).
