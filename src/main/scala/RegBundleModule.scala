// using a bundle for register

import chisel3._
import chisel3.util._

class MyBundle extends Bundle {
  val v1 = UInt(32.W)
  val v2 = UInt(32.W)
}

class RegBundleModule extends Module {
  val io = IO(new Bundle {
    val dataIn = Input(new MyBundle)
    val data = Output(new MyBundle)
  })

  val _data = Reg(new MyBundle())
  _data := io.dataIn
  io.data := _data
}
