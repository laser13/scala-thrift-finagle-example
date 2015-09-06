package ru.pavlenov

import ru.pavlenov.storage.{RelationStorage, RowStorage, TagStorage}
import scaldi.Module

/**
 * ⓭ + 01
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
class BootModule extends Module{

  bind [RowStorage] to new RowStorage

  bind [TagStorage] to new TagStorage

  bind [RelationStorage] to new RelationStorage

}
