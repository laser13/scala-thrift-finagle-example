package ru.pavlenov

import com.twitter.util.Future
import ru.pavlenov.models.{Relation, Row, Tag}
import ru.pavlenov.storage.{RelationStorage, RowStorage, TagStorage}
import ru.pavlenov.thrift.{InvalidOperation, TagService, Row => ThriftRow, Tag => ThriftTag}
import scaldi.{Injectable, Injector}

import scala.collection.Set

class MicroService(implicit inj: Injector)
  extends TagService[Future] with Injectable{

  val rowStorage = inject [RowStorage]
  val tagStorage = inject [TagStorage]
  val relStorage = inject [RelationStorage]

  override def ping(): Future[Unit] = Future.Unit

  /**
   * Add tag to row
   * @param rowId identification row
   * @param tagId identification tag
   * @return
   */
  override def addTag(rowId: Int, tagId: Int): Future[Boolean] = {

    val relOpt = for {
      row <- rowStorage.find(rowId)
      tag <- tagStorage.find(tagId)
    } yield Relation(rowId = row.id, tagId = tag.id)

    relOpt match {
      case Some(rel) =>
        Future.value(relStorage.add(rel))
      case _ =>
        Future.exception(new InvalidOperation(s"Tag with id = $tagId or Row with id = $rowId not found"))
    }
  }

  /**
   * Remove tag from row
   * @param rowId identification row
   * @param tagId identification tag
   * @return
   */
  override def removeTag(rowId: Int, tagId: Int): Future[Boolean] = Future.value {
    relStorage.remove(Relation(rowId = rowId, tagId = tagId))
    true
  }

  /**
   * Finds all the rows that have at least one of these tags
   * @param tagIds identifications tags
   * @return
   */
  override def filterRowsByTags(tagIds: Set[Int]): Future[Set[ThriftRow]] = Future.value {

    val rowIds = relStorage.data
      .filter(rel => tagIds.contains(rel.tagId))
      .map(_.rowId)

    rowStorage.data
      .filter(row => rowIds.contains(row.id))
      .map(Row.row2thrift)

  }

  /**
   * Finds all tags belonging to the row
   * @param rowId identification row
   * @return
   */
  override def filterTagsByRow(rowId: Int): Future[Set[ThriftTag]] = Future.value {

    val tagIds = relStorage.data
      .filter(_.rowId == rowId)
      .map(_.tagId)

    tagStorage.data
      .filter(tag => tagIds.contains(tag.id))
      .map(Tag.tag2thrift)

  }

}
