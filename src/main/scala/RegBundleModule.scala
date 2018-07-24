// using a bundle for register

import chisel3._
import chisel3.util._

class MyBundle extends Bundle {
  val v1 = UInt(32.W)
  val v2 = UInt(32.W)
}

object MyBundle {
  val init = Wire(new MyBundle)
  init.v1 := "h_1111_1111".U
  init.v2 := "h_2222_2222".U
}

class RegBundleModule extends Module {
  val io = IO(new Bundle {
    val dataIn = Input(new MyBundle)
    val data = Output(new MyBundle)
  })

  val _data = RegInit(MyBundle.init)
  _data := io.dataIn
  io.data := _data
}
