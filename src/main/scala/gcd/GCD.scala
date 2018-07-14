// See README.md for license details.

package gcd

import chisel3._

class GCD extends Module {
  val io = IO(new Bundle {
    val i = Input(UInt(32.W))
    val o = Output(UInt(32.W))
  })

  io.o := io.i
}
