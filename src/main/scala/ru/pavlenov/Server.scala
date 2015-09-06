package ru.pavlenov

import java.net.{InetSocketAddress, SocketAddress}

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol
import ru.pavlenov.thrift.TagService$FinagleService
import scaldi.Injector

/**
 * ⓭ + 05
 * Какой сам? by Pavlenov Semen 06.09.15.
 */
class Server(address: SocketAddress = new InetSocketAddress("localhost", 8888))(implicit inj: Injector) {

  val serverService = new TagService$FinagleService(new MicroService, new TBinaryProtocol.Factory())
  val server = ServerBuilder()
    .codec(ThriftServerFramedCodec())
    .bindTo(address)
    .name("FinagleRPCServer")
    .build(serverService)

  def stop() = server.close()

}
