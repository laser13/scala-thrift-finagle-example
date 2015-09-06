package storage

import ru.pavlenov.models.Tag
import ru.pavlenov.storage.TagStorage

/**
 * ⓭ + 15
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
object TagStorageTest {

  def apply() = new TagStorage

  def apply(tags: Tag*) = {
    val rs = new TagStorage()
    tags.foreach(rs.add)
    rs
  }

}
