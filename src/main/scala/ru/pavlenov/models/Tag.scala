package ru.pavlenov.models

import ru.pavlenov.thrift.{Tag => ThriftTag}

import scala.language.implicitConversions

/**
 * ⓭ + 18
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
case class Tag(id: Int, name: String)

object Tag {

  implicit def thrift2tag(thriftTag: ThriftTag): Tag = Tag(thriftTag.id, thriftTag.name)

  implicit def tag2thrift(tag: Tag): ThriftTag = ThriftTag(id = tag.id, name = tag.name)

}