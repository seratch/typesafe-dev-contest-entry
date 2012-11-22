Typesafe Developer Contest 2012 Entry
=====================================

http://typesafe.com/resources/developer-contest

What’s this
-----------

I was going to create a high performance HTTP API server using Typesafe Stack. But I became more interested in performance measurement. This is a simple benchmark for Typesafe Stack app and others that have the same function. I’m afraid that this entry is not suitable on the theme of the contest, but I believe it doesn’t entirely devoid of interest. Anyway, please have a look.

URLs
----

The application has just 2 URLs as follows.

POST /logs {name: “accesslog”, line: “xxx”}
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Accepting POST requests, saving params into MySQL and finally returning
200 OK.

POST /logs/async {name: “accesslog”, line: “xxx”}
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Accepting POST requests and returning 202 Accepted. Saving params into
MySQL will be executed asynchronously.

Frameworks and app servers
--------------------------

Typesafe Stack (Play 2.0.4 + Akka 2.0.2) + MySQL 5.5.24 + Play server 2.0.4
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    curl -XPOST http://localhost:9000/logs -d'name=access_log&line=xxx'
    curl -XPOST http://localhost:9000/logs/async -d'name=access_log&line=xxx'

JAX-RS (Jersey 1.13 + Spring 3.1.2.RELEASE) + MySQL 5.5.24 + Jetty 6.1.26
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    curl -XPOST http://localhost:8080/logs -d'name=access_log&line=xxx'
    curl -XPOST http://localhost:8080/logs/async -d'name=access_log&line=xxx'

Ruby on Rails 3.2.9 + MySQL 5.5.24 + Passenger 3.0.18
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    curl -XPOST http://localhost:3000/logs -d'name=access_log&line=xxx'
    curl -XPOST http://localhost:3000/logs/async -d'name=access_log&line=xxx'

Express 3.0.3 + MySQL 5.5.24 + Node 0.8.0
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    curl -XPOST http://localhost:8124/logs -d'name=access_log&line=xxx'
    curl -XPOST http://localhost:8124/logs/async -d'name=access_log&line=xxx'

Precondition
------------

Apache Bench
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you’re using Mac OS X, please replace ``ab`` command as follows.

::

    wget http://apache.mirrors.pair.com/httpd/httpd-2.4.3.tar.bz2
    brew install pcre
    tar xzvf httpd-2.4.3.tar.bz2
    cd httpd-2.4.3
    make
    sudo cp -p support/ab /usr/sbin/ab

gnuplot
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    brew install gnuplot

MySQL 5.5.x
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    brew install mysql

::

    create database typesafedevcontest;
    grant all on typesafedevcontest.* to martin@localhost;
    set password for martin@localhost = password('odersky');

Ruby 1.9.3
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

RVM or rbenv

Maven 2.x or 3.x
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    brew install maven

Node.js 0.8.x
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    brew install node

Starting applications
--------------------------

Rails 3.2 on Passenger 3.0
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    cd Rails
    bundle install
    rake db:migrate RAILS_ENV=production
    rake assets:clean assets:precompile
    passenger start -e production -d

JAX-RS on Jetty 6.1
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    cd JAX-RS
    mvn clean jetty:run

Express on Node 0.8
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    cd Express
    npm install
    node server.js

Play 2.0 on Play server
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

::

    cd TypesafeStack
    play start

Running benchmark scripts
-------------------------

::

    ./benchmark_c20.sh

::

    ./benchmark_c50.sh

Results
-------

-  Play 2.0 is much faster than Rails and Express.
-  JAX-RS Jersey is stable and pretty fast.
-  Play 2.0 which uses Akka actors as backend is the fastest of them all.

Concurrency 20
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. figure:: https://raw.github.com/seratch/typesafe-dev-contest-entry/master/gnuplot/c20.png
   :align: center
   :alt: c20

Concurrency 50
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. figure:: https://raw.github.com/seratch/typesafe-dev-contest-entry/master/gnuplot/c50.png
   :align: center
   :alt: c50

License
-------

Apache License, Version 2.0

http://www.apache.org/licenses/LICENSE-2.0.html


