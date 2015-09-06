package ru.pavlenov.storage

import ru.pavlenov.models.Row

/**
 * ⓭ + 21
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
class RowStorage extends Storage[Row]{

  def find(id: Int): Option[Row] = data.find(_.id == id)

}
