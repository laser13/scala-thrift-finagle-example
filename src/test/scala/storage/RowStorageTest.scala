package storage

import ru.pavlenov.models.Row
import ru.pavlenov.storage.RowStorage

/**
 * ⓭ + 15
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
object RowStorageTest {

  def apply() = new RowStorage

  def apply(rows: Row*) = {
    val rs = new RowStorage()
    rows.foreach(rs.add)
    rs
  }

}
