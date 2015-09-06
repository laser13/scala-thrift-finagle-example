[![Build Status](https://travis-ci.org/laser13/scala-thrift-finagle-example.svg)](https://travis-ci.org/laser13/scala-thrift-finagle-example)

# scala-thrift-finagle-example

A sample finagle thrift application using for generation code [scrooge-sbt-plugin](http://twitter.github.io/scrooge/SBTPlugin.html)

###It uses:

* [Apache Thrift](https://thrift.apache.org/) 
* [Scalatest](http://www.scalatest.org/) for integration tests
* [Scaldi](http://scaldi.org/) for dependency injection
* [Scrooge](http://twitter.github.io/scrooge/index.html) for generation of finagle service code

### Run tests
    > sbt test