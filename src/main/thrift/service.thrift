namespace scala ru.pavlenov.thrift

exception InvalidOperation {
  1: required string message
}

struct Tag {
  1: required i32 id,
  2: required string name
}

struct Row {
  1: required i32 id,
  2: required string name
}

typedef set<Row> RowSet

typedef set<Tag> TagSet

service TagService {
  void ping(),
  // - добавлять тэг к записи
  bool addTag(1: i32 rowId, 2: i32 tagId) throws (1: InvalidOperation ex),
  // - удалять тэг от записи
  bool removeTag(1: i32 rowId, 2: i32 tagId) throws (1: InvalidOperation ex),
  // - получать список записей по набору тэгов
  RowSet filterRowsByTags(1: set<i32> tagIds),
  // - получать список тэгов по записи
  TagSet filterTagsByRow(1: i32 rowId)
}