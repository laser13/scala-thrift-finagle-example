package ru.pavlenov.models

import ru.pavlenov.thrift.{Row => ThriftRow}

import scala.language.implicitConversions

/**
 * ⓭ + 18
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
case class Row(id: Int, name: String)

object Row {

  implicit def thrift2row(thriftRow: ThriftRow): Row = Row(id = thriftRow.id, name = thriftRow.name)

  implicit def row2thrift(row: Row): ThriftRow = ThriftRow(id = row.id, name = row.name)

}