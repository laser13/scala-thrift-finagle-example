import java.net.{InetSocketAddress, SocketAddress}

import ru.pavlenov.{BootModule, Server}

/**
 * ⓭ + 18
 * Какой сам? by Pavlenov Semen 26.09.15.
 */
object Main extends App {

  implicit val bootModule = new BootModule

  val address: SocketAddress = new InetSocketAddress("localhost", 8888)

  val server = new Server(address)

}
