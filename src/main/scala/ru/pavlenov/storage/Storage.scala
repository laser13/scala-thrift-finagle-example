package ru.pavlenov.storage

/**
 * ⓭ + 25
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
trait Storage[T] {

  var data = Set[T]()

  def add(t: T): Boolean = {
    data = data + t
    true
  }

  def remove(t: T): Boolean = {
    data = data - t
    true
  }

}
