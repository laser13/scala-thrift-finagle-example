package ru.pavlenov.storage

import ru.pavlenov.models.Tag

/**
 * ⓭ + 01
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
class TagStorage  extends Storage[Tag]{

  def find(id: Int): Option[Tag] = data.find(_.id == id)

}
