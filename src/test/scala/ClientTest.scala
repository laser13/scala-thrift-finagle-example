import java.net.{InetSocketAddress, SocketAddress}

import com.twitter.finagle.ChannelWriteException
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.util.Await
import org.apache.thrift.protocol.TBinaryProtocol
import org.scalatest.{BeforeAndAfterAll, FunSpec, Matchers}
import ru.pavlenov.models.{Row, Tag}
import ru.pavlenov.storage.{RelationStorage, RowStorage, TagStorage}
import ru.pavlenov.thrift._
import ru.pavlenov.{BootModule, Server}
import scaldi.Module
import storage.{RowStorageTest, TagStorageTest}

/**
 * ⓭ + 08
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
class ClientTest extends FunSpec with Matchers with BeforeAndAfterAll{

  val (rowId1, rowId2, rowId3, rowId4) = (1,2,3,4)
  val (tagId1, tagId2, tagId3, tagId4) = (1,2,3,4)

  val rowStorage = RowStorageTest(
    Row(rowId1, "row-1"),
    Row(rowId2, "row-2"),
    Row(rowId3, "row-3"),
    Row(rowId4, "row-4")
  )

  val tagStorage = TagStorageTest(
    Tag(tagId1, "tag-1"),
    Tag(tagId2, "tag-2"),
    Tag(tagId3, "tag-3"),
    Tag(tagId4, "tag-4")
  )

  val relStorage = new RelationStorage

  def mockModule = new Module {
    bind [RowStorage] to rowStorage
    bind [TagStorage] to tagStorage
    bind [RelationStorage] to relStorage
  }

  implicit val testModule = mockModule :: new BootModule
  val address: SocketAddress = new InetSocketAddress("localhost", 8888)

  val server = new Server(address)

  val clientService = ClientBuilder()
    .hosts(address)
    .codec(ThriftClientFramedCodec())
    .hostConnectionLimit(1)
    .build

  val client = new TagService$FinagleClient(clientService, new TBinaryProtocol.Factory())

  override def afterAll() = server.stop()

  describe("MicroService") {

    it("ChannelWriteException") {

      val clientServiceEx = ClientBuilder()
        .hosts(new InetSocketAddress("localhost", 12321))
        .codec(ThriftClientFramedCodec())
        .hostConnectionLimit(1)
        .build

      val clientEx = new TagService$FinagleClient(clientServiceEx, new TBinaryProtocol.Factory())
      an [ChannelWriteException] should be thrownBy Await.result(clientEx.ping())
    }

    it("Ping") {
      Await.result(client.ping()) shouldEqual(())
    }

    describe("Add tag") {

      it("success") {

        Await.result(client.addTag(rowId1, tagId1)) shouldEqual true
        Await.result(client.addTag(rowId1, tagId2)) shouldEqual true
        Await.result(client.addTag(rowId2, tagId3)) shouldEqual true
        Await.result(client.addTag(rowId2, tagId4)) shouldEqual true

        relStorage.data.filter(_.rowId == rowId1).map(_.tagId) should contain theSameElementsAs List(tagId1, tagId2)
        relStorage.data.filter(_.rowId == rowId2).map(_.tagId) should contain theSameElementsAs List(tagId3, tagId4)

      }

      it("InvalidOperation") {
        an [InvalidOperation] should be thrownBy Await.result(client.addTag(1, -1))
        an [InvalidOperation] should be thrownBy Await.result(client.addTag(-1, 1))
        an [InvalidOperation] should be thrownBy Await.result(client.addTag(-1, -1))
      }

    }

    describe("Remove tag") {

      it("success") {

        Await.result(client.removeTag(rowId1, tagId1)) shouldEqual true
        Await.result(client.removeTag(rowId2, tagId3)) shouldEqual true

        relStorage.data.filter(_.rowId == rowId1).map(_.tagId) should contain theSameElementsAs List(tagId2)
        relStorage.data.filter(_.rowId == rowId2).map(_.tagId) should contain theSameElementsAs List(tagId4)

      }

      it("remove not exist relations") {
        Await.result(client.removeTag(-1, 1)) shouldEqual true
        Await.result(client.removeTag(-1, -1)) shouldEqual true
        Await.result(client.removeTag(1, -1)) shouldEqual true
      }

    }

    describe("Filter rows") {

      it("success") {
        Await.result(client.filterRowsByTags(Set(tagId2, tagId4))).map(x => x._1) should contain theSameElementsAs List(rowId1, rowId2)
      }

      it("empty") {
        Await.result(client.filterRowsByTags(Set(tagId1, tagId3))).map(x => x._1) should be('empty)
      }

    }

    describe("Filter tags") {

      it("success") {
        Await.result(client.filterTagsByRow(rowId1)).map(x => x._1) should contain theSameElementsAs List(tagId2)
        Await.result(client.filterTagsByRow(rowId2)).map(x => x._1) should contain theSameElementsAs List(tagId4)
      }

      it("empty") {
        Await.result(client.filterTagsByRow(rowId3)).map(x => x._1) should be('empty)
        Await.result(client.filterTagsByRow(rowId4)).map(x => x._1) should be('empty)
      }

    }

  }

}
